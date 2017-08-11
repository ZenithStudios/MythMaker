package com.elixer.core.Entity;

import com.elixer.core.ElixerGame;
import com.elixer.core.Util.Logger;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;

/**
 * Created by aweso on 7/31/2017.
 */
public class Entity {

    private Transform transform = new Transform();
    private ArrayList<Component> components = new ArrayList<>();
    private Scene parentScene;
    private String name;

    public Entity() {
        name = "GameObject";
    }

    public Entity(String name) {
        this.name = name;
    }

    public void addComponent(Component component) {
        component.setEntity(this);
        components.add(component);
    }

    public void addPos(float x, float y, float z) {
        transform.position.x += x;
        transform.position.y += y;
        transform.position.z += z;
    }

    public void addPos(Vector3f trans) {
        transform.position.add(trans);
    }

    //GETTERS
    public <T extends Component> T getComponent(Class<? extends Component> type) {
        for(Component c: components) {
            if(type.isInstance(c)) {
                return (T) c;
            }
        }

        return null;
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

    public Vector3f getPos() {
        return transform.position;
    }

    public Vector3f getRotation() {
        return transform.rotation;
    }

    public Vector3f getScale() {
        return transform.scale;
    }

    public Matrix4f getTransformationMatrix() { return transform.getTransformationMatrix(); }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setParentScene(Scene parentScene) {
        this.parentScene = parentScene;
    }

    public void setPos(float x, float y, float z) {
        transform.position.x = x;
        transform.position.y = y;
        transform.position.z = z;
    }

    public void setPos(Vector3f trans) {
        transform.position = trans;
    }

    public void setRot(float x, float y, float z) {
        transform.rotation.x = x;
        transform.rotation.y = y;
        transform.rotation.z = z;
    }

    public void setRot(Vector3f rot) {
        transform.rotation = rot;
    }

    public void setScale(float x, float y, float z) {
        transform.scale.x = x;
        transform.scale.y = y;
        transform.scale.z = z;
    }

    public void setScale(Vector3f scale) {
        transform.scale = scale;
    }
}
