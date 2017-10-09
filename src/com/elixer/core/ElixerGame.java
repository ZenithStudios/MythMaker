package com.elixer.core;

import com.elixer.core.Input.Input;
import com.elixer.core.Display.Model.Mesh;
import com.elixer.core.Display.Window;
import com.elixer.core.Entity.*;
import com.elixer.core.Entity.Components.Component;
import com.elixer.core.Entity.Components.MeshRendererComponent;
import com.elixer.core.Util.*;
import org.joml.Matrix4f;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.BaseLib;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.*;

public abstract class ElixerGame {

    public Transform camPos = new Transform();

    protected String title;
    protected static Window currWindow;

    private float fov = 75f, nearPlane = 0.01f, farPlane = 1000f;
    private boolean isRunning;
    private Scene currScene = new Scene("default", this);

    public ElixerGame(String title) {
        this.title = title;
        initSystems();
    }

    public void start() {
        init();
        run();
        end();
    }

    public void stop() {
        isRunning = false;
    }

    private void run() {
        isRunning = true;
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);

        float[] data1 = new float[]
              {-1.0f, -1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                0.0f,  1.0f, 0.0f};
        int[] indecies1 = new int[]
              {2, 0, 1};

        Mesh triangle = new Mesh(data1, indecies1);


        Entity entity1 = new Entity("Test1");
        entity1.createComponent(MeshRendererComponent.class).setMesh(triangle);
        entity1.transform.addPos(0,0,-10);

        Entity entity2 = new Entity("Test2");
        entity2.createComponent(MeshRendererComponent.class).setMesh(triangle);
        entity2.transform.addPos(0,0,-10);
        entity1.transform.addRot(0,90,180);

        currScene.addEntity(entity1);
        currScene.addEntity(entity2);

        while(isRunning) {

            Input.update();
            currWindow.update();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            //Render Begin
            for(Entity e: currScene.getEntities()) {
                for (Component c : e.getComponents()) {
                    c.onRender();
                }
            }
            //Render End

            if(currWindow.shouldWindowClose()) {
                stop();
            }
        }
    }

    private void end() {
        onEnd();

        endSystems();
    }

    private void init() {
        currWindow = new Window(title);

        onPreInit();

        onInit();

        onPostInit();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Timer gameTimer = new Timer();
                gameTimer.mark();
                int count = 0;
                int frameCount = 0;

                while(isRunning) {

                    if(gameTimer.getDeltaTime() >= 1f/60f) {

                        update();

                        gameTimer.mark();
                        count++;
                    }

                    if(gameTimer.getElapsedTime() - frameCount >= 1) {
                        Logger.println(count, gameTimer.getElapsedTime());
                        frameCount++;
                        count = 0;
                    }
                }
            }
        }).start();
    }

    private void update() {
        for(Entity entity: currScene.getEntities()) {
            entity.onUpdate();
        }
    }

    protected abstract void onPreInit();

    protected abstract void onPostInit();

    protected abstract void onInit();

    protected abstract void onEnd();

    private void initSystems() {
        if(!glfwInit()) {
            Logger.println("ELIXER ERROR: GLFW was not able to init. Ending.", Logger.Levels.ERROREND);
        }

        Util.init();
    }

    private void endSystems() {
        glfwTerminate();
    }

    public Window getCurrentWindow() {
        return currWindow;
    }

    public Matrix4f getProjectionMatrix() {
        Matrix4f matProxy = new Matrix4f().identity();
        matProxy.perspective(
            (float) Math.toRadians(fov / 2),
            (float) getCurrentWindow().getWidth() / (float) getCurrentWindow().getHeight(),
            nearPlane,
            farPlane);
        return matProxy;
    }

    public static Window getCurrWindow() {
        return currWindow;
    }
}