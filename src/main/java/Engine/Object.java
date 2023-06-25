package Engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Object extends ShaderProgram{

    List<Vector3f> vertices;
    int vao;
    int vbo;
    UniformsMap uniformsMap;
    Vector4f color;
    public Matrix4f model;
    List<Object> childObject;
    Vector3f centerPoint;
    boolean scene = true;
    Vector3f size;

    public void setScene(boolean scene) {
        this.scene = scene;
    }

    public List<Object> getChildObject() {
        return childObject;
    }

    public Vector3f getCenterPoint() {
        updateCenterPoint();
        return centerPoint;
    }

    public Object(List<ShaderModuleData> shaderModuleDataList
            , List<Vector3f> vertices
            , Vector4f color) {
        super(shaderModuleDataList);
        this.vertices = vertices;
//        this.size = size;
//        setupVAOVBO();
        uniformsMap = new UniformsMap(getProgramId());
        uniformsMap.createUniform(
                "uni_color");
        uniformsMap.createUniform(
                "model");
        uniformsMap.createUniform(
                "projection");
        uniformsMap.createUniform(
                "view");
        uniformsMap.createUniform("dirLight.direction");
        uniformsMap.createUniform("dirLight.ambient");
        uniformsMap.createUniform("dirLight.diffuse");
        uniformsMap.createUniform("dirLight.specular");

        //tambah loop untuk tambah light
        for(int i = 0; i < 10; i++){
            uniformsMap.createUniform("pointLight["+i+"].position");
            uniformsMap.createUniform("pointLight["+i+"].ambient");
            uniformsMap.createUniform("pointLight["+i+"].diffuse");
            uniformsMap.createUniform("pointLight["+i+"].specular");
            uniformsMap.createUniform("pointLight["+i+"].constant");
            uniformsMap.createUniform("pointLight["+i+"].linear");
            uniformsMap.createUniform("pointLight["+i+"].quadratic");
        }
        uniformsMap.createUniform("spotLight.position");
        uniformsMap.createUniform("spotLight.direction");
        uniformsMap.createUniform("spotLight.ambient");
        uniformsMap.createUniform("spotLight.diffuse");
        uniformsMap.createUniform("spotLight.specular");
        uniformsMap.createUniform("spotLight.constant");
        uniformsMap.createUniform("spotLight.linear");
        uniformsMap.createUniform("spotLight.quadratic");
        uniformsMap.createUniform("spotLight.cutOff");
        uniformsMap.createUniform("spotLight.outerCutOff");
        uniformsMap.createUniform("viewPos");
        this.color = color;
        model = new Matrix4f().identity();
        childObject = new ArrayList<>();
        centerPoint = new Vector3f(0f,0f,0f);
    }

    public void setupVAOVBO(){
        //set vao
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        //set vbo
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER,
                Utils.listoFloat(vertices),
                GL_STATIC_DRAW);
    }

    public void drawSetup(Camera camera, Projection projection){
        bind();
        uniformsMap.setUniform(
                "uni_color", color);
        uniformsMap.setUniform(
                "model", model);
        uniformsMap.setUniform(
                "view", camera.getViewMatrix());
        uniformsMap.setUniform(
                "projection", projection.getProjMatrix());
        uniformsMap.setUniform("dirLight.direction", new Vector3f(-0.2f, 1.0f, 0.3f));
        if(scene){
            uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.1f, 0.1f, 0.1f));
        } else {
            uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.8f, 0.8f, 0.8f));
        }
        uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.4f, 0.4f, 0.4f));
        uniformsMap.setUniform("dirLight.specular", new Vector3f(0.5f, 0.5f, 0.5f));

        //tambah posisi light
        Vector3f[] _pointLightPositions = {
                new Vector3f(0f, 1000f, -20f),
                new Vector3f(195f, 70f, -20f),
                new Vector3f(-190f, 70f, -13f),
                new Vector3f(0f, 65f, 225f),
                new Vector3f(-23f, 50f, -180f),
                new Vector3f(150f, 70f, -190f),
                new Vector3f(-190f, 70f, -150f),
                new Vector3f(140f,70f,140f),
                new Vector3f(-140f,70f,140f),
                new Vector3f(0f, 0f, 30f)
        };

        for(int i = 0; i < _pointLightPositions.length; i++){
            uniformsMap.setUniform("pointLight["+i+"].position", _pointLightPositions[i]);
            if(scene){
                uniformsMap.setUniform("pointLight["+i+"].ambient", new Vector3f(0.01f, 0.01f, 0.01f));
                uniformsMap.setUniform("pointLight["+i+"].diffuse", new Vector3f(0.8f, 0.8f, 0.8f));
                uniformsMap.setUniform("pointLight["+i+"].specular", new Vector3f(0.05f, 0.05f, 0.05f));
                uniformsMap.setUniform("pointLight["+i+"].constant", 1.0f);
                uniformsMap.setUniform("pointLight["+i+"].linear", 0.09f);
                uniformsMap.setUniform("pointLight["+i+"].quadratic", 0.032f);
            } else {
                uniformsMap.setUniform("pointLight["+i+"].ambient", new Vector3f(0.4f, 0.4f, 0.4f));
                uniformsMap.setUniform("pointLight["+i+"].diffuse", new Vector3f(0.8f, 0.8f, 0.8f));
                uniformsMap.setUniform("pointLight["+i+"].specular", new Vector3f(0.5f, 0.5f, 0.5f));
                uniformsMap.setUniform("pointLight["+i+"].constant", 1.0f);
                uniformsMap.setUniform("pointLight["+i+"].linear", 0.09f);
                uniformsMap.setUniform("pointLight["+i+"].quadratic", 0.032f);
            }
            if (i >= 0 && i < 5){
                uniformsMap.setUniform("pointLight["+i+"].ambient", new Vector3f(0.005f, 0.005f, 0.005f));
                uniformsMap.setUniform("pointLight["+i+"].diffuse", new Vector3f(0.8f, 0.8f, 255/255f));
                uniformsMap.setUniform("pointLight["+i+"].specular", new Vector3f(0.5f, 0.5f, 0.5f));
                uniformsMap.setUniform("pointLight["+i+"].constant", 1.0f);
                uniformsMap.setUniform("pointLight["+i+"].linear", 0.0014f);
                uniformsMap.setUniform("pointLight["+i+"].quadratic", 0.000007f);
            }
            if (i >= 5 && i < 9){
                uniformsMap.setUniform("pointLight["+i+"].ambient", new Vector3f(0.005f, 0.005f, 0.005f));
                uniformsMap.setUniform("pointLight["+i+"].diffuse", new Vector3f(1f, 1f, 102/255f));
                uniformsMap.setUniform("pointLight["+i+"].specular", new Vector3f(0.5f, 0.5f, 0.5f));
                uniformsMap.setUniform("pointLight["+i+"].constant", 1.0f);
                uniformsMap.setUniform("pointLight["+i+"].linear", 0.007f);
                uniformsMap.setUniform("pointLight["+i+"].quadratic", 0.0002f);
            }
            if (i == 9){
                uniformsMap.setUniform("pointLight["+i+"].ambient", new Vector3f(0.005f, 0.005f, 0.005f));
                uniformsMap.setUniform("pointLight["+i+"].diffuse", new Vector3f(1f, 51/255f, 51/255f));
                uniformsMap.setUniform("pointLight["+i+"].specular", new Vector3f(0.5f, 0.5f, 0.5f));
                uniformsMap.setUniform("pointLight["+i+"].constant", 1.0f);
                uniformsMap.setUniform("pointLight["+i+"].linear", 0.014f);
                uniformsMap.setUniform("pointLight["+i+"].quadratic", 0.0007f);
            }

        }

        // spotLight
        uniformsMap.setUniform("spotLight.position", camera.getPosition());
        uniformsMap.setUniform("spotLight.direction", camera.getDirection());
        uniformsMap.setUniform("spotLight.ambient", new Vector3f(0.0f, 0.0f ,0.0f));
        uniformsMap.setUniform("spotLight.diffuse", new Vector3f(1.0f, 1.0f, 1.0f));
        uniformsMap.setUniform("spotLight.specular", new Vector3f(1.0f, 1.0f, 1.0f));
        uniformsMap.setUniform("spotLight.constant", 1.0f);
        uniformsMap.setUniform("spotLight.linear", 0.09f);
        uniformsMap.setUniform("spotLight.quadratic", 0.032f);
        uniformsMap.setUniform("spotLight.cutOff", 0f);
        uniformsMap.setUniform("spotLight.outerCutOff", 0f);

        uniformsMap.setUniform("viewPos", camera.getPosition());

        // Bind VBO
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3,
                GL_FLOAT,
                false,
                0, 0);

    }

    public void draw(Camera camera, Projection projection){
        drawSetup(camera, projection);
        // Draw the vertices
        //optional
        glLineWidth(10); //ketebalan garis
        glPointSize(10); //besar kecil vertex
        //wajib
        //GL_LINES
        //GL_LINE_STRIP
        //GL_LINE_LOOP
        //GL_TRIANGLES
        //GL_TRIANGLE_FAN
        //GL_POINT
        glDrawArrays(GL_TRIANGLES,
                0,
                vertices.size());
        for(Object child:childObject){
            child.draw(camera,projection);
        }
    }

    public void draw_ground(Camera camera, Projection projection){
        drawSetup(camera, projection);
        // Draw the vertices
        //optional
        glLineWidth(10); //ketebalan garis
        glPointSize(10); //besar kecil vertex
        //wajib
        //GL_LINES
        //GL_LINE_STRIP
        //GL_LINE_LOOP
        //GL_TRIANGLES
        //GL_TRIANGLE_FAN
        //GL_POINT
        glDrawArrays(GL_POLYGON,
                0,
                vertices.size());
        for(Object child:childObject){
            child.draw(camera,projection);
        }
    }

    public void translateObject(Float offsetX,Float offsetY,Float offsetZ){
        model = new Matrix4f().translate(offsetX,offsetY,offsetZ).mul(new Matrix4f(model));
        // update center point tak apus buat rotasi di tempat
        for(Object child:childObject){
            child.translateObject(offsetX,offsetY,offsetZ);
        }
    }

    public void rotateObject(Float degree, Float x,Float y,Float z){
        model = new Matrix4f().rotate(degree,x,y,z).mul(new Matrix4f(model));
        // update center point tak apus buat rotasi di tempat
        for(Object child:childObject){
            child.rotateObject(degree,x,y,z);
        }
    }
    public void updateCenterPoint(){
        Vector3f destTemp = new Vector3f();
        model.transformPosition(0.0f,0.0f,0.0f,destTemp);
//        centerPoint.set(0,destTemp.x);
//        centerPoint.set(1,destTemp.y);
//        centerPoint.set(2,destTemp.z);
        this.centerPoint = destTemp;
    }
    public void scaleObject(Float scaleX,Float scaleY,Float scaleZ){
        model = new Matrix4f().scale(scaleX,scaleY,scaleZ).mul(new Matrix4f(model));
        for(Object child:childObject){
            child.translateObject(scaleX,scaleY,scaleZ);
        }
    }

    public void drawLine(Camera camera, Projection projection) {
        drawSetup(camera, projection);
        glDrawArrays(GL_LINE_LOOP, 0,
                vertices.size());
    }
    public ArrayList<Boolean> checkCollision(Vector3f position, Vector3f size){

        boolean xCol = false;
        boolean yCol = false;
        boolean zCol = false;

        return new ArrayList<>();
    }

    public Vector3f getSize(){
        return new Vector3f();
    }
}
