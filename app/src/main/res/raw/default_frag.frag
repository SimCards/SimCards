#version 100
precision mediump float;
uniform vec4 vColor;
uniform sampler2D uTexture;
varying vec2 vTexCoordinate;

void main() {
      vec4 textureColor = vColor * texture2D(uTexture, vTexCoordinate);
      // Discard transparent pixels.
      if (textureColor.a <= 0.01) {
            discard;
      }
      gl_FragColor = textureColor;
}