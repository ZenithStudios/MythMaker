package com.elixer.core.Entity.Components;

import com.elixer.core.Entity.Entity;
import com.elixer.core.Scripts.LuaScript;
import com.elixer.core.Util.Logger;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.LuaValue;

import java.util.HashMap;

/**
 * Created by aweso on 8/14/2017.
 */
public class ScriptComponent extends Component {

    private LuaScript script;

    //Lua functions
    private LuaFunction onUpdate;
    private LuaFunction onStart;
    private LuaFunction onPreStart;

    HashMap<String, Object> fields = new HashMap<>();

    public ScriptComponent(Entity entity, String file) {
        super(entity);
        script = new LuaScript(file);

        this.onUpdate = script.getFunction("onUpdate");
        this.onPreStart = script.getFunction("onPreStart");
        this.onStart = script.getFunction("onStart");

        script.addConstant("entity", CoerceJavaToLua.coerce(getEntity()));
        script.addConstant("getGame", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return CoerceJavaToLua.coerce(getGame());
            }
        });

        fields = script.getFields();
    }

    @Override
    public void onUpdate() {
        script.applyFields(fields);

        if(script != null && onUpdate != null) {
            try {
                onUpdate.call();
            } catch (Exception e) {
                this.disable();
                Logger.println(Logger.Levels.ERROR, "Script runtime error: " + e.getMessage());
            }
        }

        fields = script.getFields();
    }

    @Override
    public void onStart() {
        script.applyFields(fields);

        if(script != null && onStart != null) {
            onStart.call();
        }

        fields = script.getFields();
    }

    @Override
    public void onPreStart() {
        script.applyFields(fields);

        if(script != null && onPreStart != null) {
            onPreStart.call();
        }

        fields = script.getFields();
    }
}