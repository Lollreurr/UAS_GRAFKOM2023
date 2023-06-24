package Engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class SkyBoxCube extends ShaderProgram
{
    private float size = 500f;
    private float [] vertices =
            {
                    -size,  size, -size,
                    -size, -size, -size,

                    size, -size, -size,
                    size, -size, -size,
                    size,  size, -size,
                    -size,  size, -size,

                    -size, -size,  size,
                    -size, -size, -size,
                    -size,  size, -size,
                    -size,  size, -size,
                    -size,  size,  size,
                    -size, -size,  size,

                    size, -size, -size,
                    size, -size,  size,
                    size,  size,  size,
                    size,  size,  size,
                    size,  size, -size,
                    size, -size, -size,

                    -size, -size,  size,
                    -size,  size,  size,
                    size,  size,  size,
                    size,  size,  size,
                    size, -size,  size,
                    -size, -size,  size,

                    -size,  size, -size,
                    size,  size, -size,
                    size,  size,  size,
                    size,  size,  size,
                    -size,  size,  size,
                    -size,  size, -size,

                    -size, -size, -size,
                    -size, -size,  size,
                    size, -size, -size,
                    size, -size, -size,
                    -size, -size,  size,
                    size, -size,  size
            };

    private final static String[] TEXTURE_FILE_NAMES = {"resources/skybox/right.png", "resources/skybox/left.png", "resources/skybox/top.png",
            "resources/skybox/bottom.png", "resources/skybox/back.png", "resources/skybox/front.png"};

    int vao, vbo, texture;
    UniformsMap uniformsMap;

    //constructor 1 warna
    public SkyBoxCube()
    {
        super(Arrays.asList(new ShaderModuleData("resources/shaders/skybox.vert",
                GL_VERTEX_SHADER), new ShaderModuleData("resources/shaders/skybox.frag", GL_FRAGMENT_SHADER)));
        setupVAOVBO();
        texture = Utils.loadCubeMap(TEXTURE_FILE_NAMES);
        uniformsMap = new UniformsMap(getProgramId());
    }

    public void setupVAOVBO()
    {
        //setup vao
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        //setup vbo
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
    }

    //draw setup pake kamera
    public void drawSetup(Camera camera, Projection projection)
    {
        bind();
        //isi uniform dengan variabel dari objek
        uniformsMap.setUniform("projectionMatrix",projection.getProjMatrix());
        uniformsMap.setUniform("viewMatrix",camera.getViewMatrix());


        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

//        glBindVertexArray(vao);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_CUBE_MAP, texture);
    }

    //draw pake kamera
    public void draw(Camera camera, Projection projection)
    {
        drawSetup(camera, projection);
//        glLineWidth(1);
//        glPointSize(0);
        glDrawArrays(GL_TRIANGLES, 0, 8);
        glDisableVertexAttribArray(0);
//        glBindVertexArray(0);
    }
}
