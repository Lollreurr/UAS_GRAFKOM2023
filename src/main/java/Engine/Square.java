package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class Square extends Object{

    float radiusX;
    float radiusY;
    float radiusZ;
    List<Integer> index;
    Vector3f centerPoint;
    int ibo;

    // shading color
    List<Vector3f> normal;
    int nbo;

    public Square(List<ShaderProgram.ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, Vector3f centerPoint,
                  float rX, float rY, float rZ) {
        super(shaderModuleDataList, vertices, color);
        this.radiusX = rX;
        this.radiusY = rY;
        this.radiusZ = rZ;
        this.centerPoint = centerPoint;
        createBox();
        setupVAOVBO();
    }

    public void createBox(){
        vertices.clear();
        Vector3f temp = new Vector3f();
        ArrayList<Vector3f> tempVertices = new ArrayList<>();
        //Titik 1 kiri atas belakang
        temp.x = centerPoint.get(0) - radiusX / 2;
        temp.y = centerPoint.get(1) + radiusY / 2;
        temp.z = centerPoint.get(2) - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 2 kiri bawah belakang
        temp.x = centerPoint.get(0) - radiusX / 2;
        temp.y = centerPoint.get(1) - radiusY / 2;
        temp.z = centerPoint.get(2) - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 3 kanan bawah belakang
        temp.x = centerPoint.get(0) + radiusX / 2;
        temp.y = centerPoint.get(1) - radiusY / 2;
        temp.z = centerPoint.get(2) - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 4 kiri atas belakang
        temp.x = centerPoint.get(0) + radiusX / 2;
        temp.y = centerPoint.get(1) + radiusY / 2;
        temp.z = centerPoint.get(2) - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 5 kiri atas depan
        temp.x = centerPoint.get(0) - radiusX / 2;
        temp.y = centerPoint.get(1) + radiusY / 2;
        temp.z = centerPoint.get(2) + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 6 kiri bawah depan
        temp.x = centerPoint.get(0) - radiusX / 2;
        temp.y = centerPoint.get(1) - radiusY / 2;
        temp.z = centerPoint.get(2) + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 7 kanan bawah depan
        temp.x = centerPoint.get(0) + radiusX / 2;
        temp.y = centerPoint.get(1) - radiusY / 2;
        temp.z = centerPoint.get(2) + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 8 kiri atas depan
        temp.x = centerPoint.get(0) + radiusX / 2;
        temp.y = centerPoint.get(1) + radiusY / 2;
        temp.z = centerPoint.get(2) + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();

        // kotak belakang
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));

        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(0));

        // kotak depan
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(4));

        // kotak samping kiri
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));

        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(0));

        // kotak samping kanan
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(3));

        // kotak atas
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(7));

        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(0));

        // kotak bawah
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(1));

        normal = new ArrayList<>(Arrays.asList(
                // kotak belakang
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f),
                new Vector3f(0.0f, 0.0f, -1.0f),

                // kotak depan
                new Vector3f(0.0f, 0.0f, 1.0f),
                new Vector3f(0.0f, 0.0f, 1.0f),
                new Vector3f(0.0f, 0.0f, 1.0f),
                new Vector3f(0.0f, 0.0f, 1.0f),
                new Vector3f(0.0f, 0.0f, 1.0f),
                new Vector3f(0.0f, 0.0f, 1.0f),

                // kotak kiri
                new Vector3f(-1.0f, 0.0f, 0.0f),
                new Vector3f(-1.0f, 0.0f, 0.0f),
                new Vector3f(-1.0f, 0.0f, 0.0f),
                new Vector3f(-1.0f, 0.0f, 0.0f),
                new Vector3f(-1.0f, 0.0f, 0.0f),
                new Vector3f(-1.0f, 0.0f, 0.0f),

                // kotak kanan
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f),
                new Vector3f(1.0f, 0.0f, 0.0f),

                // kotak atas
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f),
                new Vector3f(0.0f, 1.0f, 0.0f),

                // kotak bawah
                new Vector3f(0.0f, -1.0f, 0.0f),
                new Vector3f(0.0f, -1.0f, 0.0f),
                new Vector3f(0.0f, -1.0f, 0.0f),
                new Vector3f(0.0f, -1.0f, 0.0f),
                new Vector3f(0.0f, -1.0f, 0.0f),
                new Vector3f(0.0f, -1.0f, 0.0f)
        ));
    }

    public void drawObject(Camera camera, Projection projection) {
        drawSetup(camera, projection);
        // Draw the vertices
        //optional
        glDrawElements(GL_TRIANGLES, index.size(), GL_UNSIGNED_INT, 0);
        for (Object child: childObject){
            child.draw(camera, projection);
        }
    }

    public void setupVAOVBO(){
        super.setupVAOVBO();

        //set vbo
        nbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glBufferData(GL_ARRAY_BUFFER,
                Utils.listoFloat(normal),
                GL_STATIC_DRAW);

//        uniformsmap.createUniform("lightColor", new Vector3f(1.0f, 1.0f, 0.0f));
////        // lightpos ngasih tau object dari mana arah cahayanya
//        uniformsmap.createUniform("lightPos", new Vector3f(1.0f, 1.0f, 1.0f));
    }

    public void drawSetup(Camera camera, Projection projection) {
        super.drawSetup(camera, projection);
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glVertexAttribPointer(1,
                3, GL_FLOAT,
                false,
                0, 0);
    }

    public void setNormal(List<Vector3f> normal) {
        this.normal = normal;
        setupVAOVBO();
    }


    public List<Vector3f> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vector3f> vertices) {
        this.vertices = vertices;
        setupVAOVBO();
    }

    public void setIndicies(List<Integer> indicies){
        this.index = indicies;
        setupVAOVBO();
        //ibo
        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,Utils.listoInt(index),GL_STATIC_DRAW);
    }

    public ArrayList<Boolean> checkCollision(Vector3f position, Vector3f size){

        boolean xPosCol = false;
        boolean yPosCol = false;
        boolean zPosCol = false;
        boolean xNegCol = false;
        boolean yNegCol = false;
        boolean zNegCol = false;

        if ((position.x + size.x/2) >= (centerPoint.x - radiusX/2) && (position.x + size.x/2) <= (centerPoint.x + radiusX/2)) {
            xPosCol = true;
        }
        if ((position.x - size.x/2) >= (centerPoint.x - radiusX/2) && (position.x - size.x/2) <= (centerPoint.x + radiusX/2)) {
            xNegCol = true;
        }
        if ((position.y + size.y/2) >= (centerPoint.y - radiusY/2) && (position.y + size.y/2) <= (centerPoint.y + radiusY/2)){
            yPosCol = true;
        }
        if ((position.y - size.y/2) >= (centerPoint.y - radiusY/2) && (position.y - size.y/2) <= (centerPoint.y + radiusY/2)){
            yNegCol = true;
        }
        if ((position.z + size.z/2) >= (centerPoint.z - radiusZ/2) && (position.z + size.z/2) <= (centerPoint.z + radiusZ/2)){
            zPosCol = true;
        }
        if ((position.z - size.z/2) >= (centerPoint.z - radiusZ/2) && (position.z - size.z/2) <= (centerPoint.z + radiusZ/2)) {
            zNegCol = true;
        }


        ArrayList<Boolean> movement = new ArrayList<>();
        if ((xPosCol || xNegCol) && (yPosCol || yNegCol) && (zPosCol || zNegCol)){
            movement.add(true);
        }
        else{
            movement.add(false);
        }
        movement.add(xPosCol);
        movement.add(xNegCol);
        movement.add(yPosCol);
        movement.add(yNegCol);
        movement.add(zPosCol);
        movement.add(zNegCol);

        return movement;
    }

    public Vector3f getSize(){
        return new Vector3f(radiusX, radiusY, radiusZ);
    }
}
