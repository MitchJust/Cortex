/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.translators;

/**
 *
 * @author Mitchell Just (mitch.just@gmail.com)
 */
public class ObjectTranslator implements Translator<Object> {

    @Override
    public String translate(Object object) {
        return "{" + object.getClass().getSimpleName() + "} " + object.toString();
    }

}
