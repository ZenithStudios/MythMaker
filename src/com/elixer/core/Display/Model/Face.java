package com.elixer.core.Display.Model;

import org.joml.Vector3i;

/**
 * Created by aweso on 10/27/2017.
 */
public class Face {

    private Vector3i vert;
    private int textureIndex;

    public Face(Vector3i verts, int textureIndex) {
        this.vert = verts;
        this.textureIndex = textureIndex;
    }

    public Face(int vert1, int vert2, int vert3, int textureIndex) {
        this.vert = new Vector3i(vert1, vert2, vert3);
        this.textureIndex = textureIndex;
    }

    public Vector3i getVert() {
        return vert;
    }

    public int getTextureIndex() {
        return textureIndex;
    }
}
