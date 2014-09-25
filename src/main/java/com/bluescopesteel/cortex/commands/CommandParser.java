/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluescopesteel.cortex.commands;

import com.bluescopesteel.cortex.Cortex;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mitchell Just (Mitchell.Just@BlueScopeSteel.com)
 */
public class CommandParser {

    public Command parseCommand(String command) {

        Cortex cortex = Cortex.getInstance();

//        if (!command.endsWith(";")) {
//            return new InvalidCommand("Command Not Terminated Correctly");
//        }
        command = command.trim();
//        command = command.substring(0, command.length() - 1).trim();
        System.out.println("Parsing Command: " + command);

        if (command.contains("=")) {
            System.out.println("A Set Value!");
            return parseSetValueCommand(command);
        }

        if (command.startsWith("new ")) {
            System.out.println("A New Instance!");
            return parseNewInstanceCommand(command);
        }

        //Check if there's an accessor (".")
        //Also make sure that this isn't part of a number
        //And that it's not a String
        if (command.contains(".") && !isNumeric(command) && !(command.startsWith("\"") && command.endsWith("\""))) {
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

        //Might be a searchable package class shortcut?
        System.out.println("Searching packages...");
        //Try our search packages
        Class clazz = null;
        String[] searchPackages = cortex.getSearchPackages();
        for (String searchPackage : searchPackages) {
            clazz = getClassReference(searchPackage + "." + command);
            if (clazz != null) {
                break;
            }
        }
        if (clazz != null) {
            System.out.println("A Class!");
            System.out.println(command + " resolved to " + clazz);
            return new ClassReferenceCommand(clazz);
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

    public static int findParameterClose(String methodCommand) {
        System.out.println("methodCommand = " + methodCommand);
        int bracketLevel = 1;
        boolean isQuoteEnclosed = false;

        int index = methodCommand.length() - 2;

        while (bracketLevel != 0) {
            char currentChar = methodCommand.charAt(index);

            if (currentChar == '\"') {
                isQuoteEnclosed = !isQuoteEnclosed;

            } else if (currentChar == '(' && !isQuoteEnclosed) {
                bracketLevel--;
            } else if (currentChar == ')' && !isQuoteEnclosed) {
                bracketLevel++;
            }
//            
//            System.out.println(currentChar + " " + isQuoteEnclosed + " " + bracketLevel);

            index--;
        }

        return index;

    }

    public MethodInvokationCommand parseMethodInvokationCommand(String command) {

        System.out.println("Parsing a Method Command: " + command);
        int parameterStartIndex = findParameterClose(command);

        String parameterString = command.substring(parameterStartIndex + 1);
        parameterString = parameterString.substring(1, parameterString.length() - 1);

        int lastIndex = command.substring(0, parameterStartIndex).lastIndexOf(".");

        String objectName = command.substring(0, lastIndex);
        String methodName = command.substring(lastIndex + 1, parameterStartIndex + 1);

        System.out.println("objectName = " + objectName);
        System.out.println("methodName = " + methodName);
        System.out.println("parameterString = " + parameterString);

        Object[] parameters;

        if (!parameterString.isEmpty()) {
//            String[] parameterCommands = parameterString.split(",");
//
//            parameters = new Object[parameterCommands.length];
//
//            for (int i = 0; i < parameterCommands.length; i++) {
//                String parameterCommandString = parameterCommands[i].trim();
//                parameters[i] = parseCommand(parameterCommandString);
//                System.out.println("Parameter [" + (i + 1) + "] " + parameters[i]);
//            }

            parameters = parseParameters(parameterString);

            for (int i = 0; i < parameters.length; i++) {
                parameters[i] = parseCommand((String) parameters[i]);
            }

        } else {
            System.out.println("No Parameters");
            parameters = new Object[]{};
        }

        System.out.println("Building Command...");
        return new MethodInvokationCommand(objectName, methodName, parameters);
    }

    public Object[] parseParameters(String parameterString) {
        boolean isQuoteEnclosed = false;
        int bracketLevel = 0;

        List<Object> parameters = new ArrayList<>();

        int index = 0;
        String currentParameter = new String();

        while (index < parameterString.length()) {
            char currentChar = parameterString.charAt(index);
//            System.out.println(currentChar);
            if (currentChar == '\"') {
                isQuoteEnclosed = !isQuoteEnclosed;
                currentParameter += currentChar;

            } else if (currentChar == '(' && !isQuoteEnclosed) {
                bracketLevel++;

                currentParameter += currentChar;
            } else if (currentChar == ')' && !isQuoteEnclosed) {
                bracketLevel--;
                currentParameter += currentChar;
            } else if (currentChar == ',' && !isQuoteEnclosed && bracketLevel == 0) {
                //New Parameter
                System.out.println("Adding the parameter!");
                parameters.add(currentParameter.trim());
                currentParameter = new String();
            } else {
                currentParameter += currentChar;
            }
//            System.out.println("currentParameter = " + currentParameter);
//            System.out.println("isQuoteEnclosed = " + isQuoteEnclosed);
//            System.out.println("bracketLevel = " + bracketLevel);

            index++;
        }

        parameters.add(currentParameter.trim());

        Object[] parameterArray = new Object[parameters.size()];
        parameterArray = parameters.toArray(parameterArray);

        return parameterArray;
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

    private Command parseNewInstanceCommand(String command) {
        String constructorString = command.replaceFirst("new ", "").trim();
        System.out.println("constructorString = " + constructorString);

        int parameterStartIndex = findParameterClose(constructorString);

        String parameterString = constructorString.substring(parameterStartIndex + 1);
        parameterString = parameterString.substring(1, parameterString.length() - 1);

        String className = constructorString.substring(0, parameterStartIndex + 1);

        System.out.println("className = " + className);
        System.out.println("parameterString = " + parameterString);

        Object[] parameters;

        if (!parameterString.isEmpty()) {

            parameters = parseParameters(parameterString);

            for (int i = 0; i < parameters.length; i++) {
                parameters[i] = parseCommand((String) parameters[i]);
            }

        } else {
            System.out.println("No Parameters");
            parameters = new Object[]{};
        }

        return new NewInstanceCommand(className, parameters);

    }

}
