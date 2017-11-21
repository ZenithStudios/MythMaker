package com.elixer.core.Display.Model;

import com.elixer.core.Util.Util;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Mesh {

    private ArrayList<Vertex> verts = new ArrayList<>();
    private ArrayList<Face> faces = new ArrayList<>();

    private int vaoID;
    private ArrayList<Integer> vboIDs = new ArrayList<>();

    private MeshType meshType;

    public Mesh(MeshType meshType) {
        this.meshType = meshType;
    }

    public Mesh(MeshType meshType, float[] verts, float[] uvs, int[] indecies) {
        this.meshType = meshType;

        if(!meshType.isArray()) {
            startVAO(indecies);
            createVBO(verts, 3);
            createVBO(uvs, 2);
            endVAO();
        } else {
            startVAO();
            createVBO(verts, 3);
            createVBO(uvs, 2);
            endVAO();
        }
    }

    public Mesh addVertex(Vertex... v) {
        verts.addAll(Arrays.asList(v));
        return this;
    }

    public Mesh addVertex(float x, float y, float z, float u, float v, float xn, float yn, float zn) {
        verts.add(new Vertex(x, y, z, u, v, xn, yn, zn));
        return this;
    }

    public Mesh addFace(Face... face) {
        faces.addAll(Arrays.asList(face));
        return this;
    }

    public Mesh addFace(int vert1, int vert2, int vert3) {
        faces.add(new Face(vert1, vert2, vert3));
        return this;
    }

    public Mesh updateMesh() {
        if(vaoID != 0)
            destroy();

        int[] indecies = new int[getFaces().size() * 3];
        float[] verts = new float[getVerts().size() * 3];
        float[] uvs = new float[getVerts().size() * 2];

        for (int i = 0; i < getFaces().size(); i++) {
            Face curr = getFaces().get(i);
            Vector3i v = curr.getVert();

            indecies[(i*3)] = v.x;
            indecies[(i*3)+1] = v.y;
            indecies[(i*3)+2] = v.z;
        }

        for(int i = 0; i < getVerts().size(); i++) {
            Vertex vert = getVerts().get(i);

            verts[(i*3)] = vert.getPos().x;
            verts[(i*3)+1] = vert.getPos().y;
            verts[(i*3)+2] = vert.getPos().z;

            uvs[(i*2)] = vert.getUv().x;
            uvs[(i*2)+1] = vert.getUv().y;
        }

        if(!meshType.isArray()) {
            startVAO(indecies);
            createVBO(verts, 3);
            createVBO(uvs, 2);
            endVAO();
        } else {
            startVAO();
            createVBO(verts, 3);
            createVBO(uvs, 2);
            endVAO();
        }

        return this;
    }

    private void startVAO(int[] indecies) {
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        int indexBuffer = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.getIntBuffer(indecies), GL_STATIC_DRAW);
    }

    private void startVAO() {
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
    }

    private void createVBO(float[] data, int groupsOf) {
        int vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);

        glBufferData(GL_ARRAY_BUFFER, Util.getFloatBuffer(data), GL_DYNAMIC_DRAW);
        glVertexAttribPointer(vboIDs.size(), groupsOf, GL_FLOAT, false, 0, 0);
        vboIDs.add(vboID);
    }

    private void endVAO() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void destroy() {
        for(int i: vboIDs) {
            glDeleteBuffers(i);
        }

        glDeleteVertexArrays(vaoID);
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

    public int getVaoID() {
        return vaoID;
    }

    public int getVBOAmount() {
        return vboIDs.size();
    }

    public MeshType getMeshType() {
        return meshType;
    }
}
