package com.elixer.core.Entity.Components;

import com.elixer.core.Display.Model.Model;
import com.elixer.core.Display.Shaders.ShaderProgram;
import com.elixer.core.Entity.Entity;
import com.elixer.core.Util.Logger;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class MeshRendererComponent extends Component {

    private Model model;
    private ShaderProgram shaderProgram = new ShaderProgram("def/vertex.glsl","def/fragment.glsl");

    public MeshRendererComponent(Entity entity, Model model) {
        super(entity);
        this.model = model;
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
    public void onStart() {
        Logger.println(model.getTextureOffset().x, model.getTextureOffset().y, model.getTexture().getRows(), model.getTexture().getColumns());
    }

    @Override
    public void onRender() {
        render();
    }

    @Override
    public void onEnd() {
        shaderProgram.destroy();
        model.destroy();
    }

    protected void render() {
        shaderProgram.use();
        shaderProgram.setUniform("transMat", getEntity().getTransformationMatrix());
        shaderProgram.setUniform("projMat", getGame().getProjectionMatrix());
        shaderProgram.setUniform("viewMat", getGame().camPos.getRevercedMatrix());
        shaderProgram.setUniform("uvoffset", model.getTextureOffset());
        shaderProgram.setUniform("rows", model.getTexture().getRows());
        shaderProgram.setUniform("columns", model.getTexture().getColumns());

        glBindVertexArray(model.getVaoID());

        for(int i = 0; i < model.getVBOAmount(); i++) {
            glEnableVertexAttribArray(i);
        }

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, model.getTexture().getTextureID());
        glDrawElements(GL_TRIANGLES, model.getMesh().getFaceCount()*3, GL_UNSIGNED_INT, 0);

        for(int i = 0; i < model.getVBOAmount(); i++) {
            glDisableVertexAttribArray(i);
        }

        glBindVertexArray(0);
    }

    public Model getModel() {
        return model;
    }
}
