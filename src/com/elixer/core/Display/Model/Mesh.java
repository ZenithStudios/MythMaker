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
    ArrayList<Integer> vboIDs = new ArrayList<>();

    public Mesh(float[] data, int[] indecies) {

        startVAO();
        createVBO(data, 3);
        endVAO();
    }

    private void startVAO() {
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
    }

    private void createVBO(float[] data, int groupsOf) {
        int vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);

        glBufferData(GL_ARRAY_BUFFER, Util.getFloatBuffer(data), GL_STATIC_DRAW);
        glVertexAttribPointer(vboIDs.size(), groupsOf, GL_FLOAT, false, 0, 0);
    }

    private void endVAO() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public int getVaoID() {
        return vaoID;
    }
}
