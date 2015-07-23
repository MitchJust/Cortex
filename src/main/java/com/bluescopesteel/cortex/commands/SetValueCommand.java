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
public class SetValueCommand implements Command {

    private Object target;
    private Object value;

    public SetValueCommand(Object target, Object value) {
        this.target = target;
        this.value = value;
    }

    @Override
    public Object execute() throws Throwable {
        // System.out.println("Executing Set Value Command");
        if (!(target instanceof FieldAccessCommand) && target instanceof Command) {
            // System.out.println("Resolving Target...");
            target = ((Command) target).execute();
        }
        if (value instanceof Command) {
            // System.out.println("Resolving Value...");
            value = ((Command) value).execute();
        }

        if (target instanceof InternalVariable) {
            // System.out.println("Internal Variable Setting");
            ((InternalVariable) target).setVariableValue(value);
        } else if (target instanceof FieldAccessCommand) {
            System.out.println("Setting a field");
            setFieldValue((FieldAccessCommand) target, value);
            target = ((Command) target).execute();
        } else {
            System.out.println("Not Internal! Don't know how to do this yet! :(");
        }

        return (target);
    }

    public void setFieldValue(FieldAccessCommand fac, Object value) throws Throwable {
        Object object = ObjectResolver.resolveObjectReference(fac.getObjectName());
        System.out.println("Found Object: " + object);

        Field field = null;
        try {
            field = object.getClass().getField(fac.getMemberName());
        } catch (Exception ex) {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field1 : fields) {
                if (field1.getName().contentEquals(fac.getMemberName())) {
                    field = field1;
                    field.setAccessible(true);
                    break;
                }
            }
        }

        if (field != null) {
            field.set(object, value);
        } else {
            throw new Exception("Unable to find field " + fac.getMemberName() + " in object " + fac.getObjectName());
        }
    }

}
