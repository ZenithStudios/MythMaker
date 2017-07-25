package com.elixer.core.Util;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;

public class Util {

    private static GLFWVidMode currMonitor = null;

    public static void init() {
        currMonitor = glfwGetVideoMode(glfwGetPrimaryMonitor());
    }

    public static Vector2i getCurrentMonitorSize() {
        Vector2i size = new Vector2i();

        if(currMonitor != null) {
            size.set(currMonitor.width(), currMonitor.height());
        } else {
            Logger.println("Can not get current monitor size. Util was not initialized.", Logger.Levels.ERROR);
        }

        return size;
    }

    public static FloatBuffer getFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
