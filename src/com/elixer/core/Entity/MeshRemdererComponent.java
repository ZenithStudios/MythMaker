package com.elixer.core.Entity;

import com.elixer.core.Display.Model.Mesh;
import com.elixer.core.Display.Shaders.ShaderProgram;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class MeshRemdererComponent extends Component {

    private Mesh mesh;
    private ShaderProgram shaderProgram = new ShaderProgram("def/vertex.glsl","def/fragment.glsl");

    public MeshRemdererComponent(Mesh mesh) {
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh model) {
        mesh.destroy();
        this.mesh = model;
    }

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public void setShaderProgram(ShaderProgram shaderProgram) {
        shaderProgram.destroy();
        this.shaderProgram = shaderProgram;
    }

    @Override
    public void onRender() {
        render();
    }

    protected void render() {
        shaderProgram.use();
        shaderProgram.setUniform("transMat", getEntity().transform.getTransformationMatrix());
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
    }
}
