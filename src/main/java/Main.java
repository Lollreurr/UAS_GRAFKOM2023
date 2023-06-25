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
    Camera camera = new Camera();
    Projection projection = new Projection(window.getWidth(), window.getHeight());
    float distance = 1f;
    float angle = 0f;
    float rotation = (float)Math.toRadians(1f);
    float rotCamera = (float) Math.toRadians(1f);
    float move = 0.01f;
    List<Float> temp;
    boolean delay = false;
    int delayCounter = 0;
    boolean malam = true;
    boolean delay2 = false;
    int delayCounter2 = 0;
    boolean delay3 = false;
    int delayCounter3 = 0;

    boolean pressed = false;
    Skybox sk;
    int[] mode_light;

    boolean press_homerun = false;
    boolean press_mario_run = false;
    float move_mario_run = 0f;
    float move_homerun = 0;

    boolean third_p = false, first_p = false, cinema_t = false;

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
        camera.setPosition(0.7f, 7f, 50f + distance);

        mode_light = new int[]{1,1,1};

        //Lamp
        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1f, 1f, 1f, 1.0f),
                "resources/model/Ball/ball.obj"
        ));
        objects.get(0).scaleObject(1f,1f,1f);
        objects.get(0).translateObject(190f, 70f, -20f);
        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1f, 1f, 1f, 1.0f),
                "resources/model/Ball/ball.obj"
        ));
        objects.get(1).scaleObject(1f,1f,1f);
        objects.get(1).translateObject(-190f, 70f, -13f);
        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1f, 1f, 1f, 1.0f),
                "resources/model/Ball/ball.obj"
        ));
        objects.get(2).scaleObject(1f,1f,1f);
        objects.get(2).translateObject(0f, 65f, 225f);
        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1f, 1f, 1f, 1.0f),
                "resources/model/Ball/ball.obj"
        ));
        objects.get(3).scaleObject(1f,1f,1f);
        objects.get(3).translateObject(-23f, 50f, -180f);
        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1f, 1f, 1f, 1.0f),
                "resources/model/Ball/ball.obj"
        ));
        objects.get(4).scaleObject(1f,1f,1f);
        objects.get(4).translateObject(150f, 70f, -170f);
        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1f, 1f, 1f, 1.0f),
                "resources/model/Ball/ball.obj"
        ));
        objects.get(5).scaleObject(1f,1f,1f);
        objects.get(5).translateObject(-190f, 70f, -150f);


        //Bowser - obj 6
        //Bowser
        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(29/255f, 175/255f, 34/255f, 1.0f),
                "resources/model/bowserOBJ/bowser1.obj"
        ));
        objects.get(6).scaleObject(0.5f,0.5f,0.5f);

        objects.get(6).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0f, 0f, 0f, 1.0f),
                "resources/model/bowserOBJ/bowser2.obj"
        ));
        objects.get(6).getChildObject().get(0).scaleObject(0.5f,0.5f,0.5f);
        objects.get(6).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(247/255f, 225/255f, 19/255f, 1.0f),
                "resources/model/bowserOBJ/bowser3.obj"
        ));
        objects.get(6).getChildObject().get(1).scaleObject(0.5f,0.5f,0.5f);
        objects.get(6).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(247/255f, 153/255f, 11/255f, 1.0f),
                "resources/model/bowserOBJ/bowser4.obj"
        ));
        objects.get(6).getChildObject().get(2).scaleObject(0.5f,0.5f,0.5f);
        objects.get(6).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(255/255f, 255/255f, 153/255f, 1.0f),
                "resources/model/bowserOBJ/bowser5.obj"
        ));
        objects.get(6).getChildObject().get(3).scaleObject(0.5f,0.5f,0.5f);
        objects.get(6).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(247/255f, 19/255f, 19/255f, 1.0f),
                "resources/model/bowserOBJ/bowser6.obj"
        ));
        objects.get(6).getChildObject().get(4).scaleObject(0.5f,0.5f,0.5f);

        objects.get(6).translateObject(0f,1.2f,25f);



        //mario - obj 7
        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(255/255f, 204/255f, 153/255f, 1.0f),
                "resources/model/marioOBJ/mario1.obj"
        ));
        objects.get(7).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(255/255f, 0f,0f, 1.0f),
                "resources/model/marioOBJ/mario2.obj"
        ));
        objects.get(7).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(192/255f, 192/255f, 192/255f, 1.0f),
                "resources/model/marioOBJ/mario3.obj"
        ));


        //princess peach
        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(255/255f, 255/255f, 102/255f, 1.0f),
                "resources/model/peachOBJ/peach1.obj"
        ));
        objects.get(8).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(255/255f, 153/255f, 204/255f, 1.0f),
                "resources/model/peachOBJ/peach2.obj"
        ));


        //luigi
        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0f, 1f, 0f, 1.0f),
                "resources/model/luigiOBJ/luigi1.obj"
        ));




        //Stadium - obj 10
        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(128/255f, 128/255f, 128/255f, 1.0f),
                "resources/model/Stadium/basee.obj"
        ));
        objects.get(10).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(61/255f, 113/255f, 181/255f, 1.0f),
                "resources/model/Stadium/fence.obj"
        ));
        objects.get(10).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
                "resources/model/Stadium/landingPlace.obj"
        ));
        objects.get(10).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
                "resources/model/Stadium/detail.obj"
        ));
        objects.get(10).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(112/255f, 242/255f, 252/255f, 1.0f),
                "resources/model/Stadium/chair.obj"
        ));
        objects.get(10).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(70/255f, 232/255f, 86/255f, 1.0f),
                "resources/model/Stadium/dirt.obj"
        ));
        objects.get(10).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(249/255f, 216/255f, 166/255f, 1.0f),
                "resources/model/Stadium/pitch.obj"
        ));

        //bola baseball - obj 11
        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1f, 153/255f, 51/255f, 1.0f),
                "resources/model/Ball/ball.obj"
        ));
        objects.get(11).scaleObject(0.5f,0.5f,0.5f);
        objects.get(11).translateObject(0f,6f,35f);

        //lamp back kuning - kanan
        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1f, 1f, 102/255f, 1.0f),
                "resources/model/Ball/ball.obj"
        ));
        objects.get(12).translateObject(140f,70f,140f);
        //lamp back kuning kiri
        objects.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1f, 1f, 102/255f, 1.0f),
                "resources/model/Ball/ball.obj"
        ));
        objects.get(13).translateObject(-140f,70f,140f);

    }

    public void setHomerun(){
        Vector3f pos = objects.get(7).model.transformPosition(new Vector3f());

        ArrayList<Vector3f> vertices = new ArrayList<>(List.of());

        for(double i=0;i<360;i+=360/360){
            float x = (float)(pos.x + 130f*Math.sin(Math.toRadians(i)));
            float z = (float)(pos.z + 130f*Math.cos(Math.toRadians(i)));
            vertices.add(new Vector3f(x, pos.y+15f, z));
        }
        camera.setPosition(vertices.get((int)rotation).x,vertices.get((int)rotation).y, vertices.get((int)rotation).z);
    }
    public void setTPS(){
        Vector3f pos = objects.get(7).model.transformPosition(new Vector3f());

        ArrayList<Vector3f> vertices = new ArrayList<>(List.of());

        for(double i=0;i<360;i+=360/360){
            float x = (float)(pos.x + 110f*Math.sin(Math.toRadians(i)));
            float z = (float)(pos.z + 110f*Math.cos(Math.toRadians(i)));
            vertices.add(new Vector3f(x, pos.y+13f, z));
        }
        camera.setPosition(vertices.get((int)rotation).x,vertices.get((int)rotation).y, vertices.get((int)rotation).z);
    }

    public void setFPS(){
        Vector3f pos = objects.get(7).model.transformPosition(new Vector3f());

        ArrayList<Vector3f> track = new ArrayList<>(List.of());

        for(double i=0;i<360;i+=360/360){
            float x = (float)(pos.x + 75f*Math.sin(Math.toRadians(i)));
            float z = (float)(pos.z + 75f*Math.cos(Math.toRadians(i)));
            track.add(new Vector3f(x, pos.y + 9f, z));
        }

        camera.setPosition(track.get((int)rotation).x, track.get((int)rotation).y, track.get((int)rotation).z);
    }

