/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.translators;

import com.cortex.Cortex;
import com.cortex.InternalVariable;

/**
 *
 * @author Mitchell Just (mitch.just@gmail.com)
 */
public class InternalVariableTranslator implements Translator<InternalVariable> {

    @Override
    public String translate(InternalVariable object) {
        return object.getVariableName() + " = " + Cortex.getInstance().translate(object.getVariableValueNoLink());
    }

}
