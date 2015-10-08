/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.interfaces.console;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.log4j.Logger;

/**
 *
 * @author Mitchell Just (mitch.just@gmail.com)
 */
public class CortexConsoleInterceptorOutputStream extends OutputStream {

    private final OutputStream passthrough;
    private boolean isNewLine;
    private final String cortexPrefix = "ctx-> ";
    private final String backspaces = "\b\b\b\b\b\b";
    private final String newLine;
    private final char[] buffer;

    public CortexConsoleInterceptorOutputStream(OutputStream passthrough) {
        this.passthrough = passthrough;
        isNewLine = true;
        writePrefix();
        newLine = "\n";
        buffer = new char[newLine.length()];
    }

    private void writePrefix() {
        try {
            passthrough.write(cortexPrefix.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(CortexConsoleInterceptorOutputStream.class.getName()).error(ex);
        }
    }

    private void writeBackspaces() {
        try {
            passthrough.write(backspaces.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(CortexConsoleInterceptorOutputStream.class.getName()).error(ex);
        }
    }
    
    private void pushToBuffer(char c) {
        for(int i=buffer.length-1;i>0;i--) {
            buffer[i] = buffer[i-1];
        }
        buffer[0] = c;
    }
    
    private boolean bufferContainsNewLine() {
        for (int i = buffer.length-1; i >=0; i--) {
            if(buffer[i] != newLine.charAt((buffer.length-1)-i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void write(int i) throws IOException {
        pushToBuffer((char)i);
        if (bufferContainsNewLine()) {
            isNewLine = true;
            passthrough.write(i);
            writePrefix();

        } else {
            if (isNewLine) {
                writeBackspaces();
                passthrough.write(i);
                isNewLine = false;
            } else {
                passthrough.write(i);
            }
        }
    }

}
