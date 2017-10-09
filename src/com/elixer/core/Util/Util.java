package com.elixer.core.Util;

import org.joml.Vector2i;
import org.luaj.vm2.LuaValue;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public static IntBuffer getIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static Path getResource(String fileName, ResourceType type) {
        if(!type.legal(fileName)) {
            Logger.println("File extention not supported: ." + fileName.split("\\.")[1] + "  Skipping Resource and returning null.");
            return null;
        }

        Path path = Paths.get(".","res", type.getDir(), fileName);

        if(!path.toFile().exists()) {
            Logger.println("Could not find file " + fileName + " at dir " + path.toFile().getAbsolutePath());
            return null;
        }

        return path;
    }
}
