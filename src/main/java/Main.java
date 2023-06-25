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
    float rotation1 = (float) Math.toRadians(1f);
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

        //bola baseball
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

//        this.sk = new Skybox(
//                Arrays.asList(
//                        new ShaderProgram.ShaderModuleData(
//                                "resources/shaders/skybox.vert"
//                                , GL_VERTEX_SHADER),
//                        new ShaderProgram.ShaderModuleData(
//                                "resources/shaders/skybox.frag"
//                                , GL_FRAGMENT_SHADER)
//                ),
//                new ArrayList<>(),
//                new Vector4f(0.0f,1.0f,0.0f,1.0f)
//        );




//        //mario duduk
//        objects.add(new Model(
//                Arrays.asList(
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
//                ),
//                new ArrayList<>(),
//                new Vector4f(0.0f, 1.0f, 0.0f, 1.0f),
//                "resources/model/files/mario111.obj"
//        ));
//        objects.get(2).scaleObject(0.8f,0.8f,0.8f);
//        objects.get(2).translateObject(15f,3f,0f);
//        //trophy
//        objects.add(new Model(
//                Arrays.asList(
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
//                ),
//                new ArrayList<>(),
//                new Vector4f(1.0f, 1.0f, 0.0f, 1.0f),
//                "resources/model/files/mariow.obj"
//        ));
//
//        //mario bunshin
//        objects.add(new Model(
//                Arrays.asList(
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
//                ),
//                new ArrayList<>(),
//                new Vector4f(0.0f, 0.0f, 1.0f, 1.0f),
//                "resources/model/files/mario-sculpture.obj"
//        ));
//        objects.get(4).scaleObject(0.1f,0.1f,0.1f);
//        objects.get(4).translateObject(5f,8f, 0f);
////        objects.get(4).rotateObject((float) Math.toRadians(1f), 1f, 1f, 1f);
//
//        //bola baseball
//        objects.add(new Model(
//                Arrays.asList(
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
//                ),
//                new ArrayList<>(),
//                new Vector4f(255/255f, 255/255f, 153/255f, 1.0f),
//                "resources/model/Ball/ball.obj"
//        ));
//        objects.get(5).scaleObject(0.6f,0.6f,0.6f);
//        objects.get(5).translateObject(25f,5f,0f);
//
//    }
    }

    public void setPos(){
        Vector3f pos = objects.get(7).model.transformPosition(new Vector3f());

        ArrayList<Vector3f> vertices = new ArrayList<>(List.of());

        for(double i=0;i<360;i+=360/360){
            float x = (float)(pos.x + 110f*Math.sin(Math.toRadians(i)));
            float z = (float)(pos.z + 110f*Math.cos(Math.toRadians(i)));
            vertices.add(new Vector3f(x, pos.y+13f, z));
        }

        camera.setPosition(vertices.get((int)rotation1).x,vertices.get((int)rotation1).y, vertices.get((int)rotation1).z);
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



        if (window.isKeyPressed(GLFW_KEY_LEFT)){
            objects.get(7).translateObject(-1f, 0.0f, 0.0f);
            setPos();
        }

        if (window.isKeyPressed(GLFW_KEY_RIGHT)){
            objects.get(7).translateObject(1f, 0.0f, 0.0f);
            setPos();
        }

        if (window.isKeyPressed(GLFW_KEY_UP)){
            objects.get(7).translateObject(0.0f, 0.0f, -1f);
            setPos();
        }

        if (window.isKeyPressed(GLFW_KEY_DOWN)){
            objects.get(7).translateObject(0.0f, 0.0f, 1f);
            setPos();
        }

//        if (window.isKeyPressed(GLFW_KEY_P)){
//            objects.get(7).translateObject(0.0f, 1f, 0.0f);
//            setPos();
//        }
//
//        if (window.isKeyPressed(GLFW_KEY_O)){
//            objects.get(7).translateObject(0.0f, -1f, 0.0f);
//            setPos();
//        }


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

            rotation += move;

            if (rotation > 359.0f){
                rotation = 0.0f;
            }

            camera.setPosition(vertices.get((int)rotation).x,vertices.get((int)rotation).y, vertices.get((int)rotation).z );
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
//        if (window.isKeyPressed(GLFW_KEY_F1) && !FPS){
//
//            Vector3f pos = camera.getPosition();
//
//            if (cinematic) {
//                for (int i=0;i<camRotation; i+=5){
//                    camera.setPosition(-pos.x, -pos.y, -pos.z);
//                    camera.addRotation(0f, (float) Math.toRadians(5));
//                    camera.setPosition(pos.x, pos.y, pos.z);
//                }
//
//                camera.setPosition(-pos.x, -pos.y, -pos.z);
//                camera.addRotation(0f, (float) Math.toRadians(180));
//                camera.setPosition(pos.x, pos.y, pos.z);
//
//                for (int i=0;i<rotation; i+=5){
//                    camera.setPosition(-pos.x, -pos.y, -pos.z);
//                    camera.addRotation(0f, (float) Math.toRadians(-5));
//                    camera.setPosition(pos.x, pos.y, pos.z);
//                }
//            }
//            else if (!FPS && !TPS){
//                camera.setPosition(-pos.x, -pos.y, -pos.z);
//                camera.setRotation(0f, (float) Math.toRadians(90));
//                camera.setPosition(pos.x, pos.y, pos.z);
//            }
//
//            FPS = true;
//            TPS = false;
//            cinematic = false;
//
//            updateFPS();
//        }
//
//        // FPS CAMERA
//        if (window.isKeyPressed(GLFW_KEY_LEFT) && FPS) {
//            if (rotation <= 90 || rotation > 270) {
//                Vector3f poss = objects.get(7).model.transformPosition(new Vector3f());
//                for (Object object : objectAstronaut) {
//                    Vector3f pos = object.model.transformPosition(new Vector3f());
//                    object.translateObject(-pos.x, -pos.y, -pos.z);
//                    object.rotateObject((float) Math.toRadians(5), 0f, -1f, 0f);
//                    object.translateObject(pos.x, pos.y, pos.z);
//                }
//                rotation-=5;
//                if (rotation > 355) {
//                    rotation = 0;
//                }
//                if (rotation < 0) {
//                    rotation = 355;
//                }
//                Vector3f pos = camera.getPosition();
//                camera.setPosition(-pos.x, -pos.y, -pos.z);
//                camera.addRotation(0f, (float) Math.toRadians(5));
//                camera.setPosition(pos.x, pos.y, pos.z);
//
//            }
//            else if (rotation < 270){
//                for (Object object : objectAstronaut) {
//                    Vector3f pos = object.model.transformPosition(new Vector3f());
//                    object.translateObject(-pos.x, -pos.y, -pos.z);
//                    object.rotateObject((float) Math.toRadians(5), 0f, 1f, 0f);
//                    object.translateObject(pos.x, pos.y, pos.z);
//                }
//                rotation+=5;
//                if (rotation > 355) {
//                    rotation = 0;
//                }
//                if (rotation < 0) {
//                    rotation = 355;
//                }
//                Vector3f pos = camera.getPosition();
//                camera.setPosition(-pos.x, -pos.y, -pos.z);
//                camera.addRotation(0f, (float) Math.toRadians(-5));
//                camera.setPosition(pos.x, pos.y, pos.z);
//            }
////            if (!collision.get(2)) {
////                hitboxPerson.get(0).translateObject(-0.005f, 0f, 0f);
////                updateFPS();
////                for (Object object: objectAstronaut){
////                    object.translateObject(-0.005f, 0f, 0f);
////                }
////            }
//
//        }
//
//        if (window.isKeyPressed(GLFW_KEY_UP) && FPS) {
//            if (rotation > 180) {
//                for (Object object : objectAstronaut) {
//                    Vector3f pos = object.model.transformPosition(new Vector3f());
//                    object.translateObject(-pos.x, -pos.y, -pos.z);
//                    object.rotateObject((float) Math.toRadians(5), 0f, -1f, 0f);
//                    object.translateObject(pos.x, pos.y, pos.z);
//                }
//                rotation-=5;
//                if (rotation > 355) {
//                    rotation = 0;
//                }
//                if (rotation < 0) {
//                    rotation = 355;
//                }
//                Vector3f pos = camera.getPosition();
//                camera.setPosition(-pos.x, -pos.y, -pos.z);
//                camera.addRotation(0f, (float) Math.toRadians(5));
//                camera.setPosition(pos.x, pos.y, pos.z);
//
//            } else if (rotation < 180){
//                for (Object object : objectAstronaut) {
//                    Vector3f pos = object.model.transformPosition(new Vector3f());
//                    object.translateObject(-pos.x, -pos.y, -pos.z);
//                    object.rotateObject((float) Math.toRadians(5), 0f, 1f, 0f);
//                    object.translateObject(pos.x, pos.y, pos.z);
//                }
//                rotation+=5;
//                if (rotation > 355) {
//                    rotation = 0;
//                }
//                if (rotation < 0) {
//                    rotation = 355;
//                }
//                Vector3f pos = camera.getPosition();
//                camera.setPosition(-pos.x, -pos.y, -pos.z);
//                camera.addRotation(0f, (float) Math.toRadians(-5));
//                camera.setPosition(pos.x, pos.y, pos.z);
//            }
//
////            if (!collision.get(6)) {
////                hitboxPerson.get(0).translateObject(0f, 0f, -0.005f);
////                updateFPS();
////                for (Object object: objectAstronaut){
////                    object.translateObject(0f, 0f, -0.005f);
////                }
////            }
//        }
//
//        if (window.isKeyPressed(GLFW_KEY_RIGHT) && FPS) {
//            if (rotation >= 270 || rotation < 90) {
//                for (Object object : objectAstronaut) {
//                    Vector3f pos = object.model.transformPosition(new Vector3f());
//                    object.translateObject(-pos.x, -pos.y, -pos.z);
//                    object.rotateObject((float) Math.toRadians(5), 0f, 1f, 0f);
//                    object.translateObject(pos.x, pos.y, pos.z);
//                }
//                rotation+=5;
//                if (rotation > 355) {
//                    rotation = 0;
//                }
//                if (rotation < 0) {
//                    rotation = 355;
//                }
//                Vector3f pos = camera.getPosition();
//                camera.setPosition(-pos.x, -pos.y, -pos.z);
//                camera.addRotation(0f, (float) Math.toRadians(-5));
//                camera.setPosition(pos.x, pos.y, pos.z);
//            } else if (rotation > 90){
//                for (Object object : objectAstronaut) {
//                    Vector3f pos = object.model.transformPosition(new Vector3f());
//                    object.translateObject(-pos.x, -pos.y, -pos.z);
//                    object.rotateObject((float) Math.toRadians(5), 0f, -1f, 0f);
//                    object.translateObject(pos.x, pos.y, pos.z);
//                }
//                rotation-=5;
//                if (rotation > 355) {
//                    rotation = 0;
//                }
//                if (rotation < 0) {
//                    rotation = 355;
//                }
//                Vector3f pos = camera.getPosition();
//                camera.setPosition(-pos.x, -pos.y, -pos.z);
//                camera.addRotation(0f, (float) Math.toRadians(5));
//                camera.setPosition(pos.x, pos.y, pos.z);
//            }
//
////            if (!collision.get(1)) {
////                hitboxPerson.get(0).translateObject(0.005f, 0f, 0f);
////                updateFPS();
////                for (Object object: objectAstronaut){
////                    object.translateObject(0.005f, 0f, 0f);
////                }
////            }
//        }
//
//
//        if (window.isKeyPressed(GLFW_KEY_DOWN) && first_p) {
//            if (rotation <= 180 && rotation !=0){
//                for (Object object : objectAstronaut) {
//                    Vector3f pos = object.model.transformPosition(new Vector3f());
//                    object.translateObject(-pos.x, -pos.y, -pos.z);
//                    object.rotateObject((float) Math.toRadians(5), 0f, -1f, 0f);
//                    object.translateObject(pos.x, pos.y, pos.z);
//                }
//                rotation-=5;
//                if (rotation > 355) {
//                    rotation = 0;
//                }
//                if (rotation < 0) {
//                    rotation = 355;
//                }
//                Vector3f pos = camera.getPosition();
//                camera.setPosition(-pos.x, -pos.y, -pos.z);
//                camera.addRotation(0f, (float) Math.toRadians(5));
//                camera.setPosition(pos.x, pos.y, pos.z);
//            }
//            else if (rotation > 180){
//                for (Object object : objects.get(7).getChildObject()) {
//                    Vector3f pos = object.model.transformPosition(new Vector3f());
//                    object.translateObject(-pos.x, -pos.y, -pos.z);
//                    object.rotateObject((float) Math.toRadians(5), 0f, 1f, 0f);
//                    object.translateObject(pos.x, pos.y, pos.z);
//                }
//                rotation+=5;
//                if (rotation > 355) {
//                    rotation = 0;
//                }
//                if (rotation < 0) {
//                    rotation = 355;
//                }
//                Vector3f pos = camera.getPosition();
//                camera.setPosition(-pos.x, -pos.y, -pos.z);
//                camera.addRotation(0f, (float) Math.toRadians(-5));
//                camera.setPosition(pos.x, pos.y, pos.z);
//            }
//
////            if (!collision.get(5)) {
////                hitboxPerson.get(0).translateObject(0f, 0f ,0.005f);
////                updateFPS();
////                for (Object object: objectAstronaut){
////                    object.translateObject(0f, 0f, 0.005f);
////                }
////            }
//        }
        

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
