/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.translators;

import com.cortex.Cortex;
import java.util.AbstractList;

/**
 *
 * @author Mitchell Just (mitch.just@gmail.com)
 */
public class AbstractListTranslator implements Translator<AbstractList> {

    @Override
    public String translate(AbstractList objects) {

        if (objects.isEmpty()) {
            return "List{0}";
        }

        Cortex instance = Cortex.getInstance();

        StringBuilder sb = new StringBuilder();
        sb.append("List{").append(objects.size()).append("}\n");

        for (int i = 0; i < objects.size(); i++) {
            sb.append("Element[").append(i).append("]\n");
            sb.append(instance.translate(objects.get(i)));
            sb.append("\n");
        }

        return sb.toString();
    }

}
