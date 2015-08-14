%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

X1 = h5read('matlabPredictions1.h5', '/data');
X2 = h5read('matlabPredictions3.h5', '/data');

% predictions = zeros(4577464,1);
% for i = 1:10
%     mdl = LinearModel.fit(X,Clicked);
%     predictions = predictions + sfd;
% end
% 
% predictions = predictions ./ 10;

disp('writing predictions to hdf5 file...');
hdf5write('matlabPredictions.h5', '/data', (X1+X2)/2);

disp('writing predictions to csv file...');
%write csv file
system('java -jar C:/Users/meehanb/Documents/NetBeansProjects/WriteCSVpredictions/dist/WriteCSVpredictions.jar matlabPredictions.h5 clickDataPredictions.csv');
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
