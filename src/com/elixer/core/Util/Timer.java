package com.elixer.core.Util;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by aweso on 10/8/2017.
 */
public class Timer {

    private double startTime;
    private double lastMark = -1;

    public Timer() {
        Logger.println("timer start");
        startTime = glfwGetTime();
    }

    public double getElapsedTime() {
        return glfwGetTime() - startTime;
    }

    public double getDeltaTime() {
        if(isMarked()) {
            return glfwGetTime() - lastMark;
        } else {
            Logger.println("No mark on timer!");
        }

        return 0;
    }

    public void mark() {
        lastMark = glfwGetTime();
    }

    public double getStartTime() {
        return startTime;
    }

    public boolean isMarked() {
        if(lastMark >= 0) {
            return true;
        }

        return false;
    }
}
