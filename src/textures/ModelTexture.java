package textures;

public class ModelTexture {

    private int textureId;

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    private float shineDamper = 1;
    private float reflectivity = 0;

    public boolean isHasTransparency() {
        return hasTransparency;
    }

    public void setHasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }

    private boolean hasTransparency = false;

    public boolean isUseFakeLighting() {
        return useFakeLighting;
    }

    public void setUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
    }

    private boolean useFakeLighting = false;

    public ModelTexture(int id) {
        this.textureId = id;
    }

    public int getId() {
        return this.textureId;
    }
}
