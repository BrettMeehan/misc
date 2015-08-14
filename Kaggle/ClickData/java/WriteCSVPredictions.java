/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package writecsvpredictions;

import ch.systemsx.cisd.hdf5.HDF5Factory;
import ch.systemsx.cisd.hdf5.IHDF5SimpleReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author meehanb
 */
public class WriteCSVpredictions {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        IHDF5SimpleReader reader = HDF5Factory.openForReading(args[0]);//"../../MATLAB/Kaggle/Click Data/matlabPredictions.h5"
        PrintWriter writer = new PrintWriter(args[1], "UTF-8");//"../../MATLAB/Kaggle/Click Data/clickDataPredictions.csv"
        
        writer.printf("id,click\n");
        
        double[] preds = reader.readDoubleArray("data");
        //System.out.println(preds.length);
        
        reader = HDF5Factory.openForReading("numeric_bin_data/test/column0.h5");//"../../MATLAB/Kaggle/Click Data/numeric_bin_data/test/column0.h5"
        String[] ids;
        
        int predsCounter = 0;
        for(int k = 0; k < 5; k++){//for each column of ids
            ids = reader.readStringArray("column0_"+ k);
            for(int m = 0; m < ids.length; m++){
                if(predsCounter != preds.length - 1)
                    writer.printf("%s,%f\n",ids[m], preds[predsCounter++]);
                else 
                    writer.printf("%s,%f",ids[m], preds[predsCounter++]);//no return literal on last line
            }
            
        }

        reader.close();
        writer.close();
    }
    
}
