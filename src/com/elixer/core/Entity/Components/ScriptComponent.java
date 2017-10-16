package com.elixer.core.Entity.Components;

import com.elixer.core.Entity.Entity;
import com.elixer.core.Util.Logger;
import com.elixer.core.Util.ResourceType;
import com.elixer.core.Util.Util;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

import javax.script.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aweso on 8/14/2017.
 */
public class ScriptComponent extends Component {

    private String scriptTitle;

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
        this.scriptTitle = name;

        manager = new ScriptEngineManager();
        engine = manager.getEngineByName("luaj");
        mainBinding = engine.createBindings();

        applyConstants();
        setScript(name, mainBinding);
    }

    @Override
    public void onUpdate() {
        applyFields();

        if(script != null && onUpdate != null) {
            try {
                onUpdate.call();
            } catch (Exception e) {
                this.disable();
                Logger.println(Logger.Levels.ERROR, "Script runtime error: " + e.getMessage());
            }
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

    private void applyConstants() {
        mainBinding.put("Vectors", "com.elixer.core.Scripts.Vectors");
        mainBinding.put("MeshRendererComponent", CoerceJavaToLua.coerce(MeshRendererComponent.class));

        mainBinding.put("log", new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs args) {
                ArrayList<String> messages = new ArrayList<>();
                for(int i = 1; i < args.narg()+1; i++) {
                    messages.add(args.arg(i).tojstring());
                }
                Logger.println(messages.toArray(new String[messages.size()]));
                return NIL;
            }
        });

        mainBinding.put("entity", CoerceJavaToLua.coerce(getEntity()));
    }

    private void setScript(String name, Bindings bindings) {
        try {
            script = ((Compilable)engine).compile(new FileReader(Util.getResource(name, ResourceType.SCRIPT_LUA).toFile()));
            script.eval(bindings);
        } catch (ScriptException e1) {
            Logger.println(Logger.Levels.ERROR, "Starting Error in script: " + e1.getMessage());
        } catch (FileNotFoundException e) {
            Logger.println(Logger.Levels.ERROR, "Could not find script '" + this.scriptTitle + "'.");
        }

        onUpdate = (LuaFunction) mainBinding.get("onUpdate");
        onStart = (LuaFunction) mainBinding.get("onStart");
        onPreStart = (LuaFunction) mainBinding.get("onPreStart");

        saveFields();
    }

    public String getScriptTitle() {
        return scriptTitle;
    }
}