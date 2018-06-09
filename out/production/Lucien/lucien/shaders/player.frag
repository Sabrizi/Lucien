#version 330 core

 layout(location = 0) out vec4 color;

 in DATA
 {
     vec2 tc;
 } fs_in;

  uniform sampler2D tex;

 void main(){
//    color = vec4(0.5, 0.0, 0.50, 1.0);
//    color = vec4(fs_in.tc.x, fs_in.tc.y, 0.00, 1.0);

    color = vec4(texture(tex, fs_in.tc));
    if(color.w < 1.0){
        discard;
    }
//    color *= 3.0 / (length(ball - fs_in.position.xy) + 0.0) + 0.2;
    color.w = 1.0;
 }