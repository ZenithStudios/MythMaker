package com.elixer.core.Display;

import com.elixer.core.Util.Logger;
import com.elixer.core.Util.Util;
import org.joml.Vector2i;

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

        glfwSetWindowPos(windowID, posX, posY);

        glfwMakeContextCurrent(windowID);
        glfwSwapInterval(1);

        glfwShowWindow(windowID);
    }

    public void update() {
        glfwSwapBuffers(windowID);
        glfwPollEvents();
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
}
