package com.elixer.core.Entity;

import com.elixer.core.Util.Ref;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

    public Vector3f position = new Vector3f(0, 0,0);
    public Vector3f scale = new Vector3f(1, 1, 1);
    public Vector3f rotation = new Vector3f(0, 0, 0);

    private Matrix4f matProxy = new Matrix4f().identity();

    public Transform() {
    }

    public Transform(Vector3f position, Vector3f scale, Vector3f rotation) {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }

    public Transform(Vector3f position) {
        this.position = position;
    }

    public Matrix4f getTransformationMatrix() {
        matProxy.identity();
        matProxy.translate(position);
        matProxy.rotate((float) Math.toRadians(rotation.x), Ref.X_AXIS);
        matProxy.rotate((float) Math.toRadians(rotation.y), Ref.Y_AXIS);
        matProxy.rotate((float) Math.toRadians(rotation.z), Ref.Z_AXIS);
        matProxy.scale(scale);
        return matProxy;
    }

    public Matrix4f getRevercedMatrix() {
        matProxy.identity();
        matProxy.translate(-position.x, -position.y, -position.z);
        matProxy.rotate((float) Math.toRadians(-rotation.x), Ref.X_AXIS);
        matProxy.rotate((float) Math.toRadians(-rotation.y), Ref.Y_AXIS);
        matProxy.rotate((float) Math.toRadians(-rotation.z), Ref.Z_AXIS);
        matProxy.scale(scale);
        return matProxy;
    }
}
