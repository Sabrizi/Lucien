#version 330 core

in vec4 position;

out vec4 color;

void main(){
    color = vec4(position.xyz, 1.0);
}