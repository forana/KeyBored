package com.alexforan.keybored.ui;

import java.awt.Image;
import java.lang.reflect.Method;

/*
 * Better name for this class: "OS X standardizer"
 */
public class OS {
    public static final boolean IS_OSX = System.getProperty("os.name").contains("OS X");
    
    public static void setDockIcon(Image image) {
        if (IS_OSX) {
            try {
                Class<?> appClass = Class.forName("com.apple.eawt.Application");
                Object appObj = appClass.getMethod("getApplication").invoke(appClass);
                Method setDockIconImage = appClass.getMethod("setDockIconImage", Image.class);
        
                setDockIconImage.invoke(appObj, image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void setDockBadge(String text) {
        if (IS_OSX) {
            try {
                Class<?> appClass = Class.forName("com.apple.eawt.Application");
                Object appObj = appClass.getMethod("getApplication").invoke(appClass);
                Method setDockIconBadge = appClass.getMethod("setDockIconBadge", String.class);
        
                setDockIconBadge.invoke(appObj, text);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void requestForeground() {
        if (IS_OSX) {
            try {
                Class<?> appClass = Class.forName("com.apple.eawt.Application");
                Object appObj = appClass.getMethod("getApplication").invoke(appClass);
                Method requestForeground = appClass.getMethod("requestForeground", Boolean.TYPE);
        
                requestForeground.invoke(appObj, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void enableSuddenTermination() {
        if (IS_OSX) {
            try {
                Class<?> appClass = Class.forName("com.apple.eawt.Application");
                Object appObj = appClass.getMethod("getApplication").invoke(appClass);
                appClass.getMethod("enableSuddenTermination").invoke(appObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
