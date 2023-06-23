#version 330
layout (location=0) in vec3 position;

// normal (normal sama Normal beda)
layout (location=1) in vec3 normal;
out vec3 Normal;

// diffuse
out vec3 FragPos;

uniform mat4 model;

// tambahan untuk projection
uniform mat4 view;
uniform mat4 projection;
void main()
{
    //vec4(position,alpha)
    // selalu dibalik (apa yang dilihat di internet harus dibalik)
    // karena komputer baca dari kanan (?)

    //gl_Position = model * vec4(position, 1.0);

    gl_Position = projection * view * model * vec4(position, 1.0);

    FragPos = vec3(model * vec4(position, 1.0));
    Normal = normalize(model * vec4(normal, 1.0)).xyz;
}