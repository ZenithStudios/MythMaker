package com.elixer.core.Display.Model;

import com.elixer.core.Display.Textures.Texture;
import com.elixer.core.Util.Util;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3i;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by aweso on 10/29/2017.
 */
public class Model {

    private Mesh mesh;
    private Texture texture;

    private int vaoID;
    private ArrayList<Integer> vboIDs = new ArrayList<>();

    private int textureIndex = 3;
    private boolean shouldUpdate = false;

    public Model(Mesh mesh, Texture texture) {
        this.mesh = mesh;
        this.texture = texture;

        updateModel();
    }

    public void updateModel() {
        //if(vaoID != 0) destroy();

        int[] indecies = new int[mesh.getFaces().size() * 3];
        float[] verts = new float[mesh.getVerts().size() * 3];
        float[] uvs = new float[mesh.getVerts().size() * 2];

        for (int i = 0; i < mesh.getFaces().size(); i++) {
            Face curr = mesh.getFaces().get(i);
            Vector3i v = curr.getVert();

            indecies[(i*3)] = v.x;
            indecies[(i*3)+1] = v.y;
            indecies[(i*3)+2] = v.z;
        }

        for(int i = 0; i < mesh.getVerts().size(); i++) {
            Vertex vert = mesh.getVerts().get(i);

            verts[(i*3)] = vert.getPos().x;
            verts[(i*3)+1] = vert.getPos().y;
            verts[(i*3)+2] = vert.getPos().z;

            uvs[(i*2)] = vert.getUv().x;
            uvs[(i*2)+1] = vert.getUv().y;
        }

        startVAO(indecies);
        createVBO(verts, 3);
        createVBO(uvs, 2);
        endVAO();

        shouldUpdate = false;
    }

    public boolean shouldUpdate() {
        return shouldUpdate;
    }

    public void destroy() {
        for(int i: vboIDs) {
            glDeleteBuffers(i);
        }

        glDeleteVertexArrays(vaoID);
    }

    public void incrementTextureIndex() {
        if(textureIndex+1 >= texture.getTexturesCount()) {
            textureIndex = 0;
        } else {
            textureIndex++;
        }
    }

    public void setTextureIndex(int textureIndex) {
        this.textureIndex = textureIndex;
        shouldUpdate = true;
    }

    public Vector2f getTextureOffset() {
        Vector2i index = texture.getIndexRowCol(textureIndex);
        int colWidth = texture.getColWidth();
        int rowHeight = texture.getRowHeight();
        int width = texture.getWidth();
        int height = texture.getHeight();
        float xOffset = ((float)texture.getColWidth()/(float)texture.getWidth()) * index.x;
        float yOffset = ((float)texture.getRowHeight()/(float)texture.getHeight()) * index.y;
        return new Vector2f(xOffset, yOffset);
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVBOAmount() {
        return vboIDs.size();
    }

    public int getTextureIndex() {
        return textureIndex;
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

        glBufferData(GL_ARRAY_BUFFER, Util.getFloatBuffer(data), GL_DYNAMIC_DRAW);
        glVertexAttribPointer(vboIDs.size(), groupsOf, GL_FLOAT, false, 0, 0);
        vboIDs.add(vboID);
    }

    private void endVAO() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }
}
