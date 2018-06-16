package engine.gui;

import engine.math.Vector3;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GUIElement {

    private ArrayList<GUIComponent> components = new ArrayList<>();
    private Vector3 position;

    public GUIElement(Vector3 position){
        this.position = position;
    }

    public void update(){
        for(GUIComponent component : components){
            component.update();
        }
    }

    public void render(){
        for(GUIComponent component : components){
            component.render();
        }
    }

    public void addComponent(GUIComponent component){
        component.setParent(this);
        components.add(component);
    }

    public void removeComponent(GUIComponent component){
        component.setParent(null);
        components.remove(component);
    }

    public Vector3 getPosition() {
        return position;
    }
}
