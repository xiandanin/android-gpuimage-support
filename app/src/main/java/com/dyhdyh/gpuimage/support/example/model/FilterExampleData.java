package com.dyhdyh.gpuimage.support.example.model;

import android.support.annotation.StringRes;

import com.dyhdyh.gpuimage.support.example.R;
import com.dyhdyh.gpuimage.support.example.adjuster.ApertureBlurAdjuster;
import com.dyhdyh.gpuimage.support.example.filter.ApertureBlurFilter;
import com.dyhdyh.gpuimage.support.example.filter.GPUImageApertureBlurFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImage3x3ConvolutionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageAddBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageAlphaBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBilateralFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBoxBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCGAColorspaceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageChromaKeyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBurnBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorDodgeBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCrosshatchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDarkenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDifferenceBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDissolveBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDivideBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExclusionBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFalseColorFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGlassSphereFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHalftoneFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHardLightBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHazeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLevelsFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLightenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLookupFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMultiplyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageNonMaximumSuppressionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageNormalBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOverlayBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePosterizeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageScreenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSmoothToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSoftLightBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSourceOverBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSphereRefractionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSubtractBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToneCurveFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageTransformFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;
import jp.co.cyberagent.android.gpuimage.adjuster.BilateralAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.BrightnessAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.ContrastAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.DissolveBlendAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.ExposureAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.GammaAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.GaussianBlurAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.HazeAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.HighlightShadowAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.HueAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.MonochromeAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.OpacityAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.RGBAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.SaturationAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.SepiaAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.WhiteBalanceAdjuster;

/**
 * 示例数据
 * @author dengyuhan
 *         created 2018/3/22 15:24
 */
public class FilterExampleData {

    public static List<FilterModel> all() {
        List<FilterModel> list = new ArrayList<>();
        list.addAll(transform(R.string.label_filter_group_handle_color, handleColor()));
        list.addAll(transform(R.string.label_filter_group_handle_image, handleImage()));
        list.addAll(transform(R.string.label_filter_group_visual_effect, visualEffect()));
        list.addAll(transform(R.string.label_filter_group_blend, blend()));
        list.addAll(transform(R.string.label_filter_group_custom, custom()));
        return list;
    }

    public static List<FilterModel> handleColor() {
        FilterModel[] filterArray = new FilterModel[]{
                //调整颜色 Handle Color
                new FilterModel(R.string.label_filter_brightness, new GPUImageBrightnessFilter(), new BrightnessAdjuster(), 50),
                new FilterModel(R.string.label_filter_exposure, new GPUImageExposureFilter(), new ExposureAdjuster(), 50),
                new FilterModel(R.string.label_filter_contrast, new GPUImageContrastFilter(), new ContrastAdjuster(), 50),
                new FilterModel(R.string.label_filter_saturation, new GPUImageSaturationFilter(), new SaturationAdjuster(), 50),
                new FilterModel(R.string.label_filter_gamma, new GPUImageGammaFilter(), new GammaAdjuster(), 50),
                new FilterModel(R.string.label_filter_color_invert, new GPUImageColorInvertFilter()),
                new FilterModel(R.string.label_filter_sepia, new GPUImageSepiaFilter(), new SepiaAdjuster(), 0),
                new FilterModel(R.string.label_filter_levels, new GPUImageLevelsFilter()),
                new FilterModel(R.string.label_filter_grayscale, new GPUImageGrayscaleFilter()),
                //new FilterModel(R.string.label_filter_histogram, new GPUImageHistogramFilter()),
                //new FilterModel(R.string.label_filter_histogram_generator, new GPUImageHistogramGenerator()),
                new FilterModel(R.string.label_filter_rgb, new GPUImageRGBFilter(), new RGBAdjuster(), 0),
                new FilterModel(R.string.label_filter_tone_curve, new GPUImageToneCurveFilter()),
                new FilterModel(R.string.label_filter_monochrome, new GPUImageMonochromeFilter(), new MonochromeAdjuster(), 0),
                new FilterModel(R.string.label_filter_opacity, new GPUImageOpacityFilter(), new OpacityAdjuster(), 0),
                new FilterModel(R.string.label_filter_highlight_shadow, new GPUImageHighlightShadowFilter(), new HighlightShadowAdjuster(), 0),
                new FilterModel(R.string.label_filter_false_color, new GPUImageFalseColorFilter()),
                new FilterModel(R.string.label_filter_hue, new GPUImageHueFilter(), new HueAdjuster(), 0),
                //new FilterModel(R.string.label_filter_chroma_key, new GPUImageChromaKeyFilter()),
                new FilterModel(R.string.label_filter_white_balance, new GPUImageWhiteBalanceFilter(), new WhiteBalanceAdjuster(), 0),
                //new FilterModel(R.string.label_filter_average_color, new GPUImageAverageColor()),
                //new FilterModel(R.string.label_filter_solid_color, new GPUImageSolidColorGenerator()),
                //new FilterModel(R.string.label_filter_luminosity, new GPUImageLuminosity()),
                //new FilterModel(R.string.label_filter_average_luminance_threshold, new GPUImageAverageLuminanceThresholdFilter()),
                new FilterModel(R.string.label_filter_lookup, new GPUImageLookupFilter()),
                //new FilterModel(R.string.label_filter_amatorka, new GPUImageAmatorkaFilter()),
                //new FilterModel(R.string.label_filter_miss_etikate, new GPUImageMissEtikateFilter()),
                //new FilterModel(R.string.label_filter_soft_elegance, new GPUImageSoftEleganceFilter()),
        };
        return Arrays.asList(filterArray);
    }


