/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluescopesteel.cortex.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mitchell Just (Mitchell.Just@BlueScopeSteel.com)
 */
public class CommandParser {

    public Command parseCommand(String command) {
//        if (!command.endsWith(";")) {
//            return new InvalidCommand("Command Not Terminated Correctly");
//        }

//        command = command.substring(0, command.length() - 1).trim();
        System.out.println("Parsing Command: " + command);

        if (command.contains("=")) {
            System.out.println("A Set Value!");
            return parseSetValueCommand(command);
        }

        //Check if there's an accessor (".")
        //Also make sure that this isn't part of a number
        //And that it's not a String
        if (command.contains(".") && !isNumeric(command) && !command.startsWith("\"")) {
            //Memeber Access
            if (command.endsWith(")")) {
                //Method
                System.out.println("A Method!");
                return parseMethodInvokationCommand(command);
            } else {

                //Is this a class reference?
                Class clazz = getClassReference(command);
                if (clazz != null) {
                    System.out.println("A Class!");
                    return new ClassReferenceCommand(clazz);
                }
                //Field

                System.out.println("A Field!");

                return parseFieldAccessCommand(command);
            }
        }

        //Object Reference
        System.out.println("An Object Reference!");
        return parseObjectReferenceCommand(command);

    }

    private static Class getClassReference(String string) {
        try {
            Class clazz = Class.forName(string);
            return clazz;
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    private static boolean isNumeric(String string) {
        try {
            double d = Double.parseDouble(string);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public MethodInvokationCommand parseMethodInvokationCommand(String command) {

        System.out.println("Parsing a Method Command: " + command);

        int lastIndex = command.substring(0, command.lastIndexOf("(")).lastIndexOf(".");

        String objectName = command.substring(0, lastIndex);
        String methodString = command.substring(lastIndex + 1);
        String[] methodSplit = methodString.split("\\(");
        String methodName = methodSplit[0];
        String parameterString = methodSplit[1];
        parameterString = parameterString.replaceAll("\\)", " ").trim();

        System.out.println("objectName = " + objectName);
        System.out.println("methodString = " + methodString);
        System.out.println("methodName = " + methodName);
        System.out.println("parameterString = " + parameterString);

        Object[] parameters;

        if (!parameterString.isEmpty()) {
            String[] parameterCommands = parameterString.split(",");

            parameters = new Object[parameterCommands.length];

            for (int i = 0; i < parameterCommands.length; i++) {
                String parameterCommandString = parameterCommands[i].trim();
                parameters[i] = parseCommand(parameterCommandString);
                System.out.println("Parameter [" + (i + 1) + "] " + parameters[i]);
            }
        } else {
            System.out.println("No Parameters");
            parameters = new Object[]{};
        }

        System.out.println("Building Command...");
        return new MethodInvokationCommand(objectName, methodName, parameters);
    }

    public ObjectReferenceAccessCommand parseObjectReferenceCommand(String command) {
        System.out.println("Searcing for object " + command);
        return new ObjectReferenceAccessCommand(command);
    }

    private Command parseFieldAccessCommand(String command) {

        System.out.println("Parsing a Field Access Command: " + command);

        int lastIndex = command.lastIndexOf(".");
        String objectName = command.substring(0, lastIndex);
        String fieldName = command.substring(lastIndex + 1);

        System.out.println("objectName = " + objectName);
        System.out.println("fieldName = " + fieldName);

        System.out.println("Building Command...");

        return new FieldAccessCommand(objectName, fieldName);
    }

    private Command parseSetValueCommand(String command) {
        String[] split = command.split("=");
        String target = split[0].trim();
        String value = split[1].trim();

        Command targetCommand = parseCommand(target);
        Command valueCommand = parseCommand(value);

        System.out.println("Finished constructing command");
        System.out.println(targetCommand + " = " + valueCommand);

        return new SetValueCommand(targetCommand, valueCommand);
    }

}
