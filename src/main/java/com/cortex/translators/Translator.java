/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortex.translators;

/**
 *
 * @author Mitchell Just (mitch.just@gmail.com)
 * @param <T> The class this translator translates
 */
public interface Translator<T> {

    public String translate(T object);
}
