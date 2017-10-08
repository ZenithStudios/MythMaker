package com.elixer.core.Util;

import org.omg.CORBA.UNKNOWN;

import static org.lwjgl.glfw.GLFW.*;

public enum Keycode {

    A(GLFW_KEY_A),
    B(GLFW_KEY_B),
    C(GLFW_KEY_C),
    D(GLFW_KEY_D),
    E(GLFW_KEY_E),
    F(GLFW_KEY_F),
    G(GLFW_KEY_G),
    H(GLFW_KEY_H),
    I(GLFW_KEY_I),
    J(GLFW_KEY_J),
    K(GLFW_KEY_K),
    L(GLFW_KEY_L),
    M(GLFW_KEY_M),
    N(GLFW_KEY_N),
    O(GLFW_KEY_O),
    P(GLFW_KEY_P),
    Q(GLFW_KEY_Q),
    R(GLFW_KEY_R),
    S(GLFW_KEY_S),
    T(GLFW_KEY_T),
    U(GLFW_KEY_U),
    V(GLFW_KEY_V),
    W(GLFW_KEY_W),
    X(GLFW_KEY_X),
    Y(GLFW_KEY_Y),
    Z(GLFW_KEY_Z),
    TAB(GLFW_KEY_TAB),
    L_SHIFT(GLFW_KEY_LEFT_SHIFT),
    L_CONTROL(GLFW_KEY_LEFT_CONTROL),
    R_SHIFT(GLFW_KEY_RIGHT_SHIFT),
    R_CONTROL(GLFW_KEY_RIGHT_CONTROL),
    UNKNOWN(-1);

    private int code;

    Keycode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Keycode getKeycode(int value) {
        Keycode val = Keycode.UNKNOWN;

        for(Keycode keycode: values()) {
            if(keycode.getCode() == value) {
                val = keycode;
            }
        }

        return val;
    }
}
