package engineTester;

import entities.Camera;
import entities.Light;
import models.TexturedModel;
import entities.Entity;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;


public class MainGameLoop {

    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();

        RawModel model = ObjLoader.loadObjModel("dragon", loader);
        TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));
        ModelTexture texture = texturedModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -50), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1));

        Terrain terrain1 = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("grass1")));
        Terrain terrain2 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass1")));

        Camera camera = new Camera();
        camera.setPosition(new Vector3f(0, 1, 0));

        MasterRenderer renderer = new MasterRenderer();
        while (!Display.isCloseRequested()) {
            entity.increaseRotation(0, 1, 0);
            camera.move();
            renderer.processTerrain(terrain1);
            renderer.processTerrain(terrain2);

            renderer.processEntitiy(entity);
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
