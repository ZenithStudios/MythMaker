package com.elixer.core.Entity.Components;

import com.elixer.core.ElixerGame;
import com.elixer.core.Entity.Entity;

/**
 * Created by aweso on 7/31/2017.
 */
public abstract class Component {

    private Entity entity;

    protected boolean isEnabled;

    protected Component(Entity entity){
        this.entity = entity;
    }

    protected final Entity getEntity() {
        return entity;
    }

    protected final ElixerGame getGame() {
        if(entity != null) {
            return entity.getGame();
        }

        return null;
    }

    public void onUpdate() {}

    public void OnStart() {}

    public void OnPreStart() {}

    public void onRender(){}

    public final void disable() {
        isEnabled = false;
    }

    public final void enable() {
        isEnabled = true;
    }

    public final boolean isEnabled() {
        return isEnabled;
    }
}
