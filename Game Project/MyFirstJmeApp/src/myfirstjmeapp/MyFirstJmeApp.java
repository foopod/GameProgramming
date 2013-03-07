/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myfirstjmeapp;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
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
    private Spatial sofa;
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

        /** Map Creation */
        Map testMap = new Map(rootNode, assetManager);
        testMap.mapInit();
        
        /** Model Test */
        float scaleSofa = .01f;
        sofa = assetManager.loadModel("Models/Sofa/small sofa.obj");
        Material matDefault = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matDefault.setTexture("ColorMap", assetManager.loadTexture("Models/Sofa/OBJ_Chair_Sofa_D_02.tga"));
        sofa.setMaterial(matDefault);
        sofa.scale(scaleSofa);
        sofa.move(0,-5,-10);
        rootNode.attachChild(sofa);
        
        
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
        inputManager.addMapping("Up",   new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Down",   new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_L));
        
        
        
        
        inputManager.addListener(actionListener, new String[]{"Pause"});
        inputManager.addListener(analogListener, new String[]{"Left", "Right", "Up", "Down"});
    }
    
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            if(name.equals("Pause") && !isPressed){
                isRunning = !isRunning;
                System.out.println(isRunning);
            }
        }
    };
        
    private AnalogListener analogListener = new AnalogListener() {
            public void onAnalog(String name, float value, float tpf) {
                if(isRunning){
                    int moveSpeed = 20;
                    if(name.equals("Rotate")){
                        sofa.rotate(0, 5*value*speed, 0);
                    }else if(name.equals("Up")){
                        Vector3f vs = new Vector3f(0,0,moveSpeed*tpf);
                        Quaternion sofaRotation = sofa.getLocalRotation();
                        Vector3f moveVector = sofaRotation.mult(vs);
                        sofa.move(moveVector);
                    }else if(name.equals("Right")){
                        sofa.rotate(0, -5*value*speed, 0);
                    }else if(name.equals("Left")){
                        sofa.rotate(0, 5*value*speed, 0);
                    }else if(name.equals("Down")){
                        Vector3f vs = new Vector3f(0,0,-moveSpeed*tpf);
                        Quaternion sofaRotation = sofa.getLocalRotation();
                        Vector3f moveVector = sofaRotation.mult(vs);
                        sofa.move(moveVector);
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
