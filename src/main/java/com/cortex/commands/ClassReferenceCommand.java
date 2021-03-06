/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.commands;

/**
 *
 * @author Mitchell Just (mitch.just@gmail.com)
 */
public class ClassReferenceCommand implements Command {

    private final Class clazz;

    public ClassReferenceCommand(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object execute() throws Throwable {
        return clazz;
    }

}
