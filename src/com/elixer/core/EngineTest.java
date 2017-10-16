package com.elixer.core;

import com.elixer.core.Entity.Components.MeshRendererComponent;
import com.elixer.core.Entity.Components.ScriptComponent;
import com.elixer.core.Entity.Entity;
import com.elixer.core.Entity.Scene;
import com.elixer.core.Util.Primitives;

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

        Entity triangle1 = new Entity("Entity1");
        triangle1.createComponent(MeshRendererComponent.class, Primitives.triangle);
        triangle1.createComponent(ScriptComponent.class, "hello.lua");
        triangle1.transform.addPos(0,0, -10f);

        scene.addEntity(triangle1);

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
