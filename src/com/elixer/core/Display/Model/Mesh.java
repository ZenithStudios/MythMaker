package com.elixer.core.Display.Model;

import com.elixer.core.Util.Util;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.*;

public class Mesh {

    private int vaoID;
    private ArrayList<Integer> vboIDs = new ArrayList<>();

    public Mesh(float[] data, int[] indecies) {

        startVAO(indecies);
        createVBO(data, 3);
        endVAO();
    }

    private void startVAO(int[] indecies) {
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        int indexBuffer = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.getIntBuffer(indecies), GL_STATIC_DRAW);
    }

    private void createVBO(float[] data, int groupsOf) {
        int vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);

        glBufferData(GL_ARRAY_BUFFER, Util.getFloatBuffer(data), GL_STATIC_DRAW);
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

    public int getVBOAmount() {
        return  vboIDs.size();
    }

    public int getVaoID() {
        return vaoID;
    }
}
