package com.elixer.core.Display;

import com.elixer.core.Input.Input;
import com.elixer.core.Util.Logger;
import com.elixer.core.Util.ResourceType;
import com.elixer.core.Util.Util;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.memAllocInt;
import static org.lwjgl.system.MemoryUtil.memFree;

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
        }
    }

    public void setIcon(String path) {
        IntBuffer w = memAllocInt(1);
        IntBuffer h = memAllocInt(1);
        IntBuffer comp = memAllocInt(1);

        Path file = Util.getResource(path, ResourceType.TEXTURE);

        // Icons
        {
            ByteBuffer icon16;
            ByteBuffer icon32;
            try {
                icon16 = ioResourceToByteBuffer(file.toString(), 2048);
                icon32 = ioResourceToByteBuffer(file.toString(), 4096);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            try ( GLFWImage.Buffer icons = GLFWImage.malloc(2) ) {
                ByteBuffer pixels16 = STBImage.stbi_load_from_memory(icon16, w, h, comp, 4);
                icons
                        .position(0)
                        .width(w.get(0))
                        .height(h.get(0))
                        .pixels(pixels16);

                ByteBuffer pixels32 = STBImage.stbi_load_from_memory(icon32, w, h, comp, 4);
                icons
                        .position(1)
                        .width(w.get(0))
                        .height(h.get(0))
                        .pixels(pixels32);

                icons.position(0);
                glfwSetWindowIcon(windowID, icons);

                STBImage.stbi_image_free(pixels32);
                STBImage.stbi_image_free(pixels16);
            }
        }

        memFree(comp);
        memFree(h);
        memFree(w);

    }

    private ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if ( Files.isReadable(path) ) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);
                while ( fc.read(buffer) != -1 ) ;
            }
        } else {
            try (
                    InputStream source = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source)
            ) {
                buffer = BufferUtils.createByteBuffer(bufferSize);

                while ( true ) {
                    int bytes = rbc.read(buffer);
                    if ( bytes == -1 )
                        break;
                    if ( buffer.remaining() == 0 )
                        buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                }
            }
        }

        buffer.flip();
        return buffer;
    }

    private ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
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
