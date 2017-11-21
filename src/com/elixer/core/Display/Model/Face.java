package com.elixer.core.Display.Model;

import org.joml.Vector3i;

/**
 * Created by aweso on 10/27/2017.
 */
public class Face {

    private Vector3i vert;

    public Face(Vector3i verts) {
        this.vert = verts;
    }

    public Face(int vert1, int vert2, int vert3) {
        this.vert = new Vector3i(vert1, vert2, vert3);
    }

    public Vector3i getVert() {
        return vert;
    }
}
