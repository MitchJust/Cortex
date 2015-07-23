/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluescopesteel.cortex;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;

/**
 *
 * @author Mitchell Just <mitchell.just@bluescopesteel.com>
 */
public class CortexAttach {

    public static Socket bindToVM(String id) throws Exception {
        ServerSocket server = new ServerSocket(0);
        
        int port = server.getLocalPort();
        System.out.println("Waiting for agent connection [" + port + "]");

        try {
            System.out.println("Attaching to VM...");
            Class vmClass = Cortex.getInstance().resolveClass("com.sun.tools.attach.VirtualMachine");
            Method attachMethod = vmClass.getMethod("attach", String.class);
            Object vm = attachMethod.invoke(null, id);
            System.out.println("vm = " + vm);
            Method loadAgentMethod = vmClass.getMethod("loadAgent", String.class, String.class);

            String path = "C:\\Dev\\NetBeansProjects\\Cortex\\target\\Cortex-1.0-SNAPSHOT.jar";
            
            loadAgentMethod.invoke(vm, path, "CortexPort=" + port);
            
            Socket cortexClient = server.accept();
            server.close();
            return cortexClient;

        } catch (Exception ex) {
            server.close();
            throw ex;
        }

    }
    
    public static void main(String[] args) throws Exception {
        Cortex.getInstance();
        Socket client = bindToVM("");
        System.out.println("client = " + client);
    }
}
