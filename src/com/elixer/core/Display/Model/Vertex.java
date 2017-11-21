package com.elixer.core.Display.Model;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Created by aweso on 10/26/2017.
 */
public class Vertex {

    private Vector3f pos;
    private Vector2f uv;
    private Vector3f normal;

    public Vertex(Vector3f pos, Vector2f uv, Vector3f normal) {
        this.pos = pos;
        this.uv = uv;
        this.normal = normal;
    }

    public Vertex(float x, float y, float z, float u, float v, float xn, float yn, float zn) {
        this.pos = new Vector3f(x, y, z);
        this.uv = new Vector2f(u, v);
        this.normal = new Vector3f(xn, yn, zn);
    }

    public Vector3f getPos() {
        return pos;
    }

    public Vector2f getUv() {
        return uv;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setUv(float u, float v) {
        uv.set(u, v);
    }
}
