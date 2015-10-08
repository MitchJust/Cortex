/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.translators;

import com.cortex.Cortex;

/**
 *
 * @author Mitchell Just (mitch.just@gmail.com)
 */
public class CortexTranslator implements Translator<Cortex> {

    @Override
    public String translate(Cortex object) {
        StringBuilder sb = new StringBuilder();
        sb.append("Cortex Instance");
        return sb.toString();
    }

}
