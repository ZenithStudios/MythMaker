package com.elixer.core.Display.Model;

import com.elixer.core.Util.Util;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.*;

public class Mesh {

    private ArrayList<Vertex> verts = new ArrayList<>();
    private ArrayList<Face> faces = new ArrayList<>();

    public Mesh addVertex(Vertex... v) {
        verts.addAll(Arrays.asList(v));
        return this;
    }

    public Mesh addVertex(float x, float y, float z, float u, float v, float xn, float yn, float zn) {
        verts.add(new Vertex(x, y, z, u, v, xn, yn, zn));
        return this;
    }

    public Mesh addFace(Face face) {
        faces.add(face);
        return this;
    }

    public Mesh addFace(int vert1, int vert2, int vert3, int textureIndex) {
        faces.add(new Face(vert1, vert2, vert3, textureIndex));
        return this;
    }

    public ArrayList<Vertex> getVerts() {
        return verts;
    }

    public ArrayList<Face> getFaces() {
        return faces;
    }

    public int getFaceCount() {
        return faces.size();
    }
}
