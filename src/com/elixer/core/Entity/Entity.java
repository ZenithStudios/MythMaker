package com.elixer.core.Entity;

import com.elixer.core.ElixerGame;
import com.elixer.core.Util.Logger;

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

    public void addComponent(Component component) {
        component.setEntity(this);
        components.add(component);
    }

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

    public void setParentScene(Scene parentScene) {
        this.parentScene = parentScene;
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

    public void setName(String name) {
        this.name = name;
    }
}
