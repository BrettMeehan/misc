%TAKES RANDOM SAMPLES OF SAMPLE OBSERVATIONS FROM _some_ COLUMNS-MULTILEVEL LINEAR REGRESSION
%(multinomial model for nominal regression)
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
clear;
clc;

rng(1)%for reproducibility

sampleSize = 250000;%must be less than numChosenSubsets*1,000,000
numChosenSubsets = 12;%millions of observations
numTimesToSample = 160;

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

    disp(['sample# = ', num2str(i)]);
    %subcolumn_range = 0:39;
    r = datasample(0:39,numChosenSubsets, 'Replace', false);%select random subcolumns, excluding very last subcolumn(not 1000000 entries long)
    data = zeros(1000000*numChosenSubsets + 428967, numCols + 1);% +1 column for the dependent clicked variable
    for n = 1:numChosenSubsets%select random entry from subcolumn
        %predictor variables
        for j = 1:numCols
            data(1000000*n - 999999:1000000*n, j) = h5read(['numeric_bin_data/train/column', num2str(j),'.h5'], ['/column',num2str(j),'_', num2str(r(n))]);
        end
        %clicked data
        data(1000000*n - 999999:1000000*n, numCols + 1) = h5read('numeric_bin_data/train/clicked.h5', ['/clicked_', num2str(r(n))]);
         
    end

    if true%(rand() <= numChosenSubsets/41)%add last sub column to the population 20/41 of the time(results in numChosenSubsets+1 subsets)
        disp('@41st column');
        
        %predictor variables
        for j = 1:numCols
                data((1000000*numChosenSubsets + 1):(1000000*numChosenSubsets + 428967), j)= h5read(['numeric_bin_data/train/column', num2str(j),'.h5'], ['/column',num2str(j),'_40']);
        end
        %clicked data
        data((1000000*numChosenSubsets + 1):(1000000*numChosenSubsets + 428967), numCols + 1) = h5read('numeric_bin_data/train/clicked.h5', '/clicked_40');

    else
        data = data(1:1000000*numChosenSubsets, :); 
    end
    
    sample = datasample(data, sampleSize, 'Replace', false);
    clear data;
    
    trainPredictors = sample(:, 1:numCols);
    Clicked =  sample(:, numCols + 1) + 1; %"If Y is a column vector, it must contain positive integer category numbers."
    clear sample;

    disp(['training model', ' ', num2str(i),'...']);


    model = mnrfit(trainPredictors, Clicked);

    
    clear trainPredictors;
    clear Clicked;


    %Predictions for new data
    disp('predicting...');
    scores = mnrval(model, testPredictors);
    prediction_vector = prediction_vector + scores(:,2);
    
    clear scores;
    clear model;
    
    %break;
    %397 seconds

end


toc; 






prediction_vector = prediction_vector ./(numTimesToSample);

disp('writing predictions to hdf5 file...');
hdf5write('matlabPredictions3.h5', '/data', prediction_vector);

disp('writing predictions to csv file...');
% write csv file
system('java -jar C:/Users/meehanb/Documents/NetBeansProjects/WriteCSVpredictions/dist/WriteCSVpredictions.jar matlabPredictions3.h5 clickDataPredictions3.csv');
