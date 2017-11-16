#version 330

in vec4 final_color;

out vec4 out_color;

void main() {
    out_color = final_color/255;
}
