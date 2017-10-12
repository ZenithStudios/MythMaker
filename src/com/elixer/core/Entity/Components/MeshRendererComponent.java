package com.elixer.core.Entity.Components;

import com.elixer.core.Display.Model.Mesh;
import com.elixer.core.Display.Shaders.ShaderProgram;
import com.elixer.core.Entity.Entity;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class MeshRendererComponent extends Component {

    private Mesh mesh;
    private ShaderProgram shaderProgram = new ShaderProgram("def/vertex.glsl","def/fragment.glsl");

    public MeshRendererComponent(Entity entity) {
        super(entity);
    }

    public MeshRendererComponent(Entity entity, Mesh mesh) {
        super(entity);
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh model) {
        if(mesh != null)
            mesh.destroy();

        this.mesh = model;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public void setShaderProgram(ShaderProgram shaderProgram) {
        if(shaderProgram != null)
            shaderProgram.destroy();

        this.shaderProgram = shaderProgram;
    }

    @Override
    public void onRender() {
        render();
    }

    protected void render() {
        shaderProgram.use();
        shaderProgram.setUniform("transMat", getEntity().getTransformationMatrix());
        shaderProgram.setUniform("projMat", getGame().getProjectionMatrix());
        shaderProgram.setUniform("viewMat", getGame().camPos.getRevercedMatrix());

        glBindVertexArray(mesh.getVaoID());

        for(int i = 0; i < mesh.getVBOAmount(); i++) {
            glEnableVertexAttribArray(i);
        }

        glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_INT, 0);

        for(int i = 0; i < mesh.getVBOAmount(); i++) {
            glDisableVertexAttribArray(i);
        }

        glBindVertexArray(0);
    }
}
