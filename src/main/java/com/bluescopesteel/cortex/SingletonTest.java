/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bluescopesteel.cortex;

/**
 *
 * @author Mitchell Just (Mitchell.Just@BlueScopeSteel.com)
 */
public class SingletonTest {

    private static SingletonTest instance;
    
    public static synchronized SingletonTest getInstance() {
        if(instance == null) {
            instance = new SingletonTest();
            
        }
        return instance;
    }
    
    public void doThing() {
        System.out.println("A Thing");
    }
}
