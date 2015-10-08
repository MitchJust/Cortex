/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.commands;

import com.cortex.InternalVariable;

/**
 *
 * @author maj890
 */
public class ObjectResolver {

    public static Object resolveObjectReference(String objectReference) throws Throwable {
        Command command = new CommandParser().parseCommand(objectReference);
        Object object = command.execute();
        if (object instanceof InternalVariable) {
            return ((InternalVariable) object).getVariableValue();
        }
        return object;
    }
}
