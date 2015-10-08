/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cortex;

/**
 *
 * @author Mitchell Just (mitch.just@gmail.com)
 */
public class WeeObject implements Runnable{
    public long timeConstructed;
    public boolean isAlive = true;
    
    public WeeObject() {
        System.out.println("Wee Wee Wee!");
        timeConstructed = System.currentTimeMillis();
    }
    
    public void setIsAlive(boolean alive) {
        this.isAlive = alive;
    }

    @Override
    public void run() {
        while(isAlive) {
            try {
                Thread.sleep(5000);
                System.out.println("I'm Still alive@!!!");
            } catch (InterruptedException ex) {
                System.out.println("ex = " + ex);
            }
        }
        System.out.println("I'm Dead :(");
 
    }
}