    private static List<FilterModel> handleImage() {
        FilterModel[] filterArray = new FilterModel[]{
                //new FilterModel(R.string.label_filter_crosshair_generator, new GPUImageCrosshairGenerator()),
                //new FilterModel(R.string.label_filter_line_generator, new GPUImageLineGenerator()),
                new FilterModel(R.string.label_filter_transform, new GPUImageTransformFilter()),
                //new FilterModel(R.string.label_filter_crop, new GPUImageCropFilter()),
                new FilterModel(R.string.label_filter_sharpen, new GPUImageSharpenFilter()),
                //new FilterModel(R.string.label_filter_unsharp_mask, new GPUImageUnsharpMaskFilter(),
                //new FilterModel(R.string.label_filter_fast_blur, new GPUImageFastBlurFilter()),
                new FilterModel(R.string.label_filter_gaussian_blur, new GPUImageGaussianBlurFilter(), new GaussianBlurAdjuster(), 0),
                //new FilterModel(R.string.label_filter_gaussian_selective_blur, new GPUImageGaussianSelectiveBlurFilter()),
                new FilterModel(R.string.label_filter_box_blur, new GPUImageBoxBlurFilter()),
                //new FilterModel(R.string.label_filter_tilt_shift, new GPUImageTiltShiftFilter()),
                //new FilterModel(R.string.label_filter_median, new GPUImageMedianFilter()),
                new FilterModel(R.string.label_filter_bilateral, new GPUImageBilateralFilter(), new BilateralAdjuster(), 0),
                //new FilterModel(R.string.label_filter_erosion, new GPUImageErosionFilter()),
                //new FilterModel(R.string.label_filter_rgb_erosion, new GPUImageRGBErosionFilter()),
                new FilterModel(R.string.label_filter_dilation, new GPUImageDilationFilter()),
                new FilterModel(R.string.label_filter_rgb_dilation, new GPUImageRGBDilationFilter()),
                //new FilterModel(R.string.label_filter_opening, new GPUImageOpeningFilter()),
                //new FilterModel(R.string.label_filter_rgb_opening, new GPUImageRGBOpeningFilter()),
                //new FilterModel(R.string.label_filter_closing, new GPUImageClosingFilter()),
                //new FilterModel(R.string.label_filter_rgb_closing, new GPUImageRGBClosingFilter()),
                //new FilterModel(R.string.label_filter_lanczos_resampling, new GPUImageLanczosResamplingFilter()),
                new FilterModel(R.string.label_filter_non_maximum_suppression, new GPUImageNonMaximumSuppressionFilter()),
                //new FilterModel(R.string.label_filter_thresholded_non_maximum_suppression, new GPUImageThresholdedNonMaximumSuppressionFilter()),
                //new FilterModel(R.string.label_filter_sobel_edge_detection, new GPUImageSobelEdgeDetectionFilter()),
                //new FilterModel(R.string.label_filter_canny_edge_detection, new GPUImageCannyEdgeDetectionFilter()),
                //new FilterModel(R.string.label_filter_threshold_edge_detection, new GPUImageThresholdEdgeDetectionFilter()),
                //new FilterModel(R.string.label_filter_prewitt_edge_detection, new GPUImagePrewittEdgeDetectionFilter()),
                //new FilterModel(R.string.label_filter_x_y_derivative, new GPUImageXYDerivativeFilter()),
                //new FilterModel(R.string.label_filter_harris_corner_detection, new GPUImageHarrisCornerDetectionFilter()),
                //new FilterModel(R.string.label_filter_noble_corner_detection, new GPUImageNobleCornerDetectionFilter()),
                //new FilterModel(R.string.label_filter_shi_tomasi_feature_detection, new GPUImageShiTomasiFeatureDetectionFilter()),
                //new FilterModel(R.string.label_filter_motion_detector, new GPUImageMotionDetector()),
                //new FilterModel(R.string.label_filter_hough_transform_line_detector, new GPUImageHoughTransformLineDetector()),
                //new FilterModel(R.string.label_filter_parallel_coordinate_line_transform, new GPUImageParallelCoordinateLineTransformFilter()),
                //new FilterModel(R.string.label_filter_local_binary_pattern, new GPUImageLocalBinaryPatternFilter()),
                //new FilterModel(R.string.label_filter_low_pass, new GPUImageLowPassFilter()),
                //new FilterModel(R.string.label_filter_high_pass, new GPUImageHighPassFilter())
        };
        return Arrays.asList(filterArray);
    }

