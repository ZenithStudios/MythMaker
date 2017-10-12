package com.elixer.core.Entity;

import com.elixer.core.ElixerGame;
import com.elixer.core.Entity.Components.Component;
import com.elixer.core.Util.Logger;
import com.elixer.core.Util.Luable;
import org.joml.Matrix4f;
import org.luaj.vm2.lib.BaseLib;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Created by aweso on 7/31/2017.
 */
public class Entity {

    public Transform transform = new Transform();

    private ArrayList<Component> components = new ArrayList<>();
    private Scene parentScene;
    private String name;

    public Entity() {
        name = "GameObject";
    }

    public Entity(String name) {
        this.name = name;
    }

    public void onUpdate(){
        transform.addRot(0, 1, 0);

        for(Component comp: components) {
            comp.onUpdate();
        }
    }

    //GETTERS
    public <T extends Component> T getComponent(Class<T> type) {
        for(Component c: components) {
            if(type.isInstance(c)) {
                return (T) c;
            }
        }

        return null;
    }

    //TODO Make better exceptions
    public <T extends Component> T createComponent(Class<T> type, Object... args) {
        T instance = null;
        Constructor<T> con = null;

        ArrayList<Class<?>> classes = new ArrayList<>();
        classes.add(Entity.class);
        for(Object obj:args) {
            classes.add(obj.getClass());
        }

        Constructor constructors[] = type.getConstructors();
        for(Constructor constructor: constructors) {
            if(constructor.getParameterCount() == classes.size()) {
                if(Arrays.equals(constructor.getParameterTypes(),classes.toArray(new Class[classes.size()]))) {
                    con = constructor;
                }
            }
        }

        if(con == null) {
            Logger.println(Logger.Levels.ERROR, "No constructor found with the given parameters for type " + type.getName());
            return null;
        }

        try {
            switch(args.length) {
                case 0:
                    instance = con.newInstance(this);
                    break;
                case 1:
                    instance = con.newInstance(this,
                            args[0].getClass().cast(args[0]));
                    break;
                case 2:
                    instance = con.newInstance(this,
                            args[0].getClass().cast(args[0]),
                            args[1].getClass().cast(args[1]));
                    break;
                case 3:
                    instance = con.newInstance(this,
                            args[0].getClass().cast(args[0]),
                            args[1].getClass().cast(args[1]),
                            args[2].getClass().cast(args[2]));
                    break;
                case 4:
                    instance = con.newInstance(this,
                            args[0].getClass().cast(args[0]),
                            args[1].getClass().cast(args[1]),
                            args[2].getClass().cast(args[2]),
                            args[3].getClass().cast(args[3]));
                    break;
                case 5:
                    instance = con.newInstance(this,
                            args[0].getClass().cast(args[0]),
                            args[1].getClass().cast(args[1]),
                            args[2].getClass().cast(args[2]),
                            args[3].getClass().cast(args[3]),
                            args[4].getClass().cast(args[4]));
                    break;
                default:
                    Logger.println(Logger.Levels.CAUTION, "Compnents can only have a maximun of 5 parameters.");
            }
            this.components.add(instance);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Logger.println(Logger.Levels.ERROR, "Component constructor is not public.");
        } catch (InvocationTargetException e) {
            Logger.println(Logger.Levels.ERROR, "Error instantiating Component of type " + type.getName() + ", check the constructor.");
            e.printStackTrace();
        }

        return instance;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public Scene getParentScene() {
        return parentScene;
    }

    public ElixerGame getGame() {
        if(parentScene == null) {
            Logger.println("Entity not attached to a scene. Returning Null.", Logger.Levels.CAUTION);
            return null;
        }

        return parentScene.getParrentgame();
    }

    public String getName() {
        return name;
    }

    public Matrix4f getTransformationMatrix() { return transform.getTransformationMatrix(); }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setParentScene(Scene parentScene) {
        this.parentScene = parentScene;
    }
}
