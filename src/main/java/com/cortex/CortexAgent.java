/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.lang.instrument.Instrumentation;
import java.net.Socket;
import java.util.Properties;

/**
 *
 * @author Mitchell Just <mitch.just@gmail.com>
 */
public class CortexAgent {

    public static void agentmain(String args, Instrumentation inst) throws Exception {
        System.out.println("CortexAgent Loaded");
        Class.forName("com.cortex.UnrelatedThing").getMethod("setThing", String.class).invoke(null, "Mitch");
        
        String[] split = args.split("=");
        Integer port = Integer.parseInt(split[1]);
        Socket s = new Socket("localhost", port);
        
        System.setOut(new PrintStream(new NaughtyOutputStream(System.out, s)));

    }
    
    public static class NaughtyOutputStream extends OutputStream{
        private final OutputStream passthrough;
        Socket s;

        public NaughtyOutputStream(OutputStream passthrough, Socket s) {
            this.passthrough = passthrough;
            this.s = s;
        }

        @Override
        public void write(int b) throws IOException {
            passthrough.write(b);
            s.getOutputStream().write(b);
        }
        
        
    }
}
