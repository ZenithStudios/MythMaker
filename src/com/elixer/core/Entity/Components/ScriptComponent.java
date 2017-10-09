package com.elixer.core.Entity.Components;

import com.elixer.core.Entity.Entity;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * Created by aweso on 8/14/2017.
 */
public class ScriptComponent extends Component {

    Globals globals = JsePlatform.standardGlobals();

    public ScriptComponent(Entity entity) {
        super(entity);

    }
}
