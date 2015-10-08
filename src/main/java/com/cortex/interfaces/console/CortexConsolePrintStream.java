/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.interfaces.console;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 *
 * @author Mitchell Just (mitch.just@gmail.com)
 */
public class CortexConsolePrintStream extends PrintStream {

    public CortexConsolePrintStream(OutputStream out) {
        super(new CortexConsoleInterceptorOutputStream(out));
    }

}
