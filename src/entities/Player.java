package entities;

import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;


public class Player extends Entity {

    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;
    private static final float TERRAIN_HEIGHT = 0;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private boolean isJumping = false;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move() {
        checkInputs();
        float frameTimeSeconds = DisplayManager.getFrameTimeSeconds();
        float rotation = currentTurnSpeed * frameTimeSeconds;
        super.increaseRotation(0, rotation, 0);
        float distance = currentSpeed * frameTimeSeconds;
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
        upwardsSpeed += GRAVITY * frameTimeSeconds;
        super.increasePosition(0, upwardsSpeed * frameTimeSeconds, 0);
        if (super.getPosition().y < TERRAIN_HEIGHT) {
            upwardsSpeed = 0;
            isJumping = false;
            super.getPosition().y = TERRAIN_HEIGHT;
        }
    }

    public void jump() {
        if (!isJumping) {
            this.upwardsSpeed = JUMP_POWER;
            isJumping = true;
        }
    }

    public void checkInputs() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.currentSpeed = RUN_SPEED;
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.currentSpeed = - RUN_SPEED;
        }
        else  {
            this.currentSpeed = 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.currentTurnSpeed = - TURN_SPEED;
        }
        else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.currentTurnSpeed = TURN_SPEED;
        }
        else {
            this.currentTurnSpeed = 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            jump();
        }
    }
}
