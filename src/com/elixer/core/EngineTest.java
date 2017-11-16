package com.elixer.core;

import com.elixer.core.Display.Textures.Texture;
import com.elixer.core.Entity.Components.UIComponent;
import com.elixer.core.Entity.Components.MeshRendererComponent;
import com.elixer.core.Entity.Components.ScriptComponent;
import com.elixer.core.Entity.Entity;
import com.elixer.core.Entity.Scene;
import com.elixer.core.Util.Primitives;

import static org.lwjgl.opengl.GL11.glPointSize;

/**
 * Created by aweso on 7/31/2017.
 */
public class EngineTest extends ElixerGame {

    public EngineTest(String title) {
        super(title);
    }

    public static void main(String[] args) {
        EngineTest test = new EngineTest("Test");
        test.begin();
    }

    @Override
    protected Scene instantiateScene() {
        Scene scene = new Scene("Test", this);

        Entity tri01 = new Entity("tri1"), tri02 = new Entity("tri2"), tri03 = new Entity("tri03");
        UIComponent comp1 = tri01.createComponent(UIComponent.class);
        UIComponent comp2 = tri02.createComponent(UIComponent.class);

        tri01.transform.addPos(-50, 0, -5);
        tri02.transform.addPos(50, 0, -6);
        tri03.transform.addPos(0,0,-10);

        tri03.createComponent(MeshRendererComponent.class, Primitives.plane).setTexture(new Texture("bench.png"));
        tri03.createComponent(ScriptComponent.class, "Rotate.lua");

        tri02.createComponent(ScriptComponent.class, "CircleMove.lua");

        comp1.setColor(200, 100,100, 150);
        comp2.setColor(100, 100, 200, 150);

        scene.addEntity(tri03, tri02, tri01);

        return scene;
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onPreStart() {
        glPointSize(5.0f);
    }

    @Override
    protected void onEnd() {

    }
}
