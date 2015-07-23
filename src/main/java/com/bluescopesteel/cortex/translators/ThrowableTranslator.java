/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bluescopesteel.cortex.translators;

/**
 *
 * @author Mitchell Just <mitchell.just@bluescopesteel.com>
 */
public class ThrowableTranslator implements Translator<Throwable> {

    @Override
    public String translate(Throwable object) {
        StringBuilder sb = new StringBuilder();
        sb.append("Throwable Caught: {").append(object.getClass()).append("}\n");
        sb.append("Message: ").append(object.getMessage()).append("\n");
        sb.append("StackTrace:\n");
        StackTraceElement[] stackTrace = object.getStackTrace();
        for (StackTraceElement element : stackTrace) {
            sb.append("\t").append(element.getClassName()).append(".").append(element.getMethodName()).append(" [").append(element.getLineNumber()).append("]\n");
        }
        
        return sb.toString();
    }
    
}