    private static List<FilterModel> visualEffect() {
        FilterModel[] filterArray = new FilterModel[]{
                new FilterModel(R.string.label_filter_sketch, new GPUImageSketchFilter()),
                //new FilterModel(R.string.label_filter_threshold_sketch, new GPUImageThresholdSketchFilter()),
                new FilterModel(R.string.label_filter_toon, new GPUImageToonFilter()),
                new FilterModel(R.string.label_filter_smooth_toon, new GPUImageSmoothToonFilter()),
                new FilterModel(R.string.label_filter_kuwahara, new GPUImageKuwaharaFilter()),
                //new FilterModel(R.string.label_filter_mosaic, new GPUImageMosaicFilter()),
                //new FilterModel(R.string.label_filter_pixellate, new GPUImagePixellateFilter()),
                //new FilterModel(R.string.label_filter_polar_pixellate, new GPUImagePolarPixellateFilter()),
                new FilterModel(R.string.label_filter_crosshatch, new GPUImageCrosshatchFilter()),
                //new FilterModel(R.string.label_filter_color_packing, new GPUImageColorPackingFilter()),
                new FilterModel(R.string.label_filter_vignette, new GPUImageVignetteFilter()),
                new FilterModel(R.string.label_filter_swirl, new GPUImageSwirlFilter()),
                new FilterModel(R.string.label_filter_bulge_distortion, new GPUImageBulgeDistortionFilter()),
                //new FilterModel(R.string.label_filter_pinch_distortion, new GPUImagePinchDistortionFilter()),
                //new FilterModel(R.string.label_filter_stretch_distortion, new GPUImageStretchDistortionFilter()),
                new FilterModel(R.string.label_filter_glass_sphere, new GPUImageGlassSphereFilter()),
                new FilterModel(R.string.label_filter_sphere_refraction, new GPUImageSphereRefractionFilter()),
                new FilterModel(R.string.label_filter_posterize, new GPUImagePosterizeFilter()),
                new FilterModel(R.string.label_filter_cga_colorspace, new GPUImageCGAColorspaceFilter()),
                //new FilterModel(R.string.label_filter_perlin_noise, new GPUImagePerlinNoiseFilter()),
                new FilterModel(R.string.label_filter_3x3_convolution, new GPUImage3x3ConvolutionFilter()),
                new FilterModel(R.string.label_filter_emboss, new GPUImageEmbossFilter()),
                //new FilterModel(R.string.label_filter_polka_dot, new GPUImagePolkaDotFilter()),
                new FilterModel(R.string.label_filter_halftone, new GPUImageHalftoneFilter()),
        };
        return Arrays.asList(filterArray);
    }

