import Engine.*;
import Engine.Object;
import org.joml.*;
import org.lwjgl.opengl.GL;

import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;

public class Main {
    private Window window = new Window(1080, 1080, "Hello World");
    ArrayList<Object> objectObj = new ArrayList<>();
    ArrayList<Object> objectGround = new ArrayList<>();
    ArrayList<Object> objectTrack = new ArrayList<>();
    ArrayList<Object> objectOuterWall = new ArrayList<>();
    ArrayList<Object> objectFinishLine = new ArrayList<>();
    ArrayList<Object> objectLighthouse = new ArrayList<>();
    ArrayList<Object> objectPagar= new ArrayList<>();
    Camera camera = new Camera();
    Projection projection = new Projection(window.getWidth(), window.getHeight());
    float distance = 1f;
    float angle = 0f;
    float rotation = (float)Math.toRadians(1f);
    float move = 0.01f;
    List<Float> temp;
    int carPos = 0;
    int modeToggle = 0;
    int carPos2 = 0;
    boolean delay = false;
    int delayCounter = 0;
    boolean start = false;
    boolean malam = true;
    boolean delay2 = false;
    int delayCounter2 = 0;
    boolean delay3 = false;
    int delayCounter3 = 0;


    public void run() throws IOException {

        init();
        loop();

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() throws IOException {
        window.init();
        GL.createCapabilities();
        camera.setPosition(-1.7f, 1f, 2.5f + distance);

        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),
                "resources/model/MStad.obj"
        ));


    }

    public void input() {
        temp = objectObj.get(0).getCenterPoint();
        angle = angle % (float) Math.toRadians(360);

        if (window.isKeyPressed(GLFW_KEY_L) && !delay2) {
            malam = !malam;
            for (Object object : objectObj) {
                object.setScene(malam);
                for (Object objectChild : object.getChildObject()) {
                    objectChild.setScene(malam);
                }
            }
        }

    }

    public void loop() {
        while (window.isOpen()) {
            window.update();
            if(malam){
                glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            } else {
                glClearColor(0.0f, 0.64453125f, 1.0f, 1.0f);
            }

            GL.createCapabilities();

            input();

            if (delay){
                delayCounter++;
            }

            if (delayCounter > 30){
                delayCounter = 0;
                delay = false;
            }

            if (delay2){
                delayCounter2++;
            }

            if (delayCounter2 > 30){
                delayCounter2 = 0;
                delay2 = false;
            }

            if (delay3){
                delayCounter3++;
            }

            if (delayCounter3 > 30){
                delayCounter3 = 0;
                delay3 = false;
            }

            if (start){
                if (carPos < 900) {
                    objectObj.get(1).translateObject(0f, 0f, -0.007f);
                    carPos++;
                }

                if (900 <= carPos && carPos < 990) {
                    List<Float> temp = objectObj.get(1).getCenterPoint();
                    objectObj.get(1).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                    objectObj.get(1).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
                    objectObj.get(1).translateObject(temp.get(0), temp.get(1), temp.get(2));
                }

                if (900 <= carPos && carPos < 1250) {
                    objectObj.get(1).translateObject(0.007f, 0f, 0f);
                    carPos++;
                }

                if (1250 <= carPos && carPos < 1340) {
                    List<Float> temp = objectObj.get(1).getCenterPoint();
                    objectObj.get(1).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                    objectObj.get(1).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
                    objectObj.get(1).translateObject(temp.get(0), temp.get(1), temp.get(2));
                }

                if (1250 <= carPos && carPos < 2330) {
                    objectObj.get(1).translateObject(0f, 0f, 0.007f);
                    carPos++;
                }

                if (2330 <= carPos && carPos < 2420) {
                    List<Float> temp = objectObj.get(1).getCenterPoint();
                    objectObj.get(1).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                    objectObj.get(1).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
                    objectObj.get(1).translateObject(temp.get(0), temp.get(1), temp.get(2));
                }

                if (2330 <= carPos && carPos < 2680) {
                    objectObj.get(1).translateObject(-0.007f, 0f, 0f);
                    carPos++;
                }

                if (2680 <= carPos && carPos < 2770) {
                    List<Float> temp = objectObj.get(1).getCenterPoint();
                    objectObj.get(1).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                    objectObj.get(1).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
                    objectObj.get(1).translateObject(temp.get(0), temp.get(1), temp.get(2));
                }

                if (2680 <= carPos && carPos < 2860) {
                    objectObj.get(1).translateObject(0f, 0f, -0.007f);
                    carPos++;
                }

                if (carPos == 2860) {
                    carPos = 0;
                }
            }

            if (modeToggle > 0) {
                if (modeToggle == 1) {
                    List<Float> temp = objectObj.get(0).getCenterPoint();
                    camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
                    camera.moveBackwards(distance);
                }

                if (carPos2 < 660) {
                    objectObj.get(0).translateObject(0f, 0f, -0.01f);
                    carPos2++;
                }

                if (660 <= carPos2 && carPos2 < 750) {
                    List<Float> temp = objectObj.get(0).getCenterPoint();
                    objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                    objectObj.get(0).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
                    objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                    angle = angle - (float) Math.toRadians(1f);
                }

                if (660 <= carPos2 && carPos2 < 1000) {
                    objectObj.get(0).translateObject(0.01f, 0f, 0f);
                    carPos2++;
                }

                if (1000 <= carPos2 && carPos2 < 1090) {
                    List<Float> temp = objectObj.get(0).getCenterPoint();
                    objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                    objectObj.get(0).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
                    objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                    angle = angle - (float) Math.toRadians(1f);
                }

                if (1000 <= carPos2 && carPos2 < 1820) {
                    objectObj.get(0).translateObject(0f, 0f, 0.01f);
                    carPos2++;
                }

                if (1820 <= carPos2 && carPos2 < 1910) {
                    List<Float> temp = objectObj.get(0).getCenterPoint();
                    objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                    objectObj.get(0).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
                    objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                    angle = angle - (float) Math.toRadians(1f);
                }

                if (1820 <= carPos2 && carPos2 < 2160) {
                    objectObj.get(0).translateObject(-0.01f, 0f, 0f);
                    carPos2++;
                }

                if (2160 <= carPos2 && carPos2 < 2250) {
                    List<Float> temp = objectObj.get(0).getCenterPoint();
                    objectObj.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                    objectObj.get(0).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
                    objectObj.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                    angle = angle - (float) Math.toRadians(1f);
                }

                if (2160 <= carPos2 && carPos2 < 2320) {
                    objectObj.get(0).translateObject(0f, 0f, -0.01f);
                    carPos2++;
                }

                if (carPos2 == 2320) {
                    carPos2 = 0;
                }
            }

            // code here
            for (Object object: objectObj) {
                object.draw(camera, projection);
            }

            for (Object object: objectGround) {
                object.draw(camera, projection);
            }

            for (Object object: objectTrack){
                object.draw(camera, projection);
            }

            for (Object object: objectOuterWall){
                object.draw(camera, projection);
            }

            for (Object object: objectLighthouse){
                object.draw(camera, projection);
            }

            for (Object object: objectPagar){
                object.draw(camera, projection);
            }

            for (Object object: objectFinishLine){
                object.draw(camera, projection);
            }

            // Restore state
            glDisableVertexAttribArray(0);
            // Poll for window events.
            // The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws IOException {
        new Main().run();
    }
}
