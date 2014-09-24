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
public class InvalidCommand implements Command{
    
    private final String errorString;

    public InvalidCommand(String errorString) {
        this.errorString = errorString;
    }
    
    @Override
    public Object execute() throws Throwable {
        throw new Throwable(errorString);
    }
}
