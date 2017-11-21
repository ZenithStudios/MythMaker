package com.elixer.core.Entity.Components;

import com.elixer.core.Display.Model.Mesh;
import com.elixer.core.Display.Model.MeshType;
import com.elixer.core.Display.Renderable;
import com.elixer.core.Display.Shaders.ShaderProgram;
import com.elixer.core.Display.Textures.Texture;
import com.elixer.core.Display.UI.UINode;
import com.elixer.core.Display.UI.UIVeiwMode;
import com.elixer.core.Entity.Components.Component;
import com.elixer.core.Entity.Entity;
import com.elixer.core.Entity.Transform;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;

/**
 * Created by aweso on 10/31/2017.
 */
public class UIComponent extends Component implements Renderable{

    private UINode root;
    private UIVeiwMode mode = UIVeiwMode.SCREEN;

    private ShaderProgram program = new ShaderProgram("ui/UIVertex.glsl", "ui/UIGeo.glsl", "ui/UIFragment.glsl");
    private Mesh mesh = new Mesh(MeshType.POINT, new float[]{0,0,0}, new float[]{0,0}, new int[]{0});

    private Vector4f color = new Vector4f();

    public UIComponent(Entity entity) {
        super(entity);
    }

    public UIComponent(Entity entity, UINode root) {
        super(entity);
        this.root = root;
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
    }

    @Override
    public void render() {
        program.use();
        program.setUniform("transMat", getEntity().transform.getTransformationMatrix());
        program.setUniform("projMat", getGame().getOrthoMatrix());
        program.setUniform("viewMat", getGame().camPos.getRevercedMatrix());
        program.setUniform("inColor", color);

        glBindVertexArray(mesh.getVaoID());

        glEnableVertexAttribArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);
        glDrawArrays(mesh.getMeshType().getType(), 0, 24);
        glDisableVertexAttribArray(0);

        glBindVertexArray(0);
    }

    @Override
    public int getRenderPriority() {
        return 0;
    }

    public UIVeiwMode getMode() {
        return mode;
    }

    public void setMode(UIVeiwMode mode) {
        this.mode = mode;
    }
}
