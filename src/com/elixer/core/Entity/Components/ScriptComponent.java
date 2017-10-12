package com.elixer.core.Entity.Components;

import com.elixer.core.Entity.Entity;
import com.elixer.core.Util.Logger;
import com.elixer.core.Util.ResourceType;
import com.elixer.core.Util.Util;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import javax.script.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aweso on 8/14/2017.
 */
public class ScriptComponent extends Component {

    private String name;

    private ScriptEngineManager manager;
    private ScriptEngine engine;
    private CompiledScript script;

    //Lua functions
    private LuaFunction onUpdate;
    private LuaFunction onStart;
    private LuaFunction onPreStart;

    HashMap<String, Object> fields = new HashMap<>();

    private Bindings mainBinding;

    public ScriptComponent(Entity entity, String name) {
        super(entity);
        this.name = name;

        manager = new ScriptEngineManager();
        engine = manager.getEngineByName("luaj");
        mainBinding = engine.createBindings();

        mainBinding.put("switch", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                setScript(arg.tojstring(), mainBinding);
                return NIL;
            }
        });

        setScript(name, mainBinding);
    }

    @Override
    public void onUpdate() {
        applyFields();

        if(script != null && onUpdate != null) {
            onUpdate.call();
        }

        saveFields();
    }

    @Override
    public void OnStart() {
        applyFields();

        if(script != null && onStart != null) {
            onStart.call();
        }

        saveFields();
    }

    @Override
    public void OnPreStart() {
        applyFields();

        if(script != null && onPreStart != null) {
            onPreStart.call();
        }

        saveFields();
    }

    private void saveFields() {
        fields.clear();
        for(String fieldName: mainBinding.keySet()) {
            Object field = mainBinding.get(fieldName);

            if(field.getClass() != LuaClosure.class) {
                fields.put(fieldName, field);
            }
        }
    }

    private void applyFields() {
        for(HashMap.Entry<String, Object> field: fields.entrySet()) {
            mainBinding.put(field.getKey(), field.getValue().getClass().cast(field.getValue()));
        }
    }

    private void setScript(String name, Bindings bindings) {
        try {
            script = ((Compilable)engine).compile(new FileReader(Util.getResource(name, ResourceType.SCRIPT_LUA).toFile()));
            script.eval(bindings);
        } catch (ScriptException e1) {
            Logger.println(Logger.Levels.ERROR, "Starting Error in script: " + e1.getMessage());
        } catch (FileNotFoundException e) {
            Logger.println(Logger.Levels.ERROR, "Could not find script '" + this.name + "'.");
        }

        onUpdate = (LuaFunction) mainBinding.get("onUpdate");
        onStart = (LuaFunction) mainBinding.get("onStart");
        onPreStart = (LuaFunction) mainBinding.get("onPreStart");

        saveFields();
    }
}