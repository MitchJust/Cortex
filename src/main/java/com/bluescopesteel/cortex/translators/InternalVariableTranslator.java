/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluescopesteel.cortex.translators;

import com.bluescopesteel.cortex.Cortex;
import com.bluescopesteel.cortex.InternalVariable;

/**
 *
 * @author Mitchell Just (Mitchell.Just@BlueScopeSteel.com)
 */
public class InternalVariableTranslator implements Translator<InternalVariable> {

    @Override
    public String translate(InternalVariable object) {
        return object.getVariableName() + " = " + Cortex.getInstance().translate(object.getVariableValueNoLink());
    }

}
