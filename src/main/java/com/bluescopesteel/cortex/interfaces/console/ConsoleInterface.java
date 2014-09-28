/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluescopesteel.cortex.interfaces.console;

import com.bluescopesteel.cortex.interfaces.CortexInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

/**
 *
 * @author Mitchell Just (Mitchell.Just@BlueScopeSteel.com)
 */
public class ConsoleInterface extends CortexInterface {

    private InputScannerThread in;

    public ConsoleInterface() {

        CortexConsolePrintStream stream = new CortexConsolePrintStream(System.out);
        System.setOut(stream);
        System.setErr(stream);
    }

    @Override
    public void output(String out) {
        String[] split = out.split("\n");
        for (String line : split) {
            System.out.println(line);
        }
    }

    @Override
    public void initialize() {
        in = new InputScannerThread(System.in);
        in.start();
    }

    private class InputScannerThread extends Thread {

        InputStream in;
        BufferedReader br;

        public InputScannerThread(InputStream in) {
            this.in = in;
            this.br = new BufferedReader(new InputStreamReader(in));
        }

        @Override
        public void run() {

            while (true) {
                try {
                    String line = br.readLine();
                    handleCommand(line);
                } catch (IOException ex) {
                    Logger.getLogger(ConsoleInterface.class.getName()).error(ex);
                }
            }
        }
    }
}
