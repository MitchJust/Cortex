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
public class ArrayTranslator implements Translator<Object[]> {

    @Override
    public String translate(Object[] objects) {

        if (objects.length == 0) {
            return "Array{0}";
        }

        Cortex instance = Cortex.getInstance();

        StringBuilder sb = new StringBuilder();
        sb.append("Array{").append(objects.length).append("}\n");

        for (int i = 0; i < objects.length; i++) {
            sb.append("Element[").append(i).append("]\n");
            sb.append(instance.translate(objects[i]));
            sb.append("\n");
        }

        return sb.toString();
    }

}
