package com.elixer.core.Entity.Components;

import com.elixer.core.Display.Renderable;
import com.elixer.core.ElixerGame;
import com.elixer.core.Entity.Entity;
import com.elixer.core.Util.Logger;
import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by aweso on 7/31/2017.
 */
public abstract class Component {

    private Entity entity;

    protected boolean isEnabled = true;

    protected Component(Entity entity){
        this.entity = entity;
    }

    public final Entity getEntity() {
        return entity;
    }

    protected final ElixerGame getGame() {
        if(entity != null) {
            return entity.getGame();
        }

        return null;
    }

    public void onUpdate() {}

    public void onStart() {}

    public void onPreStart() {}

    public void onEnd() {};

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
