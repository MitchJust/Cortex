/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bluescopesteel.cortex.interfaces.console;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 *
 * @author Mitchell Just (Mitchell.Just@BlueScopeSteel.com)
 */
public class CortexConsolePrintStream extends PrintStream{

    public CortexConsolePrintStream(OutputStream out) {
        super(new CortexConsoleInterceptorOutputStream(out));
    }
    
}
