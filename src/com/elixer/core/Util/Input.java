package com.elixer.core.Util;

import com.elixer.core.ElixerGame;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import java.util.ArrayList;

/**
 * Created by aweso on 9/19/2017.
 */
public class Input {

    private static Vector2d mousePos = new Vector2d();
    private static Vector2d lastMousePos = new Vector2d();
    private static Vector2d mouseDelta = new Vector2d();

    public static GLFWMouseButtonCallbackI onMouseClickReference = Input::onMouseClick;
    public static GLFWCursorPosCallbackI onMousePosReference = Input::onMousePosCallback;
    public static GLFWKeyCallbackI onKeyReference = Input::onKeyCallback;

    public static void update() {
        if(ElixerGame.getCurrWindow() != null) {

        }
    }

    private static void onMouseClick(long window, int button, int action, int mods) {
        if(window == ElixerGame.getCurrWindow().getWindowID()) {

        }
    }

    private static void onMousePosCallback(long window, double xpos, double ypos) {
        if(window == ElixerGame.getCurrWindow().getWindowID()) {
            lastMousePos = mousePos;
            mousePos.set(xpos, ypos);
        }
    }

    private static void onKeyCallback(long window, int key, int scancode, int action, int mods) {
        if(window == ElixerGame.getCurrWindow().getWindowID()) {

        }
    }

    public static boolean mouseState(MouseButton button, PressAction action) {

        return false;
    }

    public static Vector2d getMousePos() {
        return mousePos;
    }

    public static Vector2d getMouseDelta() {
        return mouseDelta;
    }
}
