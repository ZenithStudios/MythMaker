package com.elixer.core;

import com.elixer.core.Display.Model.Mesh;
import com.elixer.core.Display.Window;
import com.elixer.core.Entity.*;
import com.elixer.core.Util.Logger;
import com.elixer.core.Util.Util;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public abstract class ElixerGame {

    public Transform camPos = new Transform();

    protected String title;
    protected Window currWindow;

    private float fov = 75f, nearPlane = 0.01f, farPlane = 1000f;
    private boolean isRunning;
    private Scene currScene;

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

        float[] data = new float[]
              {-1.0f, -1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                0.0f,  1.0f, 0.0f};
        int[] indecies = new int[]
              {2, 0, 1};

        Mesh mesh = new Mesh(data, indecies);

        Entity entity01 = new Entity("Test1");
        Entity entity02 = new Entity("Test2");
        Entity entity03 = new Entity("Test1");
        Entity entity04 = new Entity("Test2");

        entity01.createComponent(MeshRemdererComponent.class).setMesh(mesh);
        entity02.createComponent(MeshRemdererComponent.class).setMesh(mesh);
        entity03.createComponent(MeshRemdererComponent.class).setMesh(mesh);
        entity04.createComponent(MeshRemdererComponent.class).setMesh(mesh);

        entity01.transform.addPos(0, 0, -3);
        entity02.transform.addPos(0, 0, 3);
        entity03.transform.addPos(3, 0, 0);
        entity04.transform.addPos(-3, 0, 0);

        entity03.transform.addRot(0, 90, 0);
        entity04.transform.addRot(0, 90, 0);

        Scene scene = new Scene("TestScene", this);
        scene.addEntity(entity01);
        scene.addEntity(entity02);
        scene.addEntity(entity03);
        scene.addEntity(entity04);

        currScene = scene;

        while(isRunning) {

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            camPos.rotation.add(new Vector3f(0, 1, 0));

            //Render Begin

            for(Entity e: currScene.getEntities()) {
                for (Component c : e.getComponents()) {
                    c.onRender();
                }
            }

            //Render End

            currWindow.update();

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


}
