package com.dyhdyh.gpuimage.support.example.filter;

import android.opengl.GLES20;

import jp.co.cyberagent.android.gpuimage.GPUImageTwoInputFilter;

/**
 * @author dengyuhan
 *         created 2018/3/19 16:46
 */
public class ApertureBlurBlendFilter extends GPUImageTwoInputFilter {

    private float mAmount;    //0~100
    private float[] mCenter;    //center is (0.5, 0.5)
    private float mInnerr;    //half width -> r = 1
    private float mOuterr;    //half width -> r = 1
    private float[] mSizeRegionOfInterest;
    private float[] mTextSize;

    private int amountLocation;
    private int centerLocation;
    private int innerrLocation;
    private int outerrLocation;
    private int sizeRegionOfInterestLocation;
    private int textSizeLocation;


    public static String FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n" +
            " varying highp vec2 textureCoordinate2;\n" +
            " \n" +
            " uniform sampler2D inputImageTexture;\n" +
            " uniform sampler2D inputImageTexture2;\n" +
            " \n" +
            " \n" +
            " uniform highp float amount;\n" +
            " uniform highp vec2 center;\n" +
            " uniform highp float innerr;\n" +
            " uniform highp float outerr;\n" +
            " uniform highp vec2  textSize;\n" +
            " uniform highp vec2  sizeRegionOfInterest;\n" +
            " \n" +
            " \n" +
            " void main()\n" +
            " {\n" +
            "     highp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n" +
            "     highp vec4 blurColor = texture2D(inputImageTexture2, textureCoordinate2);\n" +
            "     \n" +
            "     \n" +
            "     highp float minImageSize = min(textSize.x, textSize.y);\n" +
            "     highp float minRegionSize = min(textSize.x*sizeRegionOfInterest.x, textSize.y*sizeRegionOfInterest.y);\n" +
            "     highp float scaleSize = minRegionSize / minImageSize;\n" +
            "     highp float newInnerr = innerr * scaleSize;\n" +
            "     highp float newOuterr = outerr * scaleSize;\n" +
            "     \n" +
            "     highp vec2 newCorrd;\n" +
            "     highp vec2 newCenter;\n" +
            "     highp vec2 diff = textureCoordinate - center;\n" +
            "     if(textSize.x < textSize.y){\n" +
            "         diff = vec2(diff.x, diff.y * textSize.y/textSize.x); //scaleup y\n" +
            "     }else{\n" +
            "         diff = vec2(diff.x * textSize.x/textSize.y, diff.y); //scaleup x\n" +
            "     }\n" +
            "     highp float rToCenter = distance(vec2(0.0, 0.0), diff.xy);\n" +
            "     \n" +
            "     //highp float blurStrength = (rToCenter-innerr)/(outerr - innerr);\n" +
            "     //blurStrength = clamp(blurStrength, 0.0, 1.0);\n" +
            "     highp float blurStrength =  smoothstep(0.0, 1.0, (rToCenter-newInnerr)/(newOuterr - newInnerr));\n" +
            "     gl_FragColor = vec4(mix(textureColor.rgb, blurColor.rgb, blurStrength), textureColor.w);\n" +
            " }";

    public ApertureBlurBlendFilter() {
        super(FRAGMENT_SHADER);
    }

    @Override
    public void onInit() {
        super.onInit();
        amountLocation = GLES20.glGetUniformLocation(getProgram(), "amount");
        centerLocation = GLES20.glGetUniformLocation(getProgram(), "center");
        innerrLocation = GLES20.glGetUniformLocation(getProgram(), "innerr");
        outerrLocation = GLES20.glGetUniformLocation(getProgram(), "outerr");
        textSizeLocation = GLES20.glGetUniformLocation(getProgram(), "textSize");
        sizeRegionOfInterestLocation = GLES20.glGetUniformLocation(getProgram(), "sizeRegionOfInterest");
    }

    @Override
    public void onInitialized() {
        super.onInitialized();
        setAmount(mAmount);
        setCenter(mCenter);
        setInnerr(mInnerr);
        setOuterr(mOuterr);
        setSizeRegionOfInterest(mSizeRegionOfInterest);
        setTextSize(mTextSize);
    }


    public void setAmount(float amount) {
        this.mAmount = amount / 100.0f;
        setFloat(amountLocation, mAmount);
    }

    public void setCenter(float[] center) {
        this.mCenter = center;
        setFloatVec2(centerLocation, mCenter);
    }

    public void setInnerr(float innerr) {
        this.mInnerr = innerr;
        setFloat(innerrLocation, mInnerr);
    }

    public void setOuterr(float outerr) {
        this.mOuterr = outerr;
        setFloat(outerrLocation, mOuterr);
    }

    public void setSizeRegionOfInterest(float[] sizeRegionOfInterest) {
        this.mSizeRegionOfInterest = sizeRegionOfInterest;
        setFloatVec2(sizeRegionOfInterestLocation, sizeRegionOfInterest);
    }

    public void setTextSize(float[] textSize) {
        this.mTextSize = textSize;
        setFloatVec2(textSizeLocation, textSize);
    }
}
