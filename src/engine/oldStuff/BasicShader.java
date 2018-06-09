package engine.oldStuff;

import engine.oldStuff.Shader;

@Deprecated
public class BasicShader extends Shader {

    private static final String FRAG_PATH = "basic.frag";
    private static final String VERT_PATH = "basic.vert";

    public BasicShader(){
        super(FRAG_PATH, VERT_PATH);
    }

    @Override
    public void bindAttributes(){
        super.bindAttribute(0, "position");
    }

    public void getAllUniformLocations(){

    }
}
