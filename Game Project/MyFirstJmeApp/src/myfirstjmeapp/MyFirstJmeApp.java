/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myfirstjmeapp;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 *
 * @author wgm3389
 */
public class MyFirstJmeApp extends SimpleApplication{
    private float time = 0;

    public static void main(String[] args) {
        SimpleApplication app = new MyFirstJmeApp();
        app.start();
    }
    
    private Geometry player;
    Boolean isRunning = true;
    
    @Override
    public void simpleInitApp(){
        /** Floor Node to */
        Node mapNode = new Node("mapNode");
        rootNode.attachChild(mapNode);
        
        /** Temporary Player */
        Box playerBox = new Box(Vector3f.ZERO, 1, 1, 1);
        player = new Geometry("Box", playerBox);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        player.setMaterial(mat);
        rootNode.attachChild(player);
        
        
        Box box = new Box(Vector3f.ZERO, 1, 1, 1);
        Geometry geom = new Geometry("Box", box);
        mat.setColor("Color", ColorRGBA.Red);
        geom.setMaterial(mat);
        rootNode.attachChild(geom);
        
        /** Model Test */
        Spatial sofa = assetManager.loadModel("Models/Sofa/small sofa.obj");
        Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        sofa.setMaterial(mat_default);
        sofa.scale(-10,-10,-10);
        sofa.setLocalTranslation(2,3,0);
        rootNode.attachChild(sofa);
        
        
        /** Terrain/Map */
        Box floor = new Box(new Vector3f(0,-1,0), 100, (float)0.05, 100);
        Geometry floorGeom = new Geometry("Floor", floor);
        Material matGround = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matGround.setTexture("ColorMap", assetManager.loadTexture("Textures/Terrain/ground.jpg"));
        floorGeom.setMaterial(matGround);
        mapNode.attachChild(floorGeom);
        
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1f, 2f, -1f).normalizeLocal());
        mapNode.addLight(sun);
        
        /** WaterMark */
        guiNode.detachAllChildren();;
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText watermark = new BitmapText(guiFont, false);
        watermark.setSize(guiFont.getCharSet().getRenderedSize());
        watermark.setText("Foopod Productions");
        watermark.setLocalTranslation(0f, settings.getHeight() , 0);
        guiNode.attachChild(watermark);
        
        initKeys();
        
    }   
    
    private void initKeys(){
        inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_RIGHT));
        
        inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE),
                                      new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        
        inputManager.addListener(actionListener, new String[]{"Pause"});
        inputManager.addListener(analogListener, new String[]{"Left", "Right", "Rotate"});
    }
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if(name.equals("Pause") && !isPressed){
                isRunning = !isRunning;
            }
        }
    };
        
    private AnalogListener analogListener = new AnalogListener() {
            public void onAnalog(String name, float value, float tpf) {
                if(isRunning){
                    if(name.equals("Rotate")){
                        player.rotate(0, 5*value*speed, 0);
                    }else if(name.equals("Right")){
                        Vector3f v = player.getLocalTranslation();
                        player.setLocalTranslation(v.x + value*speed, v.y, v.z);
                    }else if(name.equals("Left")){
                        Vector3f v = player.getLocalTranslation();
                        player.setLocalTranslation(v.x - value*speed, v.y, v.z);
                    }else{
                        System.out.println("Press P to unpause.");
                    }
                }
            }
        };
    
    public void simpleUpdate(float tpf){
//        rootNode.getChild("Box").rotate(1 *tpf, 1*tpf, 1*tpf);\
        time += tpf;
        
        Spatial movingBox = rootNode.getChild("Box");
        
        movingBox.move(5*tpf, 0, 0);
        movingBox.setLocalTranslation(0f, (float)Math.abs(2*Math.sin(2*time)), -5*time);
        cam.setLocation(new Vector3f(0,0,0));
    }
}
