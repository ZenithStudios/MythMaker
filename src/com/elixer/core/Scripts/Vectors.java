package com.elixer.core.Scripts;

import com.elixer.core.Util.Logger;
import org.joml.Vector3f;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.lang.reflect.Method;

/**
 * Created by aweso on 10/14/2017.
 */
public class Vectors extends TwoArgFunction{

    public Vectors(){}

    @Override
    public LuaValue call(LuaValue callvalue, LuaValue globals) {
        LuaTable table = tableOf();
        table.set("createVector3D", new createVector3D());
        globals.set("createVector3D", new createVector3D());
        return table;
    }

    class createVector3D extends ThreeArgFunction {

        private Vector3f vector;

        public createVector3D() {
            vector = new Vector3f();
        }

        @Override
        public LuaValue call(LuaValue arg1, LuaValue arg2, LuaValue arg3) {
            vector.set(arg1.tofloat(), arg2.tofloat(), arg3.tofloat());
            return CoerceJavaToLua.coerce(vector);
        }

    }
}
