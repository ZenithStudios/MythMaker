package com.elixer.core;

import com.elixer.core.Display.Model.Mesh;
import com.elixer.core.Display.Shaders.ShaderProgram;
import com.elixer.core.Display.Window;
import com.elixer.core.Entity.*;
import com.elixer.core.Util.Logger;
import com.elixer.core.Util.Util;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
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

        Entity entity = new Entity("Test1");
        entity.transform.position.z = -5;

        Mesh mesh = new Mesh(data, indecies);

        entity.addComponent(new MeshRemdererComponent(mesh));

        Scene scene = new Scene("TestScene", this);
        scene.addEntity(entity);
        currScene = scene;

        while(isRunning) {

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

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
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
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
