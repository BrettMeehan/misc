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
test_prediction_vector = zeros(4577464,1);

tic;

for i = 1:numTimesToSample

    %place trainB prediction variables in an array
    disp('sampling for B...');
    sample = trainSample(numCols, numChosenSubsets, sampleSize);
    trainBPredictors = sample(:, 1:numCols);
    ClickedB =  sample(:, numCols + 1);
    clear sample;





    predictionB_vector = zeros(250000,2);
    test_prediction_vector_N = zeros(4577464,2);
    
    for j = 1:numTimesToSample %sample numChosenSubsets subsets numTimesToSample times
        disp('sampling for A...');
        sample = trainSample(numCols, numChosenSubsets, sampleSize);
        trainAPredictors = sample(:, 1:numCols);
        ClickedA =  sample(:, numCols + 1);
        clear sample;

        
        
        

        disp(['training ensemble using A ', num2str(j),'...']);
        BaggedEnsemble = TreeBagger(numTrees, trainAPredictors, ClickedA, 'Method', 'classification', ...
        'Minleaf', numMinLeaf, 'FBoot', 0.5, 'SampleWithReplacement', 'Off');
    
        disp(['training multilevel using A ', num2str(j),'...']);
        model = mnrfit(trainAPredictors, ClickedA + 1); %"If Y is a column vector, it must contain positive integer category numbers."--> add 1 to column

        clear trainAPredictors ClickedA;

        
        
        
        
        %Predictions for trainBPredictors data
        disp('predicting B using A...');
        [~, scores] = predict(BaggedEnsemble, trainBPredictors);
        predictionB_vector(:,1) = predictionB_vector(:,1) + scores(:,2);
        
        scores = mnrval(model, trainBPredictors);
        predictionB_vector(:,2) = predictionB_vector(:,2) + scores(:,2);

        
        %Predictions for test data
        testPredictors = testData(numCols);
        
        [~, scores] = predict(BaggedEnsemble, testPredictors);
        test_prediction_vector_N(:,1) = test_prediction_vector_N(:,1) + scores(:,2);
        
        clear BaggedEnsemble;
        
        scores = mnrval(model, testPredictors);
        test_prediction_vector_N(:,2) = test_prediction_vector_N(:,2) + scores(:,2);
        
        clear scores model testPredictors;
    end



    predictionB_vector = predictionB_vector ./(numTimesToSample);
    
    %disp(['logloss: ', num2str(logloss(predictionB_vector, ClickedB))]);
    
    
    disp('training linear regression...');
    mdl = LinearModel.fit(predictionB_vector, ClickedB);
    
    clear predictionB_vector ClickedB;



    %Predictions for new data
    disp('predicting test data...');
    scores = predict(mdl, test_prediction_vector_N);
    test_prediction_vector = test_prediction_vector + scores;

    clear scores mdl;
end
toc;

test_prediction_vector = test_prediction_vector ./ (numTimesToSample);
    
disp('writing predictions to hdf5 file...');
hdf5write('matlabPredictions.h5', '/data', test_prediction_vector);

disp('writing predictions to csv file...');
%write csv file
system('java -jar C:/Users/meehanb/Documents/NetBeansProjects/WriteCSVpredictions/dist/WriteCSVpredictions.jar matlabPredictions.h5 clickDataPredictions.csv');

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




