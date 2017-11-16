package com.elixer.core.Entity;

import com.elixer.core.Display.Renderable;
import com.elixer.core.ElixerGame;
import com.elixer.core.Entity.Components.Component;
import com.elixer.core.Util.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aweso on 8/9/2017.
 */
public class Scene {

    private ArrayList<Entity> entities = new ArrayList<>();
    private String name;
    private ElixerGame parrentgame;

    private ArrayList<Component> renderList = new ArrayList<>();

    public Scene(String name, ElixerGame parrentgame) {
        this.name = name;
        this.parrentgame = parrentgame;


    }

    public void addEntity(Entity... entities) {
        for(Entity entity: entities) {
            for(Component component: entity.getComponents()) {
                if(component instanceof Renderable) {
                    addRenderComponent(component);
                }
            }
            entity.setParentScene(this);
            this.entities.add(entity);
        }
    }

    public Entity getEntity(int index) {
        return entities.get(index);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ElixerGame getParrentgame() {
        if(parrentgame == null) {
            Logger.println("Scene '" + name + "' does not have a parent game. Returning null.", Logger.Levels.CAUTION);
        }

        return parrentgame;
    }

    public void setParrentgame(ElixerGame parrentgame) {
        this.parrentgame = parrentgame;
    }

    public void renderScene() {
        for(Component comp: renderList) {
            if(comp instanceof Renderable)
                ((Renderable) comp).render();
        }
    }

    private void addRenderComponent(Component component) {
        renderList.add(component);
    }
}
