package com.elixer.core.Scripts;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.LibFunction;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * Created by aweso on 10/16/2017.
 */
public class ElixerScript extends TwoArgFunction {

    @Override
    public LuaValue call(LuaValue callArg, LuaValue globals) {
        LuaValue table = tableOf();
        globals.set("vec2", new vector2());
        globals.set("vec3", new vector3());
        globals.set("vec4", new vector4());
        return table;
    }

    class vector2 extends TwoArgFunction {
        @Override
        public LuaValue call(LuaValue arg1, LuaValue arg2) {
            return CoerceJavaToLua.coerce(new Vector2f(arg1.tofloat(), arg2.tofloat()));
        }
    }

    class vector3 extends ThreeArgFunction {
        @Override
        public LuaValue call(LuaValue arg1, LuaValue arg2, LuaValue arg3) {
            return CoerceJavaToLua.coerce(new Vector3f(arg1.tofloat(), arg2.tofloat(), arg3.tofloat()));
        }
    }

    class vector4 extends LibFunction {
        @Override
        public Varargs invoke(Varargs args) {
            return CoerceJavaToLua.coerce(new Vector4f(args.arg(1).tofloat(), args.arg(2).tofloat(), args.arg(3).tofloat(), args.arg(4).tofloat()));
        }
    }
}
