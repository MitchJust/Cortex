/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.commands;

import com.cortex.InternalVariable;
import java.lang.reflect.Field;

/**
 *
 * @author Mitchell Just (mitch.just@gmail.com)
 */
public class FieldAccessCommand implements Command {

    private final String objectName;
    private final String memberName;

    public FieldAccessCommand(String objectName, String memberName) {
        this.objectName = objectName;
        this.memberName = memberName;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getMemberName() {
        return memberName;
    }

    @Override
    public Object execute() throws Throwable {

        Object object = ObjectResolver.resolveObjectReference(objectName);

        //If we want the class field, return the Class object
        if (memberName.contentEquals("class")) {
            // System.out.println("The Class");
            return object.getClass();
        }

        if (object instanceof Class) {
            //Static member
            // System.out.println("A static member");
            //Cast the object to class
            Class objectClass = (Class) object;
            //Find the field
            Field field = objectClass.getDeclaredField(memberName);
            field.setAccessible(true);
            //Get the static value of the field
            Object fieldValue = field.get(null);
            return fieldValue;
        } else {
            Class objectClass = object.getClass();
            Field field = objectClass.getDeclaredField(memberName);
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            return fieldValue;
        }

    }

}
