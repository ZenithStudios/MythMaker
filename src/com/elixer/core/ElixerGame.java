package com.elixer.core;

import com.elixer.core.Display.Model.Mesh;
import com.elixer.core.Display.Shaders.ShaderProgram;
import com.elixer.core.Display.Window;
import com.elixer.core.Util.Logger;
import com.elixer.core.Util.Util;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public abstract class ElixerGame {

    private String title;
    private static Window currWindow;

    private boolean isValid;
    private boolean isRunning;

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

        Mesh mesh = new Mesh(data, new int[]{});
        glBindVertexArray(mesh.getVaoID());
        glEnableVertexAttribArray(0);

        ShaderProgram prog = new ShaderProgram("vertex.glsl", "fragment.glsl");

        while(isRunning) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            prog.use();

            glBindVertexArray(mesh.getVaoID());
            glEnableVertexAttribArray(0);

            glDrawArrays(GL_TRIANGLES, 0, 3);

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
}
