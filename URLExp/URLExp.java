/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package urlexp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
 
 
public class URLExp {
 
    public static void main(String[] args) {
        try {
            URL google = new URL("http://taxfoundation.org/article/2014-tax-brackets");
            URLConnection yc = google.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc
                    .getInputStream()));
            String inputLine;
            String s = "";
            while ((inputLine = in.readLine()) != null) {
                //System.out.println(inputLine);
                if (inputLine.equals("</div></div>  </div>"))
                    System.out.println("line");
                else
                    //System.out.println("0");
                    s = inputLine;
                    s.replaceFirst("</html>" ,"hehehehehethisworks" );
                    System.out.println(inputLine);
 
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
}
