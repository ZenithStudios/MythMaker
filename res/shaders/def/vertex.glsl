#version 400

in vec3 position;

out vec3 color;

uniform mat4 transMat;
uniform mat4 projMat;
uniform mat4 viewMat;

void main() {
    gl_Position = projMat * viewMat * transMat * vec4(position, 1.0);
    color = vec3(position.x + 0.2, 0.5, position.y + 0.2);
}
