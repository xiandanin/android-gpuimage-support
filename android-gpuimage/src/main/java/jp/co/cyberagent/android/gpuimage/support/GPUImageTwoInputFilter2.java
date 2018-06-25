package jp.co.cyberagent.android.gpuimage.support;

import android.graphics.Bitmap;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.OpenGlUtils;
import jp.co.cyberagent.android.gpuimage.Rotation;
import jp.co.cyberagent.android.gpuimage.util.TextureRotationUtil;

public class GPUImageTwoInputFilter2 extends GPUImageFilter {
    private static final String VERTEX_SHADER = "attribute vec4 position;\n" +
            "attribute vec4 inputTextureCoordinate;\n" +
            "attribute vec4 inputTextureCoordinate2;\n" +
            " \n" +
            "varying vec2 textureCoordinate;\n" +
            "varying vec2 textureCoordinate2;\n" +
            " \n" +
            "void main()\n" +
            "{\n" +
            "    gl_Position = position;\n" +
            "    textureCoordinate = inputTextureCoordinate.xy;\n" +
            "    textureCoordinate2 = inputTextureCoordinate2.xy;\n" +
            "}";

    public int mFilterSecondTextureCoordinateAttribute;
    public int mFilterInputTextureUniform2;
    public int mFilterSourceTexture2 = OpenGlUtils.NO_TEXTURE;
    private ByteBuffer mTexture2CoordinatesBuffer;
    private Bitmap mBitmap;

    private GPUImageFilter mSecondFilter;
    int mFrameBuffers[];
    int mFrameBufferTextures[];
    FloatBuffer mGLTextureBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();


    public GPUImageTwoInputFilter2(String fragmentShader) {
        this(VERTEX_SHADER, fragmentShader);
    }

    public GPUImageTwoInputFilter2(String vertexShader, String fragmentShader) {
        super(vertexShader, fragmentShader);
        setRotation(Rotation.NORMAL, false, false);
    }

    public void setSecondFilter(GPUImageFilter filter) {
        mSecondFilter = filter;
    }

    @Override
    public void onInit() {
        super.onInit();

        mFilterSecondTextureCoordinateAttribute = GLES20.glGetAttribLocation(getProgram(), "inputTextureCoordinate2");
        mFilterInputTextureUniform2 = GLES20.glGetUniformLocation(getProgram(), "inputImageTexture2"); // This does assume a name of "inputImageTexture2" for second input texture in the fragment shader
        GLES20.glEnableVertexAttribArray(mFilterSecondTextureCoordinateAttribute);

        if (mBitmap != null && !mBitmap.isRecycled()) {
            setBitmap(mBitmap);
        }

        mGLTextureBuffer.put(TextureRotationUtil.TEXTURE_CUBE).position(0);

        if (mSecondFilter != null) {
            mSecondFilter.init();

            // this makes more sense....
            setRotation(Rotation.NORMAL, false, true);
        }
    }

    public void onOutputSizeChanged(final int width, final int height) {
        super.onOutputSizeChanged(width, height);

        if (mSecondFilter != null) {
            destroyFrameBuffers();

            mFrameBuffers = new int[1];
            mFrameBufferTextures = new int[1];

            OpenGlUtils.createFrameBuffers(width, height, 1, mFrameBuffers, mFrameBufferTextures);

            mFilterSourceTexture2 = mFrameBufferTextures[0];

            mSecondFilter.onOutputSizeChanged(width, height);
        }
    }

    private void destroyFrameBuffers() {
        OpenGlUtils.destroyFrameBuffers(mFrameBufferTextures, mFrameBuffers);

        mFrameBufferTextures = null;
        mFrameBuffers = null;
    }


    public void setBitmap(final Bitmap bitmap) {
        if (bitmap != null && bitmap.isRecycled()) {
            return;
        }
        mBitmap = bitmap;
        if (mBitmap == null) {
            return;
        }
        runOnDraw(new Runnable() {
            public void run() {
                if (mFilterSourceTexture2 == OpenGlUtils.NO_TEXTURE) {
                    if (bitmap == null || bitmap.isRecycled()) {
                        return;
                    }
                    GLES20.glActiveTexture(GLES20.GL_TEXTURE3);
                    mFilterSourceTexture2 = OpenGlUtils.loadTexture(bitmap, OpenGlUtils.NO_TEXTURE, false);
                }
            }
        });
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void recycleBitmap() {
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }

    public void onDestroy() {
        if (mSecondFilter != null) {

            destroyFrameBuffers();

            mSecondFilter.destroy();

        } else {
            GLES20.glDeleteTextures(1, new int[]{
                    mFilterSourceTexture2
            }, 0);
            mFilterSourceTexture2 = OpenGlUtils.NO_TEXTURE;
        }

        super.onDestroy();
    }


    @Override
    public void onDraw(final int textureId, final FloatBuffer cubeBuffer,
                       final FloatBuffer textureBuffer) {

        int[] currentFBO = new int[1];

        GLES20.glGetIntegerv(GLES20.GL_FRAMEBUFFER_BINDING, currentFBO, 0);

        if (mSecondFilter != null) {
            // activate a frame buffer for each input
            if (mFrameBuffers != null && mFrameBuffers.length > 0)
                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffers[0]);

            // use the unrotated tex coords...
            mSecondFilter.onDraw(textureId, cubeBuffer, mGLTextureBuffer);
        }

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, currentFBO[0]);

        super.onDraw(textureId, cubeBuffer, textureBuffer);
    }

    @Override
    protected void onDrawArraysPre() {
        GLES20.glEnableVertexAttribArray(mFilterSecondTextureCoordinateAttribute);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE3);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mFilterSourceTexture2);
        GLES20.glUniform1i(mFilterInputTextureUniform2, 3);

        mTexture2CoordinatesBuffer.position(0);
        GLES20.glVertexAttribPointer(mFilterSecondTextureCoordinateAttribute, 2, GLES20.GL_FLOAT, false, 0, mTexture2CoordinatesBuffer);
    }

    public void setRotation(final Rotation rotation, final boolean flipHorizontal, final boolean flipVertical) {
        float[] buffer = TextureRotationUtil.getRotation(rotation, flipHorizontal, flipVertical);

        ByteBuffer bBuffer = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        FloatBuffer fBuffer = bBuffer.asFloatBuffer();
        fBuffer.put(buffer);
        fBuffer.flip();

        mTexture2CoordinatesBuffer = bBuffer;
    }
}
