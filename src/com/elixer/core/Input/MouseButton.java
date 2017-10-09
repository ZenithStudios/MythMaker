package com.elixer.core.Input;

import static org.lwjgl.glfw.GLFW.*;

public enum MouseButton {
    MACRO_1(GLFW_MOUSE_BUTTON_1),
    MACRO_2(GLFW_MOUSE_BUTTON_2),
    MACRO_3(GLFW_MOUSE_BUTTON_3),
    MACRO_4(GLFW_MOUSE_BUTTON_4),
    MACRO_5(GLFW_MOUSE_BUTTON_5),
    MACRO_6(GLFW_MOUSE_BUTTON_6),
    MACRO_7(GLFW_MOUSE_BUTTON_7),
    MACRO_8(GLFW_MOUSE_BUTTON_8),
    LEFT(GLFW_MOUSE_BUTTON_LEFT),
    RIGHT(GLFW_MOUSE_BUTTON_RIGHT),
    MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE),
    UNKNOWN(-1);

    private int code;

    MouseButton(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static MouseButton toMouseButton(int i) {
        MouseButton val = MouseButton.UNKNOWN;

        for(MouseButton mouseButton: values()) {
            if(mouseButton.getCode() == i) {
                return mouseButton;
            }
        }

        return val;
    }
}
