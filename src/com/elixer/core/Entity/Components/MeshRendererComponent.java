package com.elixer.core.Entity.Components;

import com.elixer.core.Display.Model.Mesh;
import com.elixer.core.Display.Model.MeshType;
import com.elixer.core.Display.Renderable;
import com.elixer.core.Display.Shaders.ShaderProgram;
import com.elixer.core.Display.Textures.Texture;
import com.elixer.core.Entity.Entity;
import com.elixer.core.Util.Logger;
import com.elixer.core.Util.Util;
import org.joml.Vector2f;
import org.joml.Vector2i;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class MeshRendererComponent extends Component implements Renderable {

    private Mesh mesh;
    private Texture texture = new Texture("bench.png", 2, 2, 0);
    private ShaderProgram shaderProgram = new ShaderProgram("def/vertex.glsl","def/fragment.glsl");

    private int textureIndex = 0;

    public MeshRendererComponent(Entity entity, Mesh mesh) {
        super(entity);
        this.mesh = mesh;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public MeshRendererComponent setShaderProgram(ShaderProgram shaderProgram) {
        if(shaderProgram != null)
            shaderProgram.destroy();

        this.shaderProgram = shaderProgram;
        return this;
    }

    @Override
    public void onRender() {
        render();
    }

    @Override
    public void onEnd() {
        shaderProgram.destroy();
        mesh.destroy();
    }

    @Override
    public void render() {
        shaderProgram.use();
        shaderProgram.setUniform("transMat", getEntity().getTransformationMatrix());
        shaderProgram.setUniform("projMat", getGame().getProjectionMatrix());
        shaderProgram.setUniform("viewMat", getGame().camPos.getRevercedMatrix());
        shaderProgram.setUniform("uvoffset", getTextureOffset());
        shaderProgram.setUniform("rows", texture.getRows());
        shaderProgram.setUniform("columns", texture.getColumns());

        glBindVertexArray(mesh.getVaoID());

        for (int i = 0; i < mesh.getVBOAmount(); i++) {
            glEnableVertexAttribArray(i);
        }

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
        if(mesh.getMeshType().isArray()) {
            glDrawArrays(mesh.getMeshType().getType(), 0, mesh.getVerts().size());
        } else {
            glDrawElements(mesh.getMeshType().getType(), mesh.getFaceCount() * 3, GL_UNSIGNED_INT, 0);
        }

        for (int i = 0; i < mesh.getVBOAmount(); i++) {
            glDisableVertexAttribArray(i);
        }

        glBindVertexArray(0);
    }

    @Override
    public int getRenderPriority() {
        return 1;
    }

    public void incrementTextureIndex() {
        int count = texture.getTexturesCount();
        if(textureIndex+1 >= texture.getTexturesCount()) {
            textureIndex = 0;
        } else {
            textureIndex++;
        }
    }

    public Vector2f getTextureOffset() {
        Vector2i index = texture.getIndexRowCol(textureIndex);

        float xOffset = ((float)texture.getColWidth()/(float)texture.getWidth()) * index.x;
        float yOffset = ((float)texture.getRowHeight()/(float)texture.getHeight()) * index.y;
        return new Vector2f(xOffset, yOffset);
    }

    public Mesh getMesh() {
        return mesh;
    }

    public MeshRendererComponent setTexture(Texture texture) {
        this.texture = texture;
        return this;
    }

    public int getTextureIndex() {
        return textureIndex;
    }

    public MeshRendererComponent setTextureIndex(int textureIndex) {
        Util.clampi(this.textureIndex = textureIndex, 0, texture.getTexturesCount()-1);
        return this;
    }

    public void increment() {
        if(textureIndex >= texture.getTexturesCount()-1) {
            textureIndex = 0;
        } else {
            textureIndex++;
        }
    }
}
