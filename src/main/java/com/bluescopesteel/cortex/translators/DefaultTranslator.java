/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bluescopesteel.cortex.translators;

/**
 *
 * @author Mitchell Just (Mitchell.Just@BlueScopeSteel.com)
 */
public class DefaultTranslator implements Translator<Object>{

    @Override
    public String translate(Object object) {
        return "{" +object.getClass().getSimpleName() + "} " + object.toString();
    }

}
