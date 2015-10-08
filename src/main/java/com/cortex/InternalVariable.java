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
public class InternalVariable {

    public InternalVariable(String variableName, Object variableValue) {
        this.variableName = variableName;
        this.variableValue = variableValue;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public Object getVariableValue() {
        if (variableValue instanceof InternalVariable) {
            return ((InternalVariable) variableValue).getVariableValue();
        } else {
            return variableValue;
        }
    }
    
    public Object getVariableValueNoLink() {
        return variableValue;
    }

    public void setVariableValue(Object variableValue) {
        this.variableValue = variableValue;
    }

    String variableName;
    Object variableValue;
}