    private static List<FilterModel> blend() {
        FilterModel[] filterArray = new FilterModel[]{
                new FilterModel(R.string.label_filter_multiply_blend, new GPUImageMultiplyBlendFilter()),
                new FilterModel(R.string.label_filter_multiply_blend, new GPUImageMultiplyBlendFilter()),
                new FilterModel(R.string.label_filter_normal_blend, new GPUImageNormalBlendFilter()),
                new FilterModel(R.string.label_filter_alpha_blend, new GPUImageAlphaBlendFilter()),
                new FilterModel(R.string.label_filter_dissolve_blend, new GPUImageDissolveBlendFilter(), new DissolveBlendAdjuster(), 0),
                new FilterModel(R.string.label_filter_overlay_blend, new GPUImageOverlayBlendFilter()),
                new FilterModel(R.string.label_filter_darken_blend, new GPUImageDarkenBlendFilter()),
                new FilterModel(R.string.label_filter_lighten_blend, new GPUImageLightenBlendFilter()),
                new FilterModel(R.string.label_filter_source_over_blend, new GPUImageSourceOverBlendFilter()),
                new FilterModel(R.string.label_filter_color_burn_blend, new GPUImageColorBurnBlendFilter()),
                new FilterModel(R.string.label_filter_color_dodge_blend, new GPUImageColorDodgeBlendFilter()),
                new FilterModel(R.string.label_filter_screen_blend, new GPUImageScreenBlendFilter()),
                new FilterModel(R.string.label_filter_exclusion_blend, new GPUImageExclusionBlendFilter()),
                new FilterModel(R.string.label_filter_difference_blend, new GPUImageDifferenceBlendFilter()),
                new FilterModel(R.string.label_filter_subtract_blend, new GPUImageSubtractBlendFilter()),
                new FilterModel(R.string.label_filter_hard_light_blend, new GPUImageHardLightBlendFilter()),
                new FilterModel(R.string.label_filter_soft_light_blend, new GPUImageSoftLightBlendFilter()),
                new FilterModel(R.string.label_filter_chroma_key_blend, new GPUImageChromaKeyBlendFilter()),
                //new FilterModel(R.string.label_filter_mask, new GPUImageMaskFilter()),
                new FilterModel(R.string.label_filter_haze, new GPUImageHazeFilter(), new HazeAdjuster(), 0),
                //new FilterModel(R.string.label_filter_luminance_threshold, new GPUImageLuminanceThresholdFilter()),
                //new FilterModel(R.string.label_filter_adaptive_threshold, new GPUImageAdaptiveThresholdFilter()),
                new FilterModel(R.string.label_filter_add_blend, new GPUImageAddBlendFilter()),
                new FilterModel(R.string.label_filter_divide_blend, new GPUImageDivideBlendFilter())
        };
        return Arrays.asList(filterArray);
    }

    private static List<FilterModel> custom() {

        FilterModel[] filterArray = new FilterModel[]{
                //自定义
                new FilterModel(R.string.label_filter_aperture_blur, new GPUImageApertureBlurFilter(),new ApertureBlurAdjuster(),0),
                new FilterModel(R.string.label_filter_aperture_blur, new ApertureBlurFilter()),
        };
        return Arrays.asList(filterArray);
    }


    public static List<FilterModel> transform(@StringRes int groupName, List<FilterModel> models) {
        List<FilterModel> data = new ArrayList<>();
        data.add(new FilterModel(groupName));
        data.addAll(models);
        return data;
    }


}
