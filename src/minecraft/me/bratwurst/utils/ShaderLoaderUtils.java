package me.bratwurst.utils;

public class ShaderLoaderUtils {
    public static ShaderUtils shaderLoader;
    public static String shader = "#ifdef GL_ES\nprecision mediump float;\n#endif\n\nuniform float time;\nuniform vec2 mouse;\nuniform vec2 resolution;\n\nvec2 hash(vec2 p)\n{\n    mat2 m = mat2(  13.85, 47.77,\n                    99.41, 88.48\n                );\n\n    return fract(sin(m*p) * 46738.29);\n}\n\nfloat voronoi(vec2 p)\n{\n    vec2 g = floor(p);\n    vec2 f = fract(p);\n\n    float distanceToClosestFeaturePoint = 1.0;\n    for(int y = -1; y <= 1; y++)\n    {\n        for(int x = -1; x <= 5; x++)\n        {\n            vec2 latticePoint = vec2(x, y);\n            float currentDistance = distance(latticePoint + hash(g+latticePoint), f);\n            distanceToClosestFeaturePoint = min(distanceToClosestFeaturePoint, currentDistance);\n        }\n    }\n\n    return distanceToClosestFeaturePoint;\n}\n\nvoid main( void )\n{\n    vec2 uv = ( gl_FragCoord.xy / resolution.xy ) * 2.0 - 1.0;\n    uv.x *= resolution.x / resolution.y;\n\n    float offset = voronoi(uv*10.0 + vec2(time));\n    float t = 1.0/abs(((uv.x + sin(uv.y + time)) + offset) * 30.0);\n\n    float r = voronoi( uv * 1.0 ) * 10.0;\n    vec3 finalColor = vec3(10.0 * uv.y, 2.0, 1.0 * r) * t;\n    \n    gl_FragColor = vec4(finalColor, 1.0 );\n}";


    public static void render() {
        shaderLoader.renderFirst();
        shaderLoader.addDefaultUniforms();
        shaderLoader.renderSecond();
    }

    static {
        shaderLoader = new ShaderUtils(shader);
    }
}

