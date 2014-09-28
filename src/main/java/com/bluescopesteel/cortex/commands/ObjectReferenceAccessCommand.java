/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluescopesteel.cortex.commands;

import com.bluescopesteel.cortex.Cortex;
import com.bluescopesteel.cortex.InternalVariable;

/**
 *
 * @author Mitchell Just (Mitchell.Just@BlueScopeSteel.com)
 */
public class ObjectReferenceAccessCommand implements Command {

    public String objectName;

    public ObjectReferenceAccessCommand(String objectName) {
        this.objectName = objectName;
    }

    @Override
    public Object execute() throws Throwable {
        // System.out.println("Resolving Object Reference");
        Cortex theCortex = Cortex.getInstance();

        if (objectName.equalsIgnoreCase("null")) {
            return null;
        }
        if (objectName.equalsIgnoreCase("this")) {
            return theCortex;
        }

        Object service = theCortex.getService(objectName);
        if (service != null) {
            // System.out.println("Found a service");
            return service;
        }

        //maybe its a variable?
        InternalVariable variable = theCortex.getVariable(objectName);
        if (variable != null) {
            // System.out.println("Found a variable");
            return variable;
        }

        //maybe it's a primitive?
        //String?
        if (objectName.startsWith("\"") && objectName.endsWith("\"")) {
            // System.out.println("String");
            return objectName.substring(1, objectName.length() - 1);
        }

        //Boolean?
        if (objectName.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        } else if (objectName.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }

        try {
            Double d = Double.parseDouble(objectName);

            if (objectName.contains(".")) {
                return d;
            } else {
                return Integer.parseInt(objectName);
            }

        } catch (NumberFormatException ex) {

        }

        //Last option is to create a variable;
        // System.out.println("Creating a new variable: " + objectName);
        if (objectName.contains(" ")) {
            throw new Exception("Invalid Characters for Variable Name");
        }

        variable = theCortex.addVariable(objectName, null);
        return variable;

    }

    @Override
    public String toString() {
        return "ObjectReferenceAccessCommand{" + "objectName=" + objectName + '}';
    }

}
