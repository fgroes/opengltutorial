package engineTester;

import entities.Camera;
import entities.Light;
import entities.Player;
import models.TexturedModel;
import entities.Entity;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        RawModel model = ObjLoader.loadObjModel("dragon", loader);
        TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));
        ModelTexture texture = texturedModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        TexturedModel grass = new TexturedModel(ObjLoader.loadObjModel("grassModel", loader),
                new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        TexturedModel fern = new TexturedModel(ObjLoader.loadObjModel("fern", loader),
                new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setHasTransparency(true);
        fern.getTexture().setUseFakeLighting(true);

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for (int i = 0; i < 500; i++) {
            entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 - 400,
                    0, random.nextFloat() * -600), 0, 0, 0, 1));
            entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 800 - 400,
                    0, random.nextFloat() * -600), 0, 0, 0, 0.6f));
        }

        Entity entity = new Entity(texturedModel, new Vector3f(100, 0, -100), 0, 0, 0, 1);
        entities.add(entity);
        Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1));

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        Terrain terrain1 = new Terrain(-1, -1, loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(0, -1, loader, texturePack, blendMap);

        //camera.setPosition(new Vector3f(0, 1, 0));

        RawModel bunnyModel = ObjLoader.loadObjModel("stanfordBunny", loader);
        ModelTexture bunnyTexture = new ModelTexture(loader.loadTexture("white"));
        TexturedModel stanfordBunny = new TexturedModel(bunnyModel, bunnyTexture);
        Player player = new Player(stanfordBunny, new Vector3f(100, 0, -50), 0, 180, 0, 1);
        Camera camera = new Camera(player);

        MasterRenderer renderer = new MasterRenderer();
        while (!Display.isCloseRequested()) {
            entity.increaseRotation(0, 1, 0);
            camera.move();
            player.move();
            renderer.processEntitiy(player);
            renderer.processTerrain(terrain1);
            renderer.processTerrain(terrain2);
            for (Entity e: entities) {
                renderer.processEntitiy(e);
            }
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
