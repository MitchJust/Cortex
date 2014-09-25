/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluescopesteel.cortex.commands;

import com.bluescopesteel.cortex.InternalVariable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 *
 * @author Mitchell Just (Mitchell.Just@BlueScopeSteel.com)
 */
public class NewInstanceCommand implements Command {

    private String className;
    private Object[] parameters;

    public NewInstanceCommand(String className, Object[] parameters) {
        this.className = className;
        this.parameters = parameters;
    }

    @Override
    public Object execute() throws Throwable {
        //Find the class
//        Class objectClass = Class.forName(className);
        Command command = new CommandParser().parseCommand(className);
        Class objectClass = (Class) command.execute();

        //Resolve the parameters
        resolveParameters();

        if (objectClass == null) {
            throw new Throwable(className + " not found");
        }

        Constructor constructor = findConstructor(objectClass, parameters);
        System.out.println("Found constructor, Creating new instance");
        Object instance = constructor.newInstance(parameters);
        
        return instance;
    }

    static Constructor findConstructor(Class objectClass, Object[] params) {

        System.out.println("Finding Constructor in class " + objectClass);

        Constructor[] methods = objectClass.getConstructors();

        methodLoop:
        for (Constructor method : methods) {

            Class<?>[] parameterTypes = method.getParameterTypes();

            if (parameterTypes.length == params.length) {
                System.out.println("Right number of params!");
                for (int i = 0; i < parameterTypes.length; i++) {

                    System.out.println(parameterTypes[i].getSimpleName() + " " + params[i].getClass().getSimpleName());

                    //check for primitive
                    if (parameterTypes[i].isPrimitive()) {
                        System.out.println("This is a primitive");
                        if (!isPrimitiveEquivalent(parameterTypes[i], params[i].getClass())) {
                            continue methodLoop;
                        }
                    } else {
                        if (!parameterTypes[i].isAssignableFrom(params[i].getClass())) {
                            continue methodLoop;
                        }
                    }
                }
                System.out.println("All params match!");
                return method;

            }
        }

        System.out.println("No methods matched");
        return null;
    }

    private void resolveParameters() throws Throwable {
        System.out.println("Resolving Parameters");
        for (int i = 0; i < parameters.length; i++) {
            Object parameter = parameters[i];

            if (parameter instanceof Command) {
                parameters[i] = ((Command) parameter).execute();
                if (parameters[i] instanceof InternalVariable) {
                    parameters[i] = ((InternalVariable) parameters[i]).getVariableValue();
                }
            }

            System.out.println("Parameter " + i + " {" + parameters[i].getClass().getSimpleName() + "} " + parameters[i]);
        }
    }

    private static boolean isPrimitiveEquivalent(Class<?> primitive, Class<? extends Object> aClass) {
        if (primitive == int.class
                || primitive == long.class
                || primitive == float.class
                || primitive == double.class) {
            return (aClass == Integer.class
                    || aClass == Long.class
                    || aClass == Float.class
                    || aClass == Double.class);
        }

        return false;
    }

}
