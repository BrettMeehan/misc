/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package texttobinary;


import java.io.FileNotFoundException;
import java.io.IOException;


import ch.systemsx.cisd.hdf5.IHDF5SimpleReader;
import ch.systemsx.cisd.hdf5.IHDF5SimpleWriter;
import ch.systemsx.cisd.hdf5.HDF5Factory;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author meehanb
 */
public class TextToBinary {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
//        String string = "hello";
//        String[] stringArray = {"hello", "world"};
//        
//        //writing to a binary file
//        IHDF5SimpleWriter writer = HDF5Factory.open("binaryFile.h5");
//        
//        writer.writeString("stringName", string);
//        writer.writeStringArray("stringArrayName", stringArray);
//
//        
//        writer.close();
//        
//        //reading from a binary file
//        IHDF5SimpleReader reader = HDF5Factory.openForReading("binaryFile.h5");
//        
//        String string1 = reader.readString("stringName");
//        String[] stringArray1 = reader.readStringArray("stringArrayName");
//        
//        System.out.println(string1);
//        System.out.println(stringArray1[0]);
//        System.out.println(stringArray1[1]);
        
        BufferedReader br = new BufferedReader(new FileReader("../../MATLAB_scripts/Kaggle/Click Data/train_rev2.csv"));
        
        String line = br.readLine();
        for(int i = 0; i < 10; i++){
            line = br.readLine();
            System.out.println(line);
        }
        br.close();
        //reader.close();
        
        
    }
    
}
