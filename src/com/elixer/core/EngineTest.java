package com.elixer.core;

import com.elixer.core.Display.Model.Model;
import com.elixer.core.Display.Textures.Texture;
import com.elixer.core.Entity.Components.MeshRendererComponent;
import com.elixer.core.Entity.Components.ScriptComponent;
import com.elixer.core.Entity.Entity;
import com.elixer.core.Entity.Scene;
import com.elixer.core.Util.Logger;
import com.elixer.core.Util.Primitives;

import java.util.Arrays;

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

        Entity tri01 = new Entity("tri2");
        tri01.createComponent(MeshRendererComponent.class, new Model(Primitives.triangle, new Texture("bench.png", 2, 2, 0)));
        tri01.createComponent(ScriptComponent.class, "Rotate.lua");
        tri01.transform.addPos(0, 0, -10);

        scene.addEntity(tri01);

        return scene;
    }

    @Override
    protected void onStart() {
        //Util.makeLuaObject(new Test());
    }

    @Override
    protected void onPreStart() {

    }

    @Override
    protected void onEnd() {

    }
}
