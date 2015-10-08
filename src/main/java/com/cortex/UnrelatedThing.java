/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex;

/**
 *
 * @author Mitchell Just <mitch.just@gmail.com>
 */
public class UnrelatedThing {
    
    public static String thing = "my original String";
    
    public static void setThing(String thing) {
        UnrelatedThing.thing = thing;
    }
    
    public static void main(String[] args) throws InterruptedException {
        while(true) {
            System.out.println(thing);
            Thread.sleep(5000);
        }
    }
}
