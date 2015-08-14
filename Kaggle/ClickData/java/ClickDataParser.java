/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clickdataparser;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


import ch.systemsx.cisd.hdf5.IHDF5SimpleReader;
import ch.systemsx.cisd.hdf5.IHDF5SimpleWriter;
import ch.systemsx.cisd.hdf5.HDF5Factory;
import java.io.BufferedReader;
import java.io.FileReader;
/**
 *
 * @author meehanb
 */
public class ClickDataParser {
    /**
     * @param args the command line arguments
     */
    
    //test has: 4,577,464 entries(4,577,465 lines)
    //train has: 40,428,967 entries (40,428,968 lines)
    public static void main(String[] args) throws IOException {
//        //////////////////////////////
//        BufferedReader br = new BufferedReader(new FileReader("../../MATLAB/Kaggle/Click Data/test.csv"));
//        String line;
//        int count = 0;
//        
//        while((line = br.readLine()) != null){
//            count++;
//        }
//        System.out.println(count + " lines");
//        /////////////////////////////
        
        
        
        
        
        
//        /////////////////////////////1-3,13-22: double columns
//        String[] stringArray = new String[1];
//        double[] doubleArray = new double[1];
//        ArrayList<Integer> stringCols = new ArrayList<Integer>();
//        stringCols.add(0);
//        stringCols.add(4);
//        stringCols.add(5);
//        stringCols.add(6);
//        stringCols.add(7);
//        stringCols.add(8);
//        stringCols.add(9);
//        stringCols.add(10);
//        stringCols.add(11);
//        stringCols.add(12);
//        ////////////////////////////////////////////////////////////////////////
//        for(int k = 0; k < 1; k++){
//            boolean isString = stringCols.contains(k);
//            int columnIndex = k;//0-23
//            int i = 0;
//            int n = 0;
//            String columnName = "column"+Integer.toString(k);
//            
//            System.out.println("Working on " + columnName);
//            
//            BufferedReader br = new BufferedReader(new FileReader("../../MATLAB/Kaggle/Click Data/test.csv"));
//            IHDF5SimpleWriter writer = HDF5Factory.open("../../MATLAB/Kaggle/Click Data/Binary_data/test/" + columnName + ".h5");
//            
//            if(isString)
//                stringArray = new String[1000000];
//            else
//                doubleArray = new double[1000000];
//            
//
//
//            String line = br.readLine();//ignore header line
//            
//            while ((line = br.readLine()) != null) {
//
//                String[] tokenizedLine = line.split(",");
//                
//                if(isString)
//                    stringArray[i] = tokenizedLine[columnIndex];
//                else
//                    doubleArray[i] = Double.parseDouble(tokenizedLine[columnIndex]);
//
//                i++;
//
//                    
//                if(i >= 1000000){//write current array and create new array
//                    if(isString)
//                        writer.writeStringArray(columnName + "_"+ Integer.toString(n), stringArray);//write last filled stringArray to hdf5 file
//                    else
//                        writer.writeDoubleArray(columnName + "_"+ Integer.toString(n), doubleArray);//write last filled doubleArray to hdf5 file
//                    
//                    n++;//increment number of arrays written
//                    
//                    if(n == 4){//last set of entries is smaller 
//                        if(isString)
//                            stringArray = new String[577464];
//                        else
//                            doubleArray = new double[577464];
//                    }
//                    else{
//                        if(isString)
//                            stringArray = new String[1000000];
//                        else
//                            doubleArray = new double[1000000];
//                    }
//
//
//
//                    System.out.println("Done with part " + n);
//
//                    //break;//testing
//
//                    i = 0;//reset indexing for new array
//                }
//
//            }//end while
//
//            if(isString)
//                writer.writeStringArray(columnName + "_"+ Integer.toString(n), stringArray);//write last stringArray (smaller than others)
//            else
//                writer.writeDoubleArray(columnName + "_"+ Integer.toString(n), doubleArray);//write last doubleArray (smaller than others)
//
//            System.out.println("Done with part " + ++n);
//
//
//            br.close();
//            writer.close();
//        }//end for loop
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//        /////////////////////////////1-4,14-23: double columns
//        String[] stringArray = new String[1];
//        double[] doubleArray = new double[1];
//        ArrayList<Integer> stringCols = new ArrayList<Integer>();
//        stringCols.add(0);
//        stringCols.add(5);
//        stringCols.add(6);
//        stringCols.add(7);
//        stringCols.add(8);
//        stringCols.add(9);
//        stringCols.add(10);
//        stringCols.add(11);
//        stringCols.add(12);
//        stringCols.add(13);
//        ////////////////////////////////////////////////////////////////////////
//        for(int k = 2; k < 24; k++){
//            boolean isString = stringCols.contains(k);
//            int columnIndex = k;
//            int i = 0;
//            int n = 0;
//            String columnName = "column"+Integer.toString(k-1);
//            
//            System.out.println("Working on " + columnName);
//            
//            BufferedReader br = new BufferedReader(new FileReader("../../MATLAB/Kaggle/Click Data/train.csv"));
//            IHDF5SimpleWriter writer = HDF5Factory.open("../../MATLAB/Kaggle/Click Data/Binary_data/train/" + columnName + ".h5");
//            
//            if(isString)
//                stringArray = new String[1000000];
//            else
//                doubleArray = new double[1000000];
//            
//
//
//            String line = br.readLine();//ignore header line
//            
//            while ((line = br.readLine()) != null) {
////                System.out.println(line);
////                break;
//                String[] tokenizedLine = line.split(",");
//                
//                if(isString)
//                    stringArray[i] = tokenizedLine[columnIndex];
//                else
//                    doubleArray[i] = Double.parseDouble(tokenizedLine[columnIndex]);
//
//                i++;
//
//                    
//                if(i >= 1000000){//write current array and create new array
//                    if(isString)
//                        writer.writeStringArray(columnName + "_"+ Integer.toString(n), stringArray);//write last filled stringArray to hdf5 file
//                    else
//                        writer.writeDoubleArray(columnName + "_"+ Integer.toString(n), doubleArray);//write last filled doubleArray to hdf5 file
//                    
//                    n++;//increment number of arrays written
//                    
//                    if(n == 40){//last set of entries is smaller 
//                        if(isString)
//                            stringArray = new String[428967];
//                        else
//                            doubleArray = new double[428967];
//                    }
//                    else{
//                        if(isString)
//                            stringArray = new String[1000000];
//                        else
//                            doubleArray = new double[1000000];
//                    }
//
//
//
//                    System.out.println("Done with part " + n);
//
//                    //break;//testing
//
//                    i = 0;//reset indexing for new array
//                }
//
//            }//end while
//
//            if(isString)
//                writer.writeStringArray(columnName + "_"+ Integer.toString(n), stringArray);//write last stringArray (smaller than others)
//            else
//                writer.writeDoubleArray(columnName + "_"+ Integer.toString(n), doubleArray);//write last doubleArray (smaller than others)
//
//            System.out.println("Done with part " + ++n);
//
//
//            br.close();
//            writer.close();
//        }//end for loop
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//        //heathen string columns that need to be converted to ints-all except id column0(numbers too large)
//        ArrayList<Integer> stringCols = new ArrayList<Integer>();
//        stringCols.add(4);
//        stringCols.add(5);
//        stringCols.add(6);
//        stringCols.add(7);
//        stringCols.add(8);
//        stringCols.add(9);
//        stringCols.add(10);
//        stringCols.add(11);
//        stringCols.add(12);
//        
//        
//        
//        for(int k = 6; k < stringCols.size(); k++){//for each string column-combine BOTH test and train!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//            System.out.println("column: " + stringCols.get(k));
//            HashMap<String, Integer> mp = new HashMap<>();
//            int counter = 0;
//            String[] stringArray = new String[1];//used to read in hdf5 arrays
//            int colIndex = stringCols.get(k);
//            
//            ////Step 1: Find all of the unique id's for the column
//            
//            System.out.println("reading train column...");
//            IHDF5SimpleReader reader = HDF5Factory.openForReading("../../MATLAB/Kaggle/Click Data/Binary_data/train/column" + colIndex + ".h5");
//            //populate HashMap with train column
//            for(int i = 0; i < 41; i++){//there are 41 sub-columns per train column file
//                    
//                stringArray = reader.readStringArray("column"+colIndex+"_"+i);//////////////////PROBLEM!!!!!!!!!!!!!!!
//                System.out.println("i: "+i);
//                
//                for (String idNumber : stringArray) {
//                    if(!mp.containsKey(idNumber))
//                        mp.put(idNumber, counter++);
//                }
//            }
//
//            System.out.println("reading test column...");
//            reader = HDF5Factory.openForReading("../../MATLAB/Kaggle/Click Data/Binary_data/test/column" + colIndex +".h5");
//            //populate HashMap with test column
//            for(int i = 0; i < 5; i++){//there are 5 sub-columns per test column file
//                stringArray = reader.readStringArray("column"+colIndex+"_"+i);
//
//                for (String idNumber : stringArray) {
//                    if(!mp.containsKey(idNumber))
//                    mp.put(idNumber, counter++);
//                }
//            }
//            
//            
//            ////Step 2: Write column id's as unique integers
//            
//            
//            int[] intArray = new int[1];//for assigning id numbers as ints
//            System.out.println("rewriting train column...");
//            //rewrite train column
//            reader = HDF5Factory.openForReading("../../MATLAB/Kaggle/Click Data/Binary_data/train/column" + colIndex + ".h5");
//            IHDF5SimpleWriter writer = HDF5Factory.open("../../MATLAB/Kaggle/Click Data/numeric_bin_data/train/column"+ colIndex + ".h5");
//            
//            for(int i = 0; i < 41; i++){//there are 41 sub-columns per train column file
//                stringArray = reader.readStringArray("column"+colIndex+"_"+i);
//                intArray = new int[stringArray.length];
//
//                for (int j = 0; j < stringArray.length; j++) {
//                    intArray[j] = mp.get(stringArray[j]);
//                }
//                writer.writeIntArray("column"+colIndex+"_"+i, intArray);
//            }
//            
//            
//            System.out.println("rewriting test column");
//            //rewrite test column
//            reader = HDF5Factory.openForReading("../../MATLAB/Kaggle/Click Data/Binary_data/test/column" + colIndex + ".h5");
//            writer = HDF5Factory.open("../../MATLAB/Kaggle/Click Data/numeric_bin_data/test/column"+ colIndex + ".h5");
//            
//            for(int i = 0; i < 5; i++){//there are 5 sub-columns per test column file
//                stringArray = reader.readStringArray("column"+colIndex+"_"+i);
//                intArray = new int[stringArray.length];
//
//                for (int j = 0; j < stringArray.length; j++) {
//                    intArray[j] = mp.get(stringArray[j]);
//                }
//                writer.writeIntArray("column"+colIndex+"_"+i, intArray);
//            }
//
//
//            reader.close();
//            //break;//for testing purposes
//        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        

//       //heathen double columns that need to be converted to ints
//        ArrayList<Integer> intCols = new ArrayList<Integer>();
//        intCols.add(1);
//        intCols.add(2);
//        intCols.add(3);
//        intCols.add(13);
//        intCols.add(14);
//        intCols.add(15);
//        intCols.add(16);
//        intCols.add(18);
//        intCols.add(17);
//        intCols.add(19);
//        intCols.add(20);
//        intCols.add(21);
//        intCols.add(22);
//        
//        
//        
//        for(int k = 0; k < intCols.size(); k++){//for each double column-combine BOTH test and train!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//            System.out.println("column: " + intCols.get(k));
//            double[] doubleArray = new double[1];//used to read in hdf5 arrays
//            int colIndex = intCols.get(k);
//            
//            
//            int[] intArray = new int[1];//for converting doubles to integers
//            System.out.println("rewriting train column...");
//            //rewrite train column
//            IHDF5SimpleReader reader = HDF5Factory.openForReading("../../MATLAB/Kaggle/Click Data/Binary_data/train/column" + colIndex + ".h5");
//            IHDF5SimpleWriter writer = HDF5Factory.open("../../MATLAB/Kaggle/Click Data/numeric_bin_data/train/column"+ colIndex + ".h5");
//            
//            for(int i = 0; i < 41; i++){//there are 41 sub-columns per train column file
//                doubleArray = reader.readDoubleArray("column"+colIndex+"_"+i);
//                intArray = new int[doubleArray.length];
//
//                for (int j = 0; j < doubleArray.length; j++) {
//                    intArray[j] = (int) doubleArray[j];
//                }
//                writer.writeIntArray("column"+colIndex+"_"+i, intArray);
//            }
//            
//            
//            System.out.println("rewriting test column");
//            //rewrite test column
//            reader = HDF5Factory.openForReading("../../MATLAB/Kaggle/Click Data/Binary_data/test/column" + colIndex + ".h5");
//            writer = HDF5Factory.open("../../MATLAB/Kaggle/Click Data/numeric_bin_data/test/column"+ colIndex + ".h5");
//            
//            for(int i = 0; i < 5; i++){//there are 5 sub-columns per test column file
//                doubleArray = reader.readDoubleArray("column"+colIndex+"_"+i);
//                intArray = new int[doubleArray.length];
//
//                for (int j = 0; j < doubleArray.length; j++) {
//                    intArray[j] = (int) doubleArray[j];
//                }
//                writer.writeIntArray("column"+colIndex+"_"+i, intArray);
//            }
//
//
//            reader.close();
//            //break;//for testing purposes
//        }//end column for loop
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//        //separate timestamp data
//        int[] rIntArray;
//        int[] wIntArray;
//        IHDF5SimpleReader reader = HDF5Factory.openForReading("../../MATLAB/Kaggle/Click Data/numeric_bin_data/test/column1.h5");
//        
//        for(int i = 0; i < 4; i++){//YY,MM,DD,HH
//            String colIndex = Integer.toString(23 + i);
//            IHDF5SimpleWriter writer = HDF5Factory.open("../../MATLAB/Kaggle/Click Data/numeric_bin_data/test/column" + colIndex +".h5");
//            
//            for(int j = 0; j < 5; j++){//for every sub column
//                rIntArray = reader.readIntArray("column1_" + j);
//                wIntArray = new int[rIntArray.length];
//                
//                for(int k = 0; k < rIntArray.length; k++){//for every timestamp
//                    //System.out.println((rIntArray[k]/((int)Math.pow(10, 6-2*i)))%(100));
//                    wIntArray[k] = (rIntArray[k]/((int)Math.pow(10, 6-2*i)))%(100);//get 2 digits at a time
//                }
//                
//                writer.writeIntArray("column"+colIndex+"_"+j, wIntArray);
//            }
//            
//            writer.close();
//        }
//        
//        reader.close();
        
        
        
        //create column for weekend
        HashMap<Integer, Integer> mp = new HashMap<>();
        mp.put(21, 0);
        mp.put(22, 0);
        mp.put(23, 0);
        mp.put(24, 0);
        mp.put(25, 1);
        mp.put(26, 1);
        mp.put(27, 0);
        mp.put(28, 0);
        mp.put(29, 0);
        mp.put(30, 0);
        mp.put(31, 0);//for test data-does Halloween count as a weekend?-maybe not since people work
        
        int[] rIntArray;
        int[] wIntArray;
        IHDF5SimpleReader reader = HDF5Factory.openForReading("../../MATLAB/Kaggle/Click Data/numeric_bin_data/test/column25.h5");
        IHDF5SimpleWriter writer = HDF5Factory.open("../../MATLAB/Kaggle/Click Data/numeric_bin_data/test/column28.h5");

        for(int j = 0; j < 5; j++){//for every sub column
            rIntArray = reader.readIntArray("column25_" + j);
            wIntArray = new int[rIntArray.length];

            for(int k = 0; k < rIntArray.length; k++)
                wIntArray[k] = mp.get(rIntArray[k]);

            writer.writeIntArray("column28_"+j, wIntArray);
        }
        writer.writeString("name", "Weekend");

        writer.close();
        
        reader.close();

        
    }//end main
    
}//end class ClickDataParser
