package com.elixer.core;

import com.elixer.core.Display.Window;
import com.elixer.core.Util.Logger;

import static org.lwjgl.glfw.GLFW.*;
/**
 * Created by aweso on 7/20/2017.
 */
public abstract class ElixerGame {

    private String title;
    private static Window currWindow;

    private boolean isValid;
    private boolean isRunning;

    public static void main(String[] args) {

    }

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

        while(isRunning) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Logger.println("Test for Errors.", Logger.Levels.ERROREND);
        }
    }

    private void end() {
        endSystems();
    }

    private void init() {
        onPreInit();

        onInit();

        onPostInit();
    }

    protected abstract void onPreInit();

    protected abstract void onPostInit();

    protected abstract void onInit();

    private void initSystems() {
        if(!glfwInit()) {
            throw new IllegalStateException("GLFW failed to init.");
        }
    }

    private void endSystems() {
        glfwTerminate();
    }
}
