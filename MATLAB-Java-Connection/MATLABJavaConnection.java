/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matlabjavaconnection;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxy;

import matlabcontrol.extensions.MatlabTypeConverter;
import matlabcontrol.extensions.MatlabNumericArray;

import java.util.*;



/**
 *
 * @author meehanb
 */
public class MatlabJavaConnection {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MatlabConnectionException, MatlabInvocationException {

    //Create a proxy, which we will use to control MATLAB
    MatlabProxyFactory factory = new MatlabProxyFactory();
    MatlabProxy proxy = factory.getProxy();

//    proxy.eval("cd('Q:\\MATLAB scripts\\transfer files')");
//    proxy.eval("run lin_reg_tool1GUI(1, 2, 'Hello')");
    
    ArrayList<Double> v = new ArrayList();
    v.add(12.1);
    v.add(10.1);
    
    double [][] array = new double[1][v.size()];
    System.out.println(v.size());
    for(int i = 0; i < v.size(); i++){
        System.out.println("i: "+i);
        array[0][i] = v.get(i);
    }
    MatlabTypeConverter processor = new MatlabTypeConverter(proxy);
    processor.setNumericArray("array", new MatlabNumericArray(array, null));
    proxy.eval("array");


    //Disconnect the proxy from MATLAB
    proxy.disconnect();
    }
    
}
