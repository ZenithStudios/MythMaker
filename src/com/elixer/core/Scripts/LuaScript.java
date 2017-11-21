package com.elixer.core.Scripts;

import com.elixer.core.Entity.Components.MeshRendererComponent;
import com.elixer.core.Util.Logger;
import com.elixer.core.Util.Ref;
import com.elixer.core.Util.ResourceType;
import com.elixer.core.Util.Util;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import javax.script.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aweso on 11/11/2017.
 */
public class LuaScript {

    private Path file;

    private ScriptEngineManager manager;
    private ScriptEngine engine;
    private CompiledScript script;

    private Bindings mainBinding;

    public LuaScript(String file) {
        this.file = Util.getResource(file, ResourceType.SCRIPT_LUA);

        manager = new ScriptEngineManager();
        engine = manager.getEngineByName("luaj");
        mainBinding = engine.createBindings();

        applyConstants();
        setScript(file, mainBinding);
    }

    private void applyConstants() {
        mainBinding.put("ElixerScript", "com.elixer.core.Scripts.ElixerScript");
        mainBinding.put("gameTimer", CoerceJavaToLua.coerce(Ref.gameTimer));

        mainBinding.put("clamp", new ThreeArgFunction() {
            @Override
            public LuaValue call(LuaValue arg1, LuaValue arg2, LuaValue arg3) {
                return LuaValue.valueOf(Util.clampi(arg1.toint(), arg2.toint(), arg3.toint()));
            }
        });

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
    }

    public void addConstant(String name, Object value) {
        mainBinding.put(name, value);
    }

    public void run() {
        try {
            script.eval(mainBinding);
        } catch (ScriptException e) {
            Logger.println(Logger.Levels.ERROR, "Starting Error in script: " + e.getMessage());
        }
    }

    public HashMap<String, Object> getFields() {
        HashMap<String, Object> fields = new HashMap<>();

        for(String fieldName: mainBinding.keySet()) {
            Object field = mainBinding.get(fieldName);

            if(field.getClass() != LuaClosure.class) {
                fields.put(fieldName, field);
            }
        }

        return fields;
    }

    public void applyFields(HashMap<String, Object> fields) {
        for(HashMap.Entry<String, Object> field: fields.entrySet()) {
            mainBinding.put(field.getKey(), field.getValue().getClass().cast(field.getValue()));
        }
    }

    public LuaFunction getFunction(String name) {
        return (LuaFunction) mainBinding.get(name);
    }

    private void setScript(String name, Bindings bindings) {
        try {
            script = ((Compilable)engine).compile(new FileReader(file.toFile()));
            script.eval(bindings);
        } catch (ScriptException e1) {
            Logger.println(Logger.Levels.ERROR, "Starting Error in script: " + e1.getMessage());
        } catch (FileNotFoundException e) {
            Logger.println(Logger.Levels.ERROR, "Could not find script '" + file.getFileName().toString() + "'.");
        }
    }
}