//    public void updateTPS(){
//        Vector3f pos = objects.get(7).model.transformPosition(new Vector3f());
//
//        ArrayList<Vector3f> track = new ArrayList<>(List.of());
//
//        for(double i=0;i<360;i+=360/360){
//            float x = (float)(pos.x + 0.5f*Math.sin(Math.toRadians(i)));
//            float z = (float)(pos.z + 0.5f*Math.cos(Math.toRadians(i)));
//            track.add(new Vector3f(x, pos.y + 0.55f, z));
//        }
//
//        System.out.println(((rotation + 180)%360));
//        camera.setPosition(track.get((int)((rotation + 180)%360)).x, track.get((int)((rotation + 180)%360)).y,
//                track.get((int)((rotation + 180)%360)).z);
//    }

    public void input() {

        if (window.getMouseInput().isLeftButtonPressed()){
            Vector2f displVec = window.getMouseInput().getDisplVec();
            camera.addRotation((float) Math.toRadians(displVec.x * 0.1f), (float) Math.toRadians(displVec.y * 0.1f));
        }

        if (window.getMouseInput().getScroll().y != 0){
            projection.setFOV(projection.getFOV() - (window.getMouseInput().getScroll().y * 0.1f));
            window.getMouseInput().setScroll(new Vector2f());
        }

//        temp = objects.get(0).getCenterPoint();
        angle = angle % (float) Math.toRadians(360);
        float move = 1f;
        Vector3f temp = objects.get(0).getCenterPoint();
        angle = angle % (float) Math.toRadians(360);

        if (window.isKeyPressed(GLFW_KEY_L) && !delay2){
            malam = !malam;
            for (Object object: objects){
                object.setScene(malam);
                for(Object objectChild: object.getChildObject()){
                    objectChild.setScene(malam);
                }
            }

            for (Object object: objectGround){
                object.setScene(malam);
                for(Object objectChild: object.getChildObject()){
                    objectChild.setScene(malam);
                }
            }

            delay2 = true;
        }

        //camera movement
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

        //camera muter sendiri
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

        //camera muterin object
        if (window.isKeyPressed(GLFW_KEY_B)){
            move = 1f;
            Vector3f pos = objects.get(10).model.transformPosition(new Vector3f());
            Vector3f posCam = camera.getPosition();

            ArrayList<Vector3f> vertices = new ArrayList<>(List.of());

            for(double i=0;i<360;i+=360/360){
                float x = (float)(pos.x + 160f*Math.sin(Math.toRadians(i)));
                float z = (float)(pos.z + 160f*Math.cos(Math.toRadians(i)));
                vertices.add(new Vector3f(x, pos.y+70f, z));
            }

            camera.setPosition(vertices.get(0).x, vertices.get(0).y, vertices.get(0).z);

            camera.setPosition(-posCam.x, 0, -posCam.z);
            camera.addRotation(0.0f, (float) Math.toRadians(-move));
            camera.setPosition(posCam.x, 0, posCam.z);

            rotCamera += move;

            if (rotCamera > 359.0f){
                rotCamera = 0.0f;
            }

            camera.setPosition(vertices.get((int)rotCamera).x,vertices.get((int)rotCamera).y, vertices.get((int)rotCamera).z );
        }

        //light mode
        if (window.isKeyPressed(GLFW_KEY_1) && !delay){
            if(mode_light[0] == 0){
                mode_light[0] = 1;
            } else{
                mode_light[0] = 0;
            }
            delay = true;
        }
        if (window.isKeyPressed(GLFW_KEY_2) && !delay){
            if(mode_light[1] == 0){
                mode_light[1] = 1;
            } else{
                mode_light[1] = 0;
            }
            delay = true;
        }
        if (window.isKeyPressed(GLFW_KEY_3) && !delay){
            if(mode_light[2] == 0){
                mode_light[2] = 1;
            } else{
                mode_light[2] = 0;
            }
            delay = true;
        }


        // FPS trigger
        if (window.isKeyPressed(GLFW_KEY_F1) && !first_p){
            Vector3f pos = camera.getPosition();
            if (!first_p) {
                camera.setPosition(-pos.x, -pos.y, -pos.z);
                camera.setRotation(0f, (float) Math.toRadians(0));
                camera.setPosition(pos.x, pos.y, pos.z);
            }
            first_p = true;
            third_p = false;
            setFPS();
        }

        // FPS Camera
        if (window.isKeyPressed(GLFW_KEY_LEFT) && first_p){
            objects.get(7).translateObject(-1f, 0.0f, 0.0f);
            setFPS();
        }
        if (window.isKeyPressed(GLFW_KEY_RIGHT) && first_p){
            objects.get(7).translateObject(1f, 0.0f, 0.0f);
            setFPS();
        }
        if (window.isKeyPressed(GLFW_KEY_UP) && first_p){
            objects.get(7).translateObject(0.0f, 0.0f, -1f);
            setFPS();
        }
        if (window.isKeyPressed(GLFW_KEY_DOWN) && first_p){
            objects.get(7).translateObject(0.0f, 0.0f, 1f);
            setFPS();
        }


        // TPS trigger
        if (window.isKeyPressed(GLFW_KEY_F2) && !third_p){
            Vector3f pos = camera.getPosition();
            if (!third_p) {
                camera.setPosition(-pos.x, -pos.y, -pos.z);
                camera.setRotation(0f, (float) Math.toRadians(0));
                camera.setPosition(pos.x, pos.y, pos.z);
            }
            first_p = false;
            third_p = true;
            setTPS();
        }

        // TPS Camera
        if (window.isKeyPressed(GLFW_KEY_LEFT) && third_p){
            objects.get(7).translateObject(-1f, 0.0f, 0.0f);
            setTPS();
        }
        if (window.isKeyPressed(GLFW_KEY_RIGHT) && third_p){
            objects.get(7).translateObject(1f, 0.0f, 0.0f);
            setTPS();
        }
        if (window.isKeyPressed(GLFW_KEY_UP) && third_p){
            objects.get(7).translateObject(0.0f, 0.0f, -1f);
            setTPS();
        }
        if (window.isKeyPressed(GLFW_KEY_DOWN) && third_p){
            objects.get(7).translateObject(0.0f, 0.0f, 1f);
            setTPS();
        }

        //homerun
        if (window.isKeyPressed(GLFW_KEY_H)){
            press_homerun = true;
            setHomerun();
            Vector3f posObj = objects.get(7).model.transformPosition(new Vector3f());
            objects.get(7).translateObject(-posObj.x, -posObj.y, -posObj.z);
            objects.get(7).rotateObject((float) Math.toRadians(1), 0f, 1f, 0f);
            objects.get(7).translateObject(posObj.x, posObj.y, posObj.z);
            setHomerun();
        }
        if (press_homerun){
            setHomerun();
//            Vector3f posObj = objects.get(11).model.transformPosition(new Vector3f());
//            objects.get(11).translateObject(-posObj.x, -posObj.y, -posObj.z);
//            objects.get(11).rotateObject((float) Math.toRadians(1), 0f, 1f, 0f);
//            objects.get(11).translateObject(posObj.x, posObj.y, posObj.z);
//            setHomerun();
            objects.get(11).translateObject(0f,0f,1f);
            move_homerun += 1f;
            if (move_homerun >=45){
                move_homerun = 0.0f;
                press_mario_run = true;
            }
        }
        if (press_mario_run){
            setHomerun();
            Vector3f posObj = objects.get(7).model.transformPosition(new Vector3f());
            objects.get(7).translateObject(-posObj.x, -posObj.y, -posObj.z);
            objects.get(7).rotateObject((float) Math.toRadians(1), 0f, 1f, 0f);
            objects.get(7).translateObject(posObj.x, posObj.y, posObj.z);
            setHomerun();
            move_mario_run += movee;

            if (move_mario_run >= 360.0f){
                move_mario_run = 0.0f;
                press_mario_run = false;
            }
            press_homerun = false;
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

            //temp
//            if (start){
//                if (carPos < 900) {
//                    objects.get(1).translateObject(0f, 0f, -0.007f);
//                    carPos++;
//                }
//
//                if (900 <= carPos && carPos < 990) {
//                    List<Float> temp = objects.get(1).getCenterPoint();
//                    objects.get(1).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
//                    objects.get(1).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
//                    objects.get(1).translateObject(temp.get(0), temp.get(1), temp.get(2));
//                }
//
//                if (900 <= carPos && carPos < 1250) {
//                    objects.get(1).translateObject(0.007f, 0f, 0f);
//                    carPos++;
//                }
//
//                if (1250 <= carPos && carPos < 1340) {
//                    List<Float> temp = objects.get(1).getCenterPoint();
//                    objects.get(1).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
//                    objects.get(1).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
//                    objects.get(1).translateObject(temp.get(0), temp.get(1), temp.get(2));
//                }
//
//                if (1250 <= carPos && carPos < 2330) {
//                    objects.get(1).translateObject(0f, 0f, 0.007f);
//                    carPos++;
//                }
//
//                if (2330 <= carPos && carPos < 2420) {
//                    List<Float> temp = objects.get(1).getCenterPoint();
//                    objects.get(1).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
//                    objects.get(1).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
//                    objects.get(1).translateObject(temp.get(0), temp.get(1), temp.get(2));
//                }
//
//                if (2330 <= carPos && carPos < 2680) {
//                    objects.get(1).translateObject(-0.007f, 0f, 0f);
//                    carPos++;
//                }
//
//                if (2680 <= carPos && carPos < 2770) {
//                    List<Float> temp = objects.get(1).getCenterPoint();
//                    objects.get(1).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
//                    objects.get(1).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
//                    objects.get(1).translateObject(temp.get(0), temp.get(1), temp.get(2));
//                }
//
//                if (2680 <= carPos && carPos < 2860) {
//                    objects.get(1).translateObject(0f, 0f, -0.007f);
//                    carPos++;
//                }
//
//                if (carPos == 2860) {
//                    carPos = 0;
//                }
//            }
//
//            if (modeToggle > 0) {
//                if (modeToggle == 1) {
//                    List<Float> temp = objects.get(0).getCenterPoint();
//                    camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
//                    camera.moveBackwards(distance);
//                }
//
//                if (carPos2 < 660) {
//                    objects.get(0).translateObject(0f, 0f, -0.01f);
//                    carPos2++;
//                }
//
//                if (660 <= carPos2 && carPos2 < 750) {
//                    List<Float> temp = objects.get(0).getCenterPoint();
//                    objects.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
//                    objects.get(0).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
//                    objects.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
//                    angle = angle - (float) Math.toRadians(1f);
//                }
//
//                if (660 <= carPos2 && carPos2 < 1000) {
//                    objects.get(0).translateObject(0.01f, 0f, 0f);
//                    carPos2++;
//                }
//
//                if (1000 <= carPos2 && carPos2 < 1090) {
//                    List<Float> temp = objects.get(0).getCenterPoint();
//                    objects.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
//                    objects.get(0).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
//                    objects.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
//                    angle = angle - (float) Math.toRadians(1f);
//                }
//
//                if (1000 <= carPos2 && carPos2 < 1820) {
//                    objects.get(0).translateObject(0f, 0f, 0.01f);
//                    carPos2++;
//                }
//
//                if (1820 <= carPos2 && carPos2 < 1910) {
//                    List<Float> temp = objects.get(0).getCenterPoint();
//                    objects.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
//                    objects.get(0).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
//                    objects.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
//                    angle = angle - (float) Math.toRadians(1f);
//                }
//
//                if (1820 <= carPos2 && carPos2 < 2160) {
//                    objects.get(0).translateObject(-0.01f, 0f, 0f);
//                    carPos2++;
//                }
//
//                if (2160 <= carPos2 && carPos2 < 2250) {
//                    List<Float> temp = objects.get(0).getCenterPoint();
//                    objects.get(0).translateObject(-temp.get(0), -temp.get(1), -temp.get(2));
//                    objects.get(0).rotateObject(-(float) Math.toRadians(1f), 0f, 1f, 0f);
//                    objects.get(0).translateObject(temp.get(0), temp.get(1), temp.get(2));
//                    angle = angle - (float) Math.toRadians(1f);
//                }
//
//                if (2160 <= carPos2 && carPos2 < 2320) {
//                    objects.get(0).translateObject(0f, 0f, -0.01f);
//                    carPos2++;
//                }
//
//                if (carPos2 == 2320) {
//                    carPos2 = 0;
//                }
//            }

            // code here
            for (Object object: objects) {
                object.draw(camera, projection, mode_light);
            }

            for (Object object: objectGround) {
                object.draw(camera, projection, mode_light);
            }

//            sk.draw(camera, projection);

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
