package com.elixer.core.Display;

import com.elixer.core.Input.Input;
import com.elixer.core.Util.Logger;
import com.elixer.core.Util.Luable;
import com.elixer.core.Util.Util;
import org.joml.Vector2i;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.lib.BaseLib;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
/**
 * Created by aweso on 7/20/2017.
 */
public class Window {

    private String title;
    private long windowID;
    private int width, height, posX, posY;
    private boolean isFullscreen = false, isResizable = true, shouldPosEvent = true, shouldResizeEvent = true;

    public Window(String title) {
        this.title = title;
        Vector2i monitorSize = Util.getCurrentMonitorSize();

        this.width = monitorSize.x/2;
        this.height = monitorSize.y/2;
        this.posX = (monitorSize.x/2)-(width/2);
        this.posY = (monitorSize.y/2)-(height/2);

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, 0);

        windowID = glfwCreateWindow(width, height, this.title, 0, 0);
        if(windowID == 0) {
            Logger.println("Window could not be created.", Logger.Levels.ERROR);
            return;
        }

        GLFWKeyCallback keyCallback = GLFWKeyCallback.create(Input.onKeyReference);
        GLFWCursorPosCallback mousePosCallback = GLFWCursorPosCallback.create(Input.onMousePosReference);
        GLFWMouseButtonCallback mouseButtonCallback = GLFWMouseButtonCallback.create(Input.onMouseClickReference);
        GLFWWindowSizeCallback windowSizeCallback = GLFWWindowSizeCallback.create(this::onWindowResize);
        glfwSetKeyCallback(windowID, keyCallback);
        glfwSetCursorPosCallback(windowID, mousePosCallback);
        glfwSetMouseButtonCallback(windowID, mouseButtonCallback);
        glfwSetWindowSizeCallback(windowID, windowSizeCallback);

        glfwSetWindowPos(windowID, posX, posY);

        glfwMakeContextCurrent(windowID);
        glfwSwapInterval(1);

        glfwShowWindow(windowID);
    }

    public void update() {
        glfwSwapBuffers(windowID);
        glfwPollEvents();

        GL11.glViewport(0, 0, width, height);
    }

    private void onWindowResize(long window, int width, int height) {
        if(shouldResizeEvent) {
            this.width = width;
            this.height = height;

            Logger.println(getWidth() + " | " + getHeight());
        }
    }

    public boolean shouldWindowClose() {
        return glfwWindowShouldClose(windowID);
    };

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getWindowID() {
        return windowID;
    }
}
