#version 330 core

layout(location = 0) in vec4 position;
layout(location = 1) in vec2 tc;

uniform mat4 pr_matrix;
uniform mat4 vw_matrix = mat4(1.0);
uniform mat4 ml_matrix = mat4(1.0);

//uniform float numberOfRows;
//uniform float numberOfCols;
//uniform vec2 offset;

uniform vec4 animationData;

out DATA
{
    vec2 tc;
} vs_out;

void main(){
    gl_Position = pr_matrix * vw_matrix * ml_matrix * vec4(position.xyz, 1.0);
    vs_out.tc = vec2(tc.x / animationData.w, tc.y / animationData.z) + animationData.xy;
    vs_out.tc = vec2(tc.x / animationData.w, tc.y / animationData.z) + animationData.xy;
//
//    vs_out.tc = vec2(tc.x / numberOfCols, tc.y / numberOfRows) + offset;
//    vs_out.tc = vec2(tc.x / numberOfCols, tc.y / numberOfRows) + offset;
}