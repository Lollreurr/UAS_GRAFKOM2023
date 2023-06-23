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
    ArrayList<Object> objects = new ArrayList<>();
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

    boolean pressed = false;
    boolean pressed1 = false;

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
        camera.setPosition(0.7f, 1f, 2.5f + distance);

        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1.0f, 0.0f, 0.0f, 1.0f),
                "resources/model/Bowser/bowser.obj",
                new Vector3f(0f,0f,0f)
        ));
        objects.get(0).scaleObject(0.01f,0.01f,0.01f);
        objects.get(0).translateObject(0f,0f,0f);
        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
                "resources/model/files/MStad.obj",
                new Vector3f(0f,0f,0f)
        ));
        objects.get(1).translateObject(0f,-2f, 0f);

        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0f, 1.0f, 0.0f, 1.0f),
                "resources/model/Bowser Jr/bowser2.obj",
                new Vector3f(0f,0f,0f)
        ));
        objects.get(2).scaleObject(0.1f,0.1f,0.1f);

    }

    public void setPos(){
        Vector3f pos = objects.get(0).model.transformPosition(new Vector3f());

        ArrayList<Vector3f> vertices = new ArrayList<>(List.of());

        for(double i=0;i<360;i+=360/360){
            float x = (float)(pos.x + 0.8f*Math.sin(Math.toRadians(i)));
            float z = (float)(pos.z + 0.8f*Math.cos(Math.toRadians(i)));
            vertices.add(new Vector3f(x, pos.y, z));
        }

        camera.setPosition(vertices.get((int)rotation).x,vertices.get((int)rotation).y, vertices.get((int)rotation).z);
    }

    public void input() {

        if (window.getMouseInput().isLeftButtonPressed()){
            Vector2f displVec = window.getMouseInput().getDisplVec();
            camera.addRotation((float) Math.toRadians(displVec.x * 0.1f), (float) Math.toRadians(displVec.y * 0.1f));
        }

        if (window.getMouseInput().getScroll().y != 0){
            projection.setFOV(projection.getFOV() - (window.getMouseInput().getScroll().y * 0.1f));
            window.getMouseInput().setScroll(new Vector2f());
        }

        temp = objects.get(0).getCenterPoint();
        angle = angle % (float) Math.toRadians(360);

        if (window.isKeyPressed(GLFW_KEY_L) && !delay2) {
            malam = !malam;
            for (Object object : objects) {
                object.setScene(malam);
                for (Object objectChild : object.getChildObject()) {
                    objectChild.setScene(malam);
                }
            }
        }

        if (window.isKeyPressed(GLFW_KEY_W)){
            camera.moveForward(move);
        }
        else if (window.isKeyPressed(GLFW_KEY_S)){
            camera.moveBackwards(move);
        }

        if (window.isKeyPressed(GLFW_KEY_A)){
            camera.moveLeft(move);
        }
        else if (window.isKeyPressed(GLFW_KEY_D)){
            camera.moveRight(move);
        }

        if (window.isKeyPressed(GLFW_KEY_Q)){
            camera.moveUp(move);
        }
        else if (window.isKeyPressed(GLFW_KEY_E)){
            camera.moveDown(move);
        }

        if (window.isKeyPressed(GLFW_KEY_LEFT)){
            objects.get(0).translateObject(-0.001f, 0.0f, 0.0f);
            setPos();
        }

        if (window.isKeyPressed(GLFW_KEY_RIGHT)){
            objects.get(0).translateObject(0.001f, 0.0f, 0.0f);
            setPos();
        }

        if (window.isKeyPressed(GLFW_KEY_UP)){
            objects.get(0).translateObject(0.0f, 0.0f, -0.001f);
            setPos();
        }

        if (window.isKeyPressed(GLFW_KEY_DOWN)){
            objects.get(0).translateObject(0.0f, 0.0f, 0.001f);
            setPos();
        }

        if (window.isKeyPressed(GLFW_KEY_P)){
            objects.get(0).translateObject(0.0f, 0.001f, 0.0f);
            setPos();
        }

        if (window.isKeyPressed(GLFW_KEY_O)){
            objects.get(0).translateObject(0.0f, -0.001f, 0.0f);
            setPos();
        }


        float movee = 1f;
        if (window.isKeyPressed(GLFW_KEY_M)){
            pressed = true;
        }

        if (pressed){
            float posx = camera.getPosition().x;
            float posy = camera.getPosition().y;
            float posz = camera.getPosition().z;

            camera.setPosition(-posx, -posy, -posz);
            camera.addRotation(0.0f, (float) Math.toRadians(movee));
            camera.setPosition(posx, posy, posz);

            rotation += movee;

            if (rotation >= 360.0f){
                rotation = 0.0f;
                pressed = false;
            }
        }

        if (window.isKeyPressed(GLFW_KEY_B)){
            move = 1f;
            Vector3f pos = objects.get(0).model.transformPosition(new Vector3f());
            Vector3f posCam = camera.getPosition();

            ArrayList<Vector3f> vertices = new ArrayList<>(List.of());

            for(double i=0;i<360;i+=360/360){
                float x = (float)(pos.x + 0.5f*Math.sin(Math.toRadians(i)));
                float z = (float)(pos.z + 0.5f*Math.cos(Math.toRadians(i)));
                vertices.add(new Vector3f(x, pos.y, z));
            }

            camera.setPosition(vertices.get(0).x, vertices.get(0).y, vertices.get(0).z);

            camera.setPosition(-posCam.x, 0, -posCam.z);
            camera.addRotation(0.0f, (float) Math.toRadians(-move));
            camera.setPosition(posCam.x, 0, posCam.z);

            rotation += move;

            if (rotation > 359.0f){
                rotation = 0.0f;
            }

            camera.setPosition(vertices.get((int)rotation).x,vertices.get((int)rotation).y, vertices.get((int)rotation).z );
        }

//        if (window.isKeyPressed(GLFW_KEY_W)) {
//            objects.get(0).translateObject(0.0f, move, 0.0f);
//            Vector3f posObj = objects.get(0).model.transformPosition(new Vector3f());
//
//            ArrayList<Vector3f> verticesK = new ArrayList<>(List.of());
//
//            for(float i = 0;i<360;i+=1) {
//                float x = (float) (posObj.x + 2f * Math.sin(Math.toRadians(i)));
//                float z = (float) (posObj.z + 2f * Math.cos(Math.toRadians(i)));
//                float y =(float) posObj.y+0.3f;
//                verticesK.add(new Vector3f(x, y, z));
//            }
//            camera.setPosition(verticesK.get(0).x, verticesK.get(0).y, verticesK.get(0).z);
//
//            if (rotation >= 360.0) {
//                rotation = 0.0f;
//            }
//            camera.setPosition(verticesK.get((int)rotation).x,verticesK.get((int)rotation).y, verticesK.get((int)rotation).z);
//        }
//        if (window.isKeyPressed(GLFW_KEY_S)) {
//            objects.get(0).translateObject(0.0f, -move, 0.0f);
//            Vector3f posObj = objects.get(0).model.transformPosition(new Vector3f());
//
//            ArrayList<Vector3f> verticesK = new ArrayList<>(List.of());
//
//            for(float i = 0;i<360;i+=1) {
//                float x = (float) (posObj.x + 2f * Math.sin(Math.toRadians(i)));
//                float z = (float) (posObj.z + 2f * Math.cos(Math.toRadians(i)));
//                float y =(float) posObj.y+0.3f;
//                verticesK.add(new Vector3f(x, y, z));
//            }
//            camera.setPosition(verticesK.get(0).x, verticesK.get(0).y, verticesK.get(0).z);
//
//            if (rotation >= 360.0) {
//                rotation = 0.0f;
//            }
//            camera.setPosition(verticesK.get((int)rotation).x,verticesK.get((int)rotation).y, verticesK.get((int)rotation).z);
//        }
//        if (window.isKeyPressed(GLFW_KEY_A)) {
//            objects.get(0).translateObject(-move, 0.0f, 0.0f);
//            Vector3f posObj = objects.get(0).model.transformPosition(new Vector3f());
//
//            ArrayList<Vector3f> verticesK = new ArrayList<>(List.of());
//
//            for(float i = 0;i<360;i+=1) {
//                float x = (float) (posObj.x + 2f * Math.sin(Math.toRadians(i)));
//                float z = (float) (posObj.z + 2f * Math.cos(Math.toRadians(i)));
//                float y =(float) posObj.y+0.3f;
//                verticesK.add(new Vector3f(x, y, z));
//            }
//            camera.setPosition(verticesK.get(0).x, verticesK.get(0).y, verticesK.get(0).z);
//
//            if (rotation >= 360.0) {
//                rotation = 0.0f;
//            }
//            camera.setPosition(verticesK.get((int)rotation).x,verticesK.get((int)rotation).y, verticesK.get((int)rotation).z);
//        }
//        if (window.isKeyPressed(GLFW_KEY_D)) {
//            objects.get(0).translateObject(move, 0.0f, 0.0f);
//            Vector3f posObj = objects.get(0).model.transformPosition(new Vector3f());
//            float posX = camera.getPosition().x;
//            float posY = camera.getPosition().y;
//            float posZ = camera.getPosition().z;
//
//            ArrayList<Vector3f> verticesK = new ArrayList<>(List.of());
//
//            for(float i = 0;i<360;i+=1) {
//                float x = (float) (posObj.x + 2f * Math.sin(Math.toRadians(i)));
//                float z = (float) (posObj.z + 2f * Math.cos(Math.toRadians(i)));
//                float y =(float) posObj.y+0.3f;
//                verticesK.add(new Vector3f(x, y, z));
//            }
//            camera.setPosition(verticesK.get(0).x, verticesK.get(0).y, verticesK.get(0).z);
//
//            if (rotation >= 360.0) {
//                rotation = 0.0f;
//            }
//            camera.setPosition(verticesK.get((int)rotation).x,verticesK.get((int)rotation).y, verticesK.get((int)rotation).z);
//        }

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
                    objects.get(1).translateObject(0f, 0f, -0.007f);
                    carPos++;
                }

                if (900 <= carPos && carPos < 990) {
                    List<Float> temp = objects.get(1).getCenterPoint();
                    objects.get(1).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                    objects.get(1).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
                    objects.get(1).translateObject(temp.get(0), temp.get(1), temp.get(2));
                }

                if (900 <= carPos && carPos < 1250) {
                    objects.get(1).translateObject(0.007f, 0f, 0f);
                    carPos++;
                }

                if (1250 <= carPos && carPos < 1340) {
                    List<Float> temp = objects.get(1).getCenterPoint();
                    objects.get(1).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                    objects.get(1).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
                    objects.get(1).translateObject(temp.get(0), temp.get(1), temp.get(2));
                }

                if (1250 <= carPos && carPos < 2330) {
                    objects.get(1).translateObject(0f, 0f, 0.007f);
                    carPos++;
                }

                if (2330 <= carPos && carPos < 2420) {
                    List<Float> temp = objects.get(1).getCenterPoint();
                    objects.get(1).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                    objects.get(1).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
                    objects.get(1).translateObject(temp.get(0), temp.get(1), temp.get(2));
                }

                if (2330 <= carPos && carPos < 2680) {
                    objects.get(1).translateObject(-0.007f, 0f, 0f);
                    carPos++;
                }

                if (2680 <= carPos && carPos < 2770) {
                    List<Float> temp = objects.get(1).getCenterPoint();
                    objects.get(1).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                    objects.get(1).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
                    objects.get(1).translateObject(temp.get(0), temp.get(1), temp.get(2));
                }

                if (2680 <= carPos && carPos < 2860) {
                    objects.get(1).translateObject(0f, 0f, -0.007f);
                    carPos++;
                }

                if (carPos == 2860) {
                    carPos = 0;
                }
            }

            if (modeToggle > 0) {
                if (modeToggle == 1) {
                    List<Float> temp = objects.get(0).getCenterPoint();
                    camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
                    camera.moveBackwards(distance);
                }

                if (carPos2 < 660) {
                    objects.get(0).translateObject(0f, 0f, -0.01f);
                    carPos2++;
                }

                if (660 <= carPos2 && carPos2 < 750) {
                    List<Float> temp = objects.get(0).getCenterPoint();
                    objects.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                    objects.get(0).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
                    objects.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                    angle = angle - (float) Math.toRadians(1f);
                }

                if (660 <= carPos2 && carPos2 < 1000) {
                    objects.get(0).translateObject(0.01f, 0f, 0f);
                    carPos2++;
                }

                if (1000 <= carPos2 && carPos2 < 1090) {
                    List<Float> temp = objects.get(0).getCenterPoint();
                    objects.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                    objects.get(0).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
                    objects.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                    angle = angle - (float) Math.toRadians(1f);
                }

                if (1000 <= carPos2 && carPos2 < 1820) {
                    objects.get(0).translateObject(0f, 0f, 0.01f);
                    carPos2++;
                }

                if (1820 <= carPos2 && carPos2 < 1910) {
                    List<Float> temp = objects.get(0).getCenterPoint();
                    objects.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                    objects.get(0).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
                    objects.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                    angle = angle - (float) Math.toRadians(1f);
                }

                if (1820 <= carPos2 && carPos2 < 2160) {
                    objects.get(0).translateObject(-0.01f, 0f, 0f);
                    carPos2++;
                }

                if (2160 <= carPos2 && carPos2 < 2250) {
                    List<Float> temp = objects.get(0).getCenterPoint();
                    objects.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
                    objects.get(0).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
                    objects.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
                    angle = angle - (float) Math.toRadians(1f);
                }

                if (2160 <= carPos2 && carPos2 < 2320) {
                    objects.get(0).translateObject(0f, 0f, -0.01f);
                    carPos2++;
                }

                if (carPos2 == 2320) {
                    carPos2 = 0;
                }
            }

            // code here
            for (Object object: objects) {
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
