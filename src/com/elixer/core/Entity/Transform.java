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
        matProxy.rotate((float) Math.toRadians(-rotation.x), Ref.X_AXIS);
        matProxy.rotate((float) Math.toRadians(-rotation.y), Ref.Y_AXIS);
        matProxy.rotate((float) Math.toRadians(-rotation.z), Ref.Z_AXIS);
        matProxy.translate(-position.x, -position.y, -position.z);
        matProxy.scale(scale);
        return matProxy;
    }

    //ADDERS
    public void addPos(float x, float y, float z) {
        position.add(x, y, z);
    }

    public void addPos(Vector3f trans) {
        position.add(trans);
    }

    public void addRot(float x, float y, float z) {
        rotation.add(x, y, z);
    }

    public void addRot(Vector3f rot) {
        rotation.add(rot);
    }

    public void addScale(float x, float y, float z) {
        scale.add(x, y, z);
    }

    public void addScale(Vector3f scale) {
        scale.add(scale);
    }

    //SETTERS
    public void setPos(float x, float y, float z) {
        position.set(x, y, z);
    }

    public void setPos(Vector3f trans) {
        position = trans;
    }

    public void setRot(float x, float y, float z) {
        rotation.set(x, y, z);
    }

    public void setRot(Vector3f rot) {
        rotation = rot;
    }

    public void setScale(float x, float y, float z) {
        scale.set(x, y, z);
    }

    public void setScale(Vector3f scale) {
        scale = scale;
    }

    //GETTERS
    public Vector3f getPos() {
        return position;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Vector3f getRot() {
        return rotation;
    }

    public float distance(Transform transform) {
        Vector3f pos = transform.position;
        return (float) Math.sqrt(Math.pow(pos.x, 2) + Math.pow(pos.y, 2) + Math.pow(pos.z, 2));
    }
}
