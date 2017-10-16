package com.elixer.core.Util;

import com.elixer.core.Display.Model.Mesh;

/**
 * Created by aweso on 10/10/2017.
 */
public class Primitives {

    private static float[] data1 = new float[]
            {-1.0f, -1.0f, 0.0f,
                    1.0f, -1.0f, 0.0f,
                    0.0f,  1.0f, 0.0f};
    private static int[] indecies1 = new int[]
            {2, 0, 1};

    public static final Mesh triangle = new Mesh(data1, indecies1);
}
