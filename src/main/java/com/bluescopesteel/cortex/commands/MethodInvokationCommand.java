/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluescopesteel.cortex.commands;

import com.bluescopesteel.cortex.InternalVariable;
import java.lang.reflect.Method;

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

//        Class objectClass = object.getClass();
//        Class[] parameterTypes = new Class[parameters.length];
//        for (int i = 0; i < parameters.length; i++) {
//            parameterTypes[i] = parameters[i].getClass();
//        }
//        System.out.println("Finding the method");
//
//        StringBuilder sb = new StringBuilder();
//        sb.append(methodName).append("( ");
//        for (Class parameterType : parameterTypes) {
//            sb.append(parameterType.getSimpleName()).append(",");
//
//        }
//        sb.append("\b)");
//
//        System.out.println(sb.toString());
//        Method method = objectClass.getMethod(methodName, parameterTypes);
        Method method = findMethod(object, methodName, parameters);

        if (method != null) {

            System.out.println("Invoking the method");
            Object response = method.invoke(object, parameters);
            System.out.println("response = " + response);
            return response;
        } else {
            throw new Exception("No Method Found: " + methodName);
        }

    }

    static Method findMethod(Object object,
            String methodName, Object[] params) {

        Class objectClass = object.getClass();

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
        ObjectReferenceAccessCommand command = new ObjectReferenceAccessCommand(objectName);
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
        if (primitive == int.class) {
            return (aClass == Integer.class);
        }

        return false;
    }

}
