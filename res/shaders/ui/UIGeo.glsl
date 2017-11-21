#version 330

layout (points) in;
layout (triangle_strip, max_vertices = 24) out;

out vec4 final_color;

uniform mat4 transMat;
uniform mat4 projMat;
uniform mat4 viewMat;
uniform vec4 inColor;

void addVert(vec2 offset, vec4 color) {
    vec4 worldpos = gl_in[0].gl_Position + vec4(offset, 0.0, 0.0);
    gl_Position = projMat * viewMat * transMat * worldpos;
    final_color = color;

    EmitVertex();
}

void main() {
    addVert(vec2(-100,100), inColor);
    addVert(vec2(-100,-100), inColor);
    addVert(vec2(100,100), inColor);
    addVert(vec2(100,-100), inColor);

    EndPrimitive();
}
