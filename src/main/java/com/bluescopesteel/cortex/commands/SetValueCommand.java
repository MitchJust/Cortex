/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bluescopesteel.cortex.commands;

import com.bluescopesteel.cortex.InternalVariable;

/**
 *
 * @author Mitchell Just (Mitchell.Just@BlueScopeSteel.com)
 */
public class SetValueCommand implements Command{
    private Object target;
    private Object value;

    public SetValueCommand(Object target, Object value) {
        this.target = target;
        this.value = value;
    }

    
    @Override
    public Object execute() throws Throwable {
        System.out.println("Executing Set Value Command");
        if(target instanceof Command) {
            System.out.println("Resolving Target...");
            target = ((Command)target).execute();
        }
        if(value instanceof Command) {
            System.out.println("Resolving Value...");
            value = ((Command)value).execute();
        }
        
        
        if(target instanceof InternalVariable) {
            System.out.println("Internal Variable Setting");
            ((InternalVariable)target).setVariableValue(value);
        }
        else {
            System.out.println("Not Internal! Don't know how to do this yet! :(");
        }

        return(target);
    }
}
