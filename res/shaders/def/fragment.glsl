#version 400

in vec2 uv_out;

out vec4 out_color;

uniform sampler2D textureSampler;

void main() {
    //out_color = vec4(255, 255, 255, 255);
    out_color = texture(textureSampler, uv_out);
}
