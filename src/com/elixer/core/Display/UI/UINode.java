package com.elixer.core.Display.UI;

import com.elixer.core.Display.Shaders.ShaderProgram;
import com.elixer.core.Display.Textures.Texture;
import com.elixer.core.Entity.Transform;
import org.joml.Vector3f;

import java.util.ArrayList;

/**
 * Created by aweso on 10/31/2017.
 */
public abstract class UINode {

    protected Vector3f origin = new Vector3f();
    protected Transform transform = new Transform();
    protected float width = 0, height = 0;

    private UINode parrent;
    private ArrayList<UINode> children = new ArrayList<>();

    public void addChild(UINode node) {
        node.setParrent(this);
        children.add(node);
    }

    public ArrayList<UINode> getChildren() {
        return children;
    }

    public UINode getParrent() {
        return parrent;
    }

    public void setParrent(UINode parrent) {
        parrent.getChildren().remove(this);
        this.parrent = parrent;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
