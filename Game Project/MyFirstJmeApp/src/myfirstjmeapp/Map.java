/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myfirstjmeapp;

import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Jonathon
 */
public class Map {
    protected Node rootNode;
    protected AssetManager assetManager;
    public Map(Node rootNode, AssetManager assetManager){
        this.rootNode = rootNode;
        this.assetManager = assetManager;
    }
    
    protected void mapInit(){
        Node mapNode = new Node("mapNode");
        rootNode.attachChild(mapNode);
        
        /** Terrain/Map */
        Box floor = new Box(new Vector3f(0,-20,0), 100, 1, 100);
        Geometry floorGeom = new Geometry("Floor", floor);
        Material matGround = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matGround.setTexture("ColorMap", assetManager.loadTexture("Textures/Terrain/ground.jpg"));
        floorGeom.setMaterial(matGround);
        mapNode.attachChild(floorGeom);
        
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, 2, -1).normalizeLocal());
        mapNode.addLight(sun);
        
        /** Box Field */
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);

        
        for(int i = -30; i < 30; i+=5){
            for(int j = -30; j < 30; j+=5){
                Box box = new Box(new Vector3f(i,-5,j), .2f, .2f, .2f);
                Geometry boxGeom = new Geometry("Floor", box);
                boxGeom.setMaterial(mat);
                mapNode.attachChild(boxGeom);
            }
        }
    }
}
