/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluescopesteel.cortex.commands;

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

        if (command.contains(".") && !isNumeric(command)) {
            //Memeber Access
            if (command.endsWith(")")) {
                //Method
                System.out.println("A Method!");
                return parseMethodInvokationCommand(command);
            } else {
                //Field

                System.out.println("A Field!");

                return parseFieldAccessCommand(command);
            }
        }

        //Object Reference
        System.out.println("An Object Reference!");
        return parseObjectReferenceCommand(command);

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
        String[] split = command.split("\\.");
        String objectName = split[0].trim();
        String[] split2 = split[1].split("\\(");
        String methodName = split2[0].trim();
        String parameterString = split2[1].trim();
        parameterString = parameterString.substring(0, parameterString.indexOf(")")).trim();
        System.out.println("parameterString = " + parameterString);

        Object[] parameters;
        
        if (!parameterString.isEmpty()) {
            String[] parameterCommands = parameterString.split(",");

            parameters = new Object[parameterCommands.length];

            for (int i = 0; i < parameterCommands.length; i++) {
                String parameterCommandString = parameterCommands[i].trim();
                parameters[i] = parseCommand(parameterCommandString);
            }
        }
        else {
            parameters = new Object[]{};
        }
        return new MethodInvokationCommand(objectName, methodName, parameters);
    }

    public ObjectReferenceAccessCommand parseObjectReferenceCommand(String command) {
        System.out.println("Searcing for object " + command);
        return new ObjectReferenceAccessCommand(command);
    }

    private Command parseFieldAccessCommand(String command) {
        String[] split = command.split("\\.");
        String objectName = split[0];
        String fieldName = split[1];
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
