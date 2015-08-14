function testPredictors = testData(numCols)

    testPredictors = [];
    for i = 1:numCols %for every column
        column = [];

        for j = 0:4 %concatenate all 5 subcolumns
            sub_column = h5read(['numeric_bin_data/test/column', num2str(i),'.h5'], ['/column',num2str(i),'_', num2str(j)]);
            column = vertcat(column, sub_column);
        end
        testPredictors = [testPredictors, column];
    end

    clear column;
    clear sub_column;

    testPredictors = double(testPredictors);%convert from int32 to double in order for predict() to work
    
end
