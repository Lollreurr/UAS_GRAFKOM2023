import Engine.Object;
import Engine.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL20.*;

public class Test {
    private Window window =
            new Window
                    (800,800,"Hello World");
    private ArrayList<Object> objects
            = new ArrayList<>();
    private ArrayList<Object> object
            = new ArrayList<>();

    Camera camera = new Camera();
    Projection projection = new Projection(window.getWidth(), window.getHeight());

    boolean pressed = false;
    boolean pressed1 = false;
    float rotation = 0.0f;

    public void init(){
        window.init();
        GL.createCapabilities();

        camera.setPosition(0.0f, 0.0f, 1.5f);
        camera.setRotation(((float)Math.toRadians(0.0f)), ((float) Math.toRadians(0.0f)));

        //code
        objects.add(new Square(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1f,1f,1f,1.0f), new Vector3f(-0.5f,0,0), 0.25f, 0.25f, 0.25f
        ));

        objects.add(new Square(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1f,1f,1f,1.0f), new Vector3f(0.5f,0,0), 0.25f, 0.25f, 0.25f
        ));

        object.add(new Square(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1f,1f,1f,1.0f), new Vector3f(0f,0,0), 0.15f, 0.15f, 0.15f
        ));


    }

    public ArrayList<Boolean> checkCollision(){
        ArrayList <Boolean> collision = new ArrayList<>();
        for (int i=0; i<7; i++){
            collision.add(false);
        }

        for (Object object: objects) {
            ArrayList <Boolean> col = object.checkCollision(this.object.get(0).getCenterPoint(), this.object.get(0).getSize());
            if (col.get(0)) {
                for (int i = 0; i < col.size(); i++) {
                    if (col.get(i)) {
                        collision.set(i, true);
                    }
                }
            }
        }
        return collision;
    }

    public void input(){

        float move = 0.005f;
        ArrayList<Boolean> collision = checkCollision();

        if (window.isKeyPressed(GLFW_KEY_UP)){
            camera.moveForward(move);
        }
        else if (window.isKeyPressed(GLFW_KEY_DOWN)){
            camera.moveBackwards(move);
        }

        if (window.isKeyPressed(GLFW_KEY_LEFT)){
            camera.moveLeft(move);
        }
        else if (window.isKeyPressed(GLFW_KEY_RIGHT)){
            camera.moveRight(move);
        }

        if (window.getMouseInput().isRightButtonPressed()) {
            Vector2f displVec = window.getMouseInput().getDisplVec();
            camera.addRotation((float) Math.toRadians(displVec.x * 0.1f), (float) Math.toRadians(displVec.y * 0.1f));
        }

        if (window.isKeyPressed(GLFW_KEY_W)){
            if (!collision.get(6)) {
                object.get(0).translateObject(0f, 0f, -0.005f);
            }
        }
        else if (window.isKeyPressed(GLFW_KEY_A)){
            if (!collision.get(2)) {
                object.get(0).translateObject(-0.005f, 0f, 0f);
            }
        }

        if (window.isKeyPressed(GLFW_KEY_S)){
            if (!collision.get(5)) {
                object.get(0).translateObject(0f, 0f ,0.005f);
            }
        }
        else if (window.isKeyPressed(GLFW_KEY_D)){
            if (!collision.get(1)) {
                object.get(0).translateObject(0.005f, 0f, 0f);
            }
        }
    }

    public void loop(){
        while (window.isOpen()) {
            window.update();
            glClearColor(0.0f,
                    0.0f, 0.0f,
                    0.0f);
            GL.createCapabilities();

            input();

            //code

            for(Object object: objects){
                object.draw(camera, projection);
            }

            for(Object object: object){
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
    public void run() {

        init();
        loop();

        // Terminate GLFW and
        // free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    public static void main(String[] args) {
        new Test().run();
    }
}

