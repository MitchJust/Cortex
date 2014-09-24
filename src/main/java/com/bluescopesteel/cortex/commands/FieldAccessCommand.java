/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluescopesteel.cortex.commands;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        Class objectClass = object.getClass();
        Field field = objectClass.getField(memberName);
        Object fieldValue = field.get(object);
        return fieldValue;

    }

    private Object resolveObjectReference() throws Throwable {
        ObjectReferenceAccessCommand command = new ObjectReferenceAccessCommand(objectName);
        return command.execute();
    }

}
