/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bluescopesteel.cortex;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author Mitchell Just (Mitchell.Just@BlueScopeSteel.com)
 */
public class CortexClassLoader extends URLClassLoader{

    public CortexClassLoader() {
        super(new URL[]{},ClassLoader.getSystemClassLoader());
    }
    
    public void loadJar(File jarFile) throws MalformedURLException {
        addURL(jarFile.toURI().toURL());
    }

}
