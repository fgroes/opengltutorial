package terrains;

import com.sun.org.apache.xpath.internal.operations.Mod;
import models.RawModel;
import renderEngine.Loader;
import textures.ModelTexture;

public class Terrain {

    private static final float SIZE = 800;
    private static final int VERTEX_COUNT = 128;

    private float x;
    private float z;
    private RawModel model;
    private ModelTexture texture;

    public Terrain(int gridX, int gridZ, Loader loader, ModelTexture texture) {
        this.texture = texture;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
    }
}
