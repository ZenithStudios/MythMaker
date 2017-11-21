package com.elixer.core.Util;

import com.elixer.core.Display.Model.Mesh;
import com.elixer.core.Display.Model.MeshType;

/**
 * Created by aweso on 10/10/2017.
 */
public class Primitives {

    public static final Mesh triangle = new Mesh(MeshType.TRIANGLE)
            .addVertex(-1,-1,0,0,1,0,0,-1)
            .addVertex(0,1,0,0.5f,0,0,0,-1)
            .addVertex(1,-1,0,1,1,0,0,-1)
            .addFace(0,1,2)
            .updateMesh();

    public static final Mesh plane = new Mesh(MeshType.TRIANGLE)
            .addVertex(-1,-1,0,0,1,0,0,-1)
            .addVertex(-1,1,0,0,0,0,0,-1)
            .addVertex(1,1,0,1,0,0,0,-1)
            .addVertex(1,-1,0,1,1,0,0,-1)
            .addFace(0, 1, 2)
            .addFace(2, 3, 0)
            .updateMesh();

    public static final Mesh test = new Mesh(MeshType.POINT)
            .addVertex(-1,-1,0,0,1,0,0,-1)
            .addVertex(0,1,0,0.5f,0,0,0,-1)
            .addVertex(1,-1,0,1,1,0,0,-1)
            .addFace(0, 1, 2)
            .updateMesh();
}
