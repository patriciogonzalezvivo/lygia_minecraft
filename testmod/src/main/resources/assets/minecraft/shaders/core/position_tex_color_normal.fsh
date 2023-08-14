#version 150

#moj_import <fog.glsl>
#moj_import <lygia/generative/worley.glsl>

uniform mat4 ProjMat;
uniform mat4 ModelViewMat;

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform float GameTime;

in vec2 texCoord0;
in float vertexDistance;
in vec4 vertexColor;
in vec4 normal;

out vec4 fragColor;

void main() {
    vec4 clouds = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    if (clouds.a < 0.1) {
        discard;
    }
    
    vec4 color = vec4(vec3(0.0), 1.0);
    float time = GameTime * 720;
    vec2 st = texCoord0 * 10;
    color += worley(vec3(st * 10., time));
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}