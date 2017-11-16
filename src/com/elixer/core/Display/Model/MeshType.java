package com.elixer.core.Display.Model;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by aweso on 11/1/2017.
 */
public enum MeshType {
    POINT(GL_POINTS, true),
    TRIANGLE(GL_TRIANGLES, false);

    private int type;
    private boolean isArray;

    MeshType(int type, boolean isArray) {
        this.isArray = isArray;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public boolean isArray() {
        return isArray;
    }
}
