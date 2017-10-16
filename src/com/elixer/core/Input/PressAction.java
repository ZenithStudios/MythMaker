package com.elixer.core.Input;

import static org.lwjgl.glfw.GLFW.*;
public enum PressAction {
    PRESSED(GLFW_PRESS),
    RELEASED(GLFW_RELEASE),
    DOWN(GLFW_REPEAT),
    UNKNOWN(-1);

    private int code;

    PressAction(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PressAction getKeyAction(int value) {
        PressAction val = PressAction.UNKNOWN;

        for(PressAction keycode: values()) {
            if(keycode.getCode() == value) {
                val = keycode;
            }
        }

        return val;
    }
}
