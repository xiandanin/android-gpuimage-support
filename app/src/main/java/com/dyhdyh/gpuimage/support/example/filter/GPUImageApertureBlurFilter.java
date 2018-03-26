package com.dyhdyh.gpuimage.support.example.filter;

import android.opengl.GLES20;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageTwoPassTextureSamplingFilter;

/**
 * 光圈模糊
 * @author dengyuhan
 *         created 2018/3/20 16:05
 */
public class GPUImageApertureBlurFilter extends GPUImageTwoPassTextureSamplingFilter {

    public static String VERTEX_SHADER = "attribute vec4 position;\n" +
            " attribute vec4 inputTextureCoordinate;\n" +
            " \n" +
            " const int GAUSSIAN_SAMPLES = 9;\n" +
            " \n" +
            " uniform float texelWidthOffset;\n" +
            " uniform float texelHeightOffset;\n" +
            " varying vec2 textureCoordinate;\n" +
            " varying vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n" +
            " \n" +
            " void main()\n" +
            " {\n" +
            "     gl_Position = position;\n" +
            "     textureCoordinate = inputTextureCoordinate.xy;\n" +
            "     \n" +
            "     // Calculate the positions for the blur\n" +
            "     int multiplier = 0;\n" +
            "     vec2 blurStep;\n" +
            "     vec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);\n" +
            "     \n" +
            "     for (int i = 0; i < GAUSSIAN_SAMPLES; i++) {\n" +
            "         multiplier = (i - ((GAUSSIAN_SAMPLES - 1) / 2));\n" +
            "         // Blur in x (horizontal)\n" +
            "         blurStep = float(multiplier) * singleStepOffset;\n" +
            "         blurCoordinates[i] = inputTextureCoordinate.xy + blurStep;\n" +
            "     }\n" +
            " }";


    public static String FRAGMENT_SHADER = "uniform sampler2D inputImageTexture;\n" +
            " \n" +
            " const lowp int GAUSSIAN_SAMPLES = 9;\n" +
            " \n" +
            " varying highp vec2 textureCoordinate;\n" +
            " varying highp vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n" +
            " \n" +

            " uniform highp float aspectRatio;\n" +
            " \n" +
            " uniform lowp float excludeCircleRadius;\n" +
            " uniform lowp vec2 excludeCirclePoint;\n" +
            " uniform lowp float excludeBlurSize;\n" +
            " void main()\n" +
            " {\n" +
            "     vec2 textureCoordinateToUse = vec2(textureCoordinate.x, (textureCoordinate.y * aspectRatio + 0.5 - 0.5 * aspectRatio));\n" +
            "     vec4 sharpImageColor = texture2D(inputImageTexture, textureCoordinate);\n" +
            "     \n" +
            "     //2\n" +
            "     lowp vec4 sum = vec4(0.0);\n" +
            "     sum += texture2D(inputImageTexture, blurCoordinates[0]) * 0.05;\n" +
            "     sum += texture2D(inputImageTexture, blurCoordinates[1]) * 0.09;\n" +
            "     sum += texture2D(inputImageTexture, blurCoordinates[2]) * 0.12;\n" +
            "     sum += texture2D(inputImageTexture, blurCoordinates[3]) * 0.15;\n" +
            "     sum += texture2D(inputImageTexture, blurCoordinates[4]) * 0.18;\n" +
            "     sum += texture2D(inputImageTexture, blurCoordinates[5]) * 0.15;\n" +
            "     sum += texture2D(inputImageTexture, blurCoordinates[6]) * 0.12;\n" +
            "     sum += texture2D(inputImageTexture, blurCoordinates[7]) * 0.09;\n" +
            "     sum += texture2D(inputImageTexture, blurCoordinates[8]) * 0.05;\n" +
            "     \n" +
            "     float distanceFromCenter = distance(excludeCirclePoint, textureCoordinateToUse);\n" +
            "     \n" +
            "     gl_FragColor = mix(sharpImageColor, sum, smoothstep(excludeCircleRadius - excludeBlurSize, excludeCircleRadius, distanceFromCenter));\n" +
            " }";


    private float excludeCircleRadius;
    private float[] excludeCirclePoint;
    private float excludeBlurSize;
    private float aspectRatio;
    protected float mBlurSize = 1f;

    private int excludeCircleRadiusLocation;
    private int excludeCirclePointLocation;
    private int excludeBlurSizeLocation;
    private int aspectRatioLocation;

    public GPUImageApertureBlurFilter() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, VERTEX_SHADER, FRAGMENT_SHADER);
    }

    @Override
    public void onInit() {
        super.onInit();
    }

    @Override
    protected void initTexelOffsets() {
        super.initTexelOffsets();

        GPUImageFilter filter = mFilters.get(0);
        excludeCircleRadiusLocation = GLES20.glGetUniformLocation(filter.getProgram(), "excludeCircleRadius");
        excludeCirclePointLocation = GLES20.glGetUniformLocation(filter.getProgram(), "excludeCirclePoint");
        excludeBlurSizeLocation = GLES20.glGetUniformLocation(filter.getProgram(), "excludeBlurSize");
        aspectRatioLocation = GLES20.glGetUniformLocation(filter.getProgram(), "aspectRatio");
        setExcludeCircleRadius(filter,0.3f);
        setExcludeCirclePoint(filter,new float[]{0.5f, 0.5f});
        setExcludeBlurSize(filter,0.15f);
        setAspectRatio(filter,1f);

        filter = mFilters.get(1);
        excludeCircleRadiusLocation = GLES20.glGetUniformLocation(filter.getProgram(), "excludeCircleRadius");
        excludeCirclePointLocation = GLES20.glGetUniformLocation(filter.getProgram(), "excludeCirclePoint");
        excludeBlurSizeLocation = GLES20.glGetUniformLocation(filter.getProgram(), "excludeBlurSize");
        aspectRatioLocation = GLES20.glGetUniformLocation(filter.getProgram(), "aspectRatio");
        setExcludeCircleRadius(filter,0.3f);
        setExcludeCirclePoint(filter,new float[]{0.5f, 0.5f});
        setExcludeBlurSize(filter,0.15f);
        setAspectRatio(filter,1f);
    }

    @Override
    public void onInitialized() {
        super.onInitialized();
    }

    public void setExcludeCircleRadius(GPUImageFilter filter, float excludeCircleRadius) {
        this.excludeCircleRadius = excludeCircleRadius;
        filter.setFloat(excludeCircleRadiusLocation, excludeCircleRadius);
    }

    public void setExcludeCirclePoint(GPUImageFilter filter, float[] excludeCirclePoint) {
        this.excludeCirclePoint = excludeCirclePoint;
        filter.setFloatVec2(excludeCirclePointLocation, excludeCirclePoint);
    }

    public void setExcludeBlurSize(GPUImageFilter filter, float excludeBlurSize) {
        this.excludeBlurSize = excludeBlurSize;
        filter.setFloat(excludeBlurSizeLocation, excludeBlurSize);
    }


    public void setAspectRatio(GPUImageFilter filter, float aspectRatio) {
        this.aspectRatio = aspectRatio;
        filter.setFloat(aspectRatioLocation, aspectRatio);
    }

    public void setBlurSize(float mBlurSize) {
        this.mBlurSize = mBlurSize;
        runOnDraw(new Runnable() {
            @Override
            public void run() {
                initTexelOffsets();
            }
        });
    }

    @Override
    public float getVerticalTexelOffsetRatio() {
        return mBlurSize;
    }

    @Override
    public float getHorizontalTexelOffsetRatio() {
        return mBlurSize;
    }
}
