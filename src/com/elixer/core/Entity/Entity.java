package com.elixer.core.Entity;

import com.elixer.core.ElixerGame;
import com.elixer.core.Util.Logger;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

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

    //GETTERS
    public <T extends Component> T getComponent(Class<T> type) {
        for(Component c: components) {
            if(type.isInstance(c)) {
                return (T) c;
            }
        }

        return null;
    }

    public <T extends Component> T createComponent(Class<T> type) {
        T instance = null;

        try {
            Constructor<T> con = type.getConstructor(Entity.class);
            instance = con.newInstance(this);
            this.components.add(instance);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
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
