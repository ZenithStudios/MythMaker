package com.elixer.core.Entity.Components;

import com.elixer.core.Entity.Entity;
import com.elixer.core.Util.Logger;
import org.joml.Matrix4f;

/**
 * Created by aweso on 7/31/2017.
 */
public class CameraComponent extends Component {

    private static Matrix4f matProxy = new Matrix4f().identity();

    public float fov = 70;
    public float nearplane = 0.01f, farplane = 100.0f;

    public CameraComponent(Entity entity) {
        super(entity);
    }

    public Matrix4f getProjectionMatrix() {
        if(getEntity().getGame() != null) {
            matProxy.identity();
            matProxy.perspective(
                    (float) Math.toRadians(fov/2),
                    (float)getEntity().getGame().getCurrentWindow().getWidth()/(float)getEntity().getGame().getCurrentWindow().getHeight(),
                    nearplane,
                    farplane);
            return matProxy;
        }

        Logger.println("Camera is not assigned to a game object.", Logger.Levels.CAUTION);
        return matProxy;
    }
}
