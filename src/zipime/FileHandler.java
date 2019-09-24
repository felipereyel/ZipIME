/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zipime;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;
import java.util.Scanner;  // Import the Scanner class


public class FileHandler {
    
    public static int getKey(){
	return Integer.parseInt(getPath());			
    };
    
    public static String getPath(){
        Scanner myObj = new Scanner(System.in);
        return myObj.nextLine();
    };
    
    public static String read(String fullFilePath, String lineSeparator) {
        String fullText = "";
        try (BufferedReader br = new BufferedReader(new FileReader(fullFilePath))) {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
               fullText += sCurrentLine+lineSeparator;
            }
            
            br.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return fullText;
    } 
    
    public static boolean write(String fullFilePath, String content) {
        try {
            FileWriter writer = new FileWriter(fullFilePath, false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(content);
            bufferedWriter.close();
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            
            return false;
        }
    }
}


 