/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluescopesteel.cortex.commands;

import com.bluescopesteel.cortex.InternalVariable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author Mitchell Just (Mitchell.Just@BlueScopeSteel.com)
 */
public class MethodInvokationCommand implements Command {

    private final String objectName;
    private final String methodName;
    private final Object[] parameters;

    public MethodInvokationCommand(String objectName, String methodName, Object[] parameters) {
        this.objectName = objectName;
        this.methodName = methodName;
        this.parameters = parameters;
    }

    @Override
    public Object execute() throws Throwable {

        System.out.println("Finding the object");
        Object object = resolveObjectReference();
        resolveParameters();

        Method method = findMethod(object, methodName, parameters);

        if (method != null) {
            if (Modifier.isStatic(method.getModifiers())) {
                System.out.println("A Static Method!");
                object = null;
            }

            System.out.println("Invoking the method...");
            System.out.print("");
            Object response = method.invoke(object, parameters);
            System.out.println("Done");
            System.out.println("response = " + response);
            
            
            return response;
        } else {
            Class c = (object instanceof Class) ? (Class) object : object.getClass();
            throw new Exception("No Method Found: " + methodName + " in class " + c.getName());
        }

    }

    static Method findMethod(Object object, String methodName, Object[] params) {

        Class objectClass = object instanceof Class ? (Class) object : object.getClass();

        System.out.println("Finding Method " + methodName + " in class " + objectClass);

        Method[] methods = objectClass.getMethods();

        methodLoop:
        for (Method method : methods) {
            if (method.getName().contentEquals(methodName)) {
                System.out.println("Found a matching method name");

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
        }

        System.out.println("No methods matched");
        return null;
    }

    private Object resolveObjectReference() throws Throwable {
        Command command = new CommandParser().parseCommand(objectName);
        Object object = command.execute();
        if (object instanceof InternalVariable) {
            return ((InternalVariable) object).getVariableValue();
        }
        return object;
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
        if(primitive == boolean.class) {
            return aClass == Boolean.class;
        }

        return false;
    }

}
