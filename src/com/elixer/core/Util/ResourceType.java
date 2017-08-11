package com.elixer.core.Util;

import java.io.File;

/**
 * Created by aweso on 7/25/2017.
 */
public enum ResourceType {
    DATA("data", "dat", "txt", "csv"),
    SHADER("shaders", "glsl"),
    MODEL("models", "obj");

    private String dir;
    private String[] extentions;

    ResourceType(String dir, String... extentions) {
        this.dir = dir;
        this.extentions = extentions;
    }

    public boolean legal(String filename) {
        String[] title = filename.split("\\.");

        for(String ext: extentions) {
            if(title[title.length-1].equals(ext)) {
                return true;
            }
        }

        return false;
    }

    public String getDir() {
        return dir;
    }

    public String[] getExtentions() {
        return extentions;
    }
}
