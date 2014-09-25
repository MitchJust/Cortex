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
public class WeeObject {
    public long timeConstructed;
    
    public WeeObject() {
        timeConstructed = System.currentTimeMillis();
    }
}
