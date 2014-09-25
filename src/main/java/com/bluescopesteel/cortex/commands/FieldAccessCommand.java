/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluescopesteel.cortex.commands;

import com.bluescopesteel.cortex.InternalVariable;
import java.lang.reflect.Field;

/**
 *
 * @author Mitchell Just (Mitchell.Just@BlueScopeSteel.com)
 */
public class FieldAccessCommand implements Command {

    private final String objectName;
    private final String memberName;

    public FieldAccessCommand(String objectName, String memberName) {
        this.objectName = objectName;
        this.memberName = memberName;
    }

    @Override
    public Object execute() throws Throwable {

        Object object = resolveObjectReference();
        
        //If we want the class field, return the Class object
        if(memberName.contentEquals("class")) {
            System.out.println("The Class");
            return object.getClass();
        }
        
        
        if (object instanceof Class) {
            //Static member
            System.out.println("A static member");
            //Cast the object to class
            Class objectClass = (Class) object;
            //Find the field
            Field field = objectClass.getField(memberName);
            //Get the static value of the field
            Object fieldValue = field.get(null);
            return fieldValue;
        } else {
            Class objectClass = object.getClass();
            Field field = objectClass.getField(memberName);
            Object fieldValue = field.get(object);
            return fieldValue;
        }

    }

    private Object resolveObjectReference() throws Throwable {
        Command command = new CommandParser().parseCommand(objectName);
        Object object = command.execute();
        if (object instanceof InternalVariable) {
            return ((InternalVariable) object).getVariableValue();
        }
        return object;
    }

}
