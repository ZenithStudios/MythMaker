package com.elixer.core;

import com.elixer.core.Display.Renderable;
import com.elixer.core.Input.Input;
import com.elixer.core.Display.Window;
import com.elixer.core.Entity.*;
import com.elixer.core.Entity.Components.Component;
import com.elixer.core.Util.*;
import org.joml.Matrix4f;
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

    public void begin() {
        init();
        run();
        end();
    }

    public void stop() {
        isRunning = false;
    }

    private void run() {
        while(isRunning) {

            Input.update();
            currWindow.update();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            //Render Begin
            currScene.renderScene();
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

        isRunning = true;
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_POINT_SIZE);
        glEnable(GL_BLEND);
        glEnable(GL_COLOR_MATERIAL);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        currScene = instantiateScene();
        currWindow.setIcon("icons/icon1.png");

        prestart();

        start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Timer gameTimer = Ref.gameTimer;
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
                        Logger.println(count);
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

    private void start() {
        onStart();

        for (Entity entity: currScene.getEntities()) {
            for(Component component: entity.getComponents()) {
                component.onStart();
            }
        }
    }

    private void prestart() {
        onPreStart();

        for (Entity entity: currScene.getEntities()) {
            for(Component component: entity.getComponents()) {
                component.onStart();
            }
        }
    }

    protected abstract Scene instantiateScene();

    protected abstract void onStart();

    protected abstract void onPreStart();

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

    public Scene getCurrentScene() {
        return currScene;
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

    public Matrix4f getOrthoMatrix() {
        Matrix4f matrix4f = new Matrix4f().identity();
        matrix4f.setOrtho(-((float)currWindow.getWidth()/2), ((float)currWindow.getWidth()/2), -((float)currWindow.getHeight()/2), ((float)currWindow.getHeight()/2), this.nearPlane, this.farPlane);
        return matrix4f;
    }

    public static Window getCurrWindow() {
        return currWindow;
    }
}