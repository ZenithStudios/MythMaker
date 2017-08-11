package com.elixer.core.Entity;

import com.elixer.core.ElixerGame;
import com.elixer.core.Util.Logger;

import java.util.ArrayList;

/**
 * Created by aweso on 8/9/2017.
 */
public class Scene {

    private ArrayList<Entity> entities = new ArrayList<>();
    private String name;
    private ElixerGame parrentgame;

    public Scene(String name, ElixerGame parrentgame) {
        this.name = name;
        this.parrentgame = parrentgame;
    }

    public void addEntity(Entity entity) {
        entity.setParentScene(this);
        entities.add(entity);
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
}
