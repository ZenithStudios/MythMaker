#version 400

in vec3 position;
in vec2 uv;

out vec2 uv_out;

uniform mat4 transMat;
uniform mat4 projMat;
uniform mat4 viewMat;

uniform float rows;
uniform float columns;
uniform vec2 uvoffset;

void main() {
    gl_Position = projMat * viewMat * transMat * vec4(position, 1.0);
    uv_out = vec2(uv.x/columns + uvoffset.x, uv.y/rows + uvoffset.y);
}
