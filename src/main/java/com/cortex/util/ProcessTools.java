/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Mitchell Just <mitch.just@gmail.com>
 */
public class ProcessTools {

    public static String getCommandOutput(String command) {
        String output = null;       //the string to return

        Process process = null;
        BufferedReader reader = null;
        InputStreamReader streamReader = null;
        InputStream stream = null;

        try {
            process = Runtime.getRuntime().exec(command);

            //Get stream of the console running the command
            stream = process.getInputStream();
            streamReader = new InputStreamReader(stream);
            reader = new BufferedReader(streamReader);

            String currentLine = null;  //store current line of output from the cmd
            StringBuilder commandOutput = new StringBuilder();  //build up the output from cmd
            while ((currentLine = reader.readLine()) != null) {
                commandOutput.append(currentLine);
            }

            int returnCode = process.waitFor();
            if (returnCode == 0) {
                output = commandOutput.toString();
            }

        } catch (IOException e) {
            System.err.println("Cannot retrieve output of command");
            System.err.println(e);
            output = null;
        } catch (InterruptedException e) {
            System.err.println("Cannot retrieve output of command");
            System.err.println(e);
        } finally {
            //Close all inputs / readers

            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    System.err.println("Cannot close stream input! " + e);
                }
            }
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    System.err.println("Cannot close stream input reader! " + e);
                }
            }
            if (reader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    System.err.println("Cannot close stream input reader! " + e);
                }
            }
        }
        //Return the output from the command - may be null if an error occured
        return output;
    }

    public static File findJavaFolder() {

        //"where" on Windows and "whereis" on Linux/Mac
        if (System.getProperty("os.name").contains("win") || System.getProperty("os.name").contains("Win")) {
            String path = getCommandOutput("where javac");
            if (path == null || path.isEmpty()) {
                System.err.println("There may have been an error processing the command or ");
                System.out.println("JAVAC may not set up to be used from the command line");
                System.out.println("Unable to determine the location of the JDK using the command line");
            } else {
            //Response will be the path including "javac.exe" so need to
                //Get the two directories above that
                File javacFile = new File(path);
                File jdkInstallationDir = javacFile.getParentFile().getParentFile();
                System.out.println("jdk in use at command line is: " + jdkInstallationDir.getPath());
                return jdkInstallationDir;
            }//else: path can be found
        } else {
            String response = getCommandOutput("whereis javac");
            if (response == null) {
                System.err.println("There may have been an error processing the command or ");
                System.out.println("JAVAC may not set up to be used from the command line");
                System.out.println("Unable to determine the location of the JDK using the command line");
            } else {
            //The response will be "javac:  /usr ... "
                //so parse from the "/" - if no "/" then there was an error with the command
                int pathStartIndex = response.indexOf('/');
                if (pathStartIndex == -1) {
                    System.err.println("There may have been an error processing the command or ");
                    System.out.println("JAVAC may not set up to be used from the command line");
                    System.out.println("Unable to determine the location of the JDK using the command line");
                   
                } else {
                    //Else get the directory that is two above the javac.exe file
                    String path = response.substring(pathStartIndex, response.length());
                    File javacFile = new File(path);
                    File jdkInstallationDir = javacFile.getParentFile().getParentFile();
                    System.out.println("jdk in use at command line is: " + jdkInstallationDir.getPath());
                    return jdkInstallationDir;
                }//else: path found
            }//else: response wasn't null
        }//else: OS is not windows
        return null;
    }
}
