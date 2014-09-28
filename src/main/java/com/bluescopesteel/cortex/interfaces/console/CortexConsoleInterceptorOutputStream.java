/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluescopesteel.cortex.interfaces.console;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.log4j.Logger;

/**
 *
 * @author Mitchell Just (Mitchell.Just@BlueScopeSteel.com)
 */
public class CortexConsoleInterceptorOutputStream extends OutputStream {

    private final OutputStream passthrough;
    private boolean isNewLine;
    private final String cortexPrefix = "ctx-> ";
    private final String backspaces = "\b\b\b\b\b\b";

    public CortexConsoleInterceptorOutputStream(OutputStream passthrough) {
        this.passthrough = passthrough;
        isNewLine = true;
        writePrefix();

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

    @Override
    public void write(int i) throws IOException {
        if (i == '\n') {
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
