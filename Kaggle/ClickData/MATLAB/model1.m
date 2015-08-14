%TAKES RANDOM SAMPLES OF SAMPLE OBSERVATIONS FROM _all_ COLUMNS-TREEBAGGER
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
clear;
clc;

rng(1)%for reproducibility

sampleSize = 250000;%must be less than numChosenSubsets*1,000,000
numChosenSubsets = 10;%millions of observations->must be 10 here
numTimesToSample = 10;

numCols = 26;%number of predictor columns
numTrees = 40;
numMinLeaf = 50;%usually 50

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%store predictions here
prediction_vector = zeros(4577464,1);




%place test prediction variables in an array

testPredictors = [];
for i = 1:numCols %for every column
    column = [];

    for j = 0:4 %concatenate all 5 subcolumns
        sub_column = h5read(['numeric_bin_data/test/column', num2str(i),'.h5'], ['/column',num2str(i),'_', num2str(j)]);
        column = vertcat(column, sub_column);
    end
    testPredictors = [testPredictors, column];
end
testPredictors = double(testPredictors);%convert from int32 to double in order for predict() to work

clear column;
clear sub_column;




tic;



%SAMPLE FROM NEW POPULATION EVERY TIME, OR USE SAME POP MULTIPLE TIMES????

for i = 1:numTimesToSample %sample numChosenSubsets subsets numTimesToSample times

    for sampleSplits = 1:4%construct four subsamples from different subcolumns, concatenate, then sample again
        disp(['sample# = ', num2str(i)]);
        
        switch sampleSplits
            case 1
                r = 0:9;
            case 2
                r = 10:19;
            case 3
                r = 20:29;
            case 4
                r = 30:39;
        end
        
        if(sampleSplits == 4)
            data = zeros(1000000*numChosenSubsets + 428967, numCols + 1);% +1 column for the dependent clicked variable
        else
            data = zeros(1000000*numChosenSubsets, numCols + 1);% +1 column for the dependent clicked variable
        end
        
        for n = 1:numChosenSubsets%select random entry from subcolumn
            %predictor variables
            for j = 1:numCols
                data(1000000*n - 999999:1000000*n, j) = h5read(['numeric_bin_data/train/column', num2str(j),'.h5'], ['/column',num2str(j),'_', num2str(r(n))]);
            end
            %clicked data
            data(1000000*n - 999999:1000000*n, numCols + 1) = h5read('numeric_bin_data/train/clicked.h5', ['/clicked_', num2str(r(n))]);

        end

        if (sampleSplits == 4)%add last, shorter subcolumn
            %predictor variables
            for j = 1:numCols
                    data((1000000*numChosenSubsets + 1):(1000000*numChosenSubsets + 428967), j)= h5read(['numeric_bin_data/train/column', num2str(j),'.h5'], ['/column',num2str(j),'_40']);
            end
            %clicked data
            data((1000000*numChosenSubsets + 1):(1000000*numChosenSubsets + 428967), numCols + 1) = h5read('numeric_bin_data/train/clicked.h5', '/clicked_40');

        end

        
        
        switch sampleSplits
            case 1
                sample1 = datasample(data, sampleSize, 'Replace', false);
            case 2
                sample2 = datasample(data, sampleSize, 'Replace', false);
            case 3
                sample3 = datasample(data, sampleSize, 'Replace', false);
            case 4
                sample4 = datasample(data, sampleSize, 'Replace', false);
        end
        clear data;
    end
    sample = vertcat(sample1, sample2, sample3, sample4);
    sample = datasample(sample, sampleSize, 'Replace', false);
    clear sample1 sample2 sample3 sample4;
    
    
    trainPredictors = sample(:, 1:numCols);
    Clicked =  sample(:, numCols + 1);
    clear sample;


    disp(['training ensemble', num2str(i),'...']);
    BaggedEnsemble = TreeBagger(numTrees, trainPredictors, Clicked, 'Method', 'classification', ...
    'Minleaf', numMinLeaf, 'FBoot', 0.5, 'SampleWithReplacement', 'Off');
    
    clear trainPredictors;
    clear Clicked;

    %Predictions for new data
    disp('predicting...');
    [~, scores] = predict(BaggedEnsemble, testPredictors);
    prediction_vector = prediction_vector + scores(:,2);

    clear scores;
    clear BaggedEnsemble;
    
    %break;
    
    %223 seconds
end


toc; 






prediction_vector = prediction_vector ./(numTimesToSample);

disp('writing predictions to hdf5 file...');
hdf5write('matlabPredictions1.h5', '/data', prediction_vector);

disp('writing predictions to csv file...');
% write csv file
system('java -jar C:/Users/meehanb/Documents/NetBeansProjects/WriteCSVpredictions/dist/WriteCSVpredictions.jar matlabPredictions1.h5 clickDataPredictions1.csv');

%worse score
%     |         0.4252968 10 trees 500000 entries - all categorical
%     |         0.4250831 20 trees 250000 entries - all categorical
%     |         0.4236981 20 trees 250000 entries - none categorical
%     |         0.4223366 20 trees 250000 entries - none categorical- separated timestamp vars
%     |         0.4223345 40 trees 250000 entries - MinLeaf 25-none categorical- separated timestamp vars
%     |         0.4218420 40 trees 250000 entries - none categorical- separated timestamp vars
%     |         0.4210285 30 trees 500000 entries - all categorical-2400 trees total
%     |         0.4149861 160 trees 250000 entries - 25920 trees total-none categorical- separated timestamp vars  
%     V         0.4149744 40 trees 250000 entries - 6480 trees total-none categorical- separated timestamp vars  

%better score

%run model2.m
