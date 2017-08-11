package com.elixer.core.Entity;

import com.elixer.core.ElixerGame;

/**
 * Created by aweso on 7/31/2017.
 */
public abstract class Component {

    private Entity entity;

    protected boolean isEnabled;

    protected Component(){}

    protected Entity getEntity() {
        return entity;
    }

    protected void setEntity(Entity entity) {
        this.entity = entity;
    }

    protected ElixerGame getGame() {
        if(entity != null) {
            return entity.getGame();
        }

        return null;
    }

    public void onUpdate() {

    }

    public void onRender(){

    }

    public void disable() {
        isEnabled = false;
    }

    public void enable() {
        isEnabled = true;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
