/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluescopesteel.cortex.interfaces;

import com.bluescopesteel.cortex.Cortex;
import com.bluescopesteel.cortex.commands.Command;
import com.bluescopesteel.cortex.commands.CommandParser;

/**
 *
 * @author Mitchell Just (Mitchell.Just@BlueScopeSteel.com)
 */
public abstract class CortexInterface {

    protected Cortex cortex;
    protected CommandParser commandParser = new CommandParser();

    public void registerCortex(Cortex cortex) {
        this.cortex = cortex;
    }

    public abstract void initialize();

    public void handleCommand(String commandString) {

        Command command = commandParser.parseCommand(commandString);

        if (command == null) {
            output("Parsing Command Returned a null command");

        } else {

            Object response;
            try {
                response = command.execute();
            } catch (Throwable ex) {
                response = ex;
            }

            output(response == null ? "void" : cortex.translate(response));
        }

    }

    public abstract void output(String out);
}
