/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluescopesteel.cortex.commands;

import com.bluescopesteel.cortex.InternalVariable;

/**
 *
 * @author Mitchell Just (Mitchell.Just@BlueScopeSteel.com)
 */
public class AddCommand implements Command {

    private Object left;
    private Object right;

    public AddCommand(Object left, Object right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Object execute() throws Throwable {
        while (left instanceof Command) {
            left = ((Command) left).execute();
        }
        if (left instanceof InternalVariable) {
            left = ((InternalVariable) left).getVariableValue();
        }
        while (right instanceof Command) {
            right = ((Command) right).execute();
        }
        if (right instanceof InternalVariable) {
            right = ((InternalVariable) right).getVariableValue();
        }

        Class leftClass = left.getClass();
        Class rightClass = right.getClass();

        String leftString = left.toString();
        String rightString = right.toString();

        if (leftClass == String.class || rightClass == String.class) {
            return leftString + rightString;
        } else {
            Class highestPrecision = findHighestPrecisionClass(leftClass, rightClass);
            if (highestPrecision == Double.class || highestPrecision == Float.class) {
                double leftDouble = Double.parseDouble(leftString);
                double rightDouble = Double.parseDouble(rightString);

                double sum = leftDouble + rightDouble;

                if (highestPrecision == Double.class) {
                    return sum;
                } else {
                    return (float) sum;
                }

            } else {
                long leftLong = Long.parseLong(leftString);
                long rightLong = Long.parseLong(rightString);

                long sum = leftLong + rightLong;

                if (highestPrecision == Long.class) {
                    return sum;
                } else {
                    return (int) sum;
                }

            }

        }

    }

    public static Class findHighestPrecisionClass(Class left, Class right) {
        if (left == Double.class || right == Double.class) {
            return Double.class;
        } else if (left == Float.class || right == Float.class) {
            return Float.class;
        } else if (left == Long.class || right == Long.class) {
            return Long.class;
        } else {
            return Integer.class;
        }
    }

}
