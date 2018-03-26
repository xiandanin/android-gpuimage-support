package com.dyhdyh.gpuimage.support.example.model;

import android.support.annotation.StringRes;

import com.dyhdyh.gpuimage.support.example.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFalseColorFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLevelsFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLookupFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLuminosityBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMultiplyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToneCurveFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageTransformFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;
import jp.co.cyberagent.android.gpuimage.adjuster.BrightnessAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.ContrastAdjuster;
import jp.co.cyberagent.android.gpuimage.adjuster.ExposureAdjuster;

/**
 * @author dengyuhan
 *         created 2018/3/22 15:24
 */
public class FilterViewData {

    public static List<FilterModel> all() {
        List<FilterModel> list = new ArrayList<>();
        list.addAll(transform(R.string.label_filter_group_handle_color, handleColor()));
        list.addAll(transform(R.string.label_filter_group_handle_image, handleImage()));
        list.addAll(transform(R.string.label_filter_group_visual_effect, visualEffect()));
        list.addAll(transform(R.string.label_filter_group_blend, blend()));
        //list.addAll(transform(R.string.label_filter_group_custom, custom())
        return list;
    }

    public static List<FilterModel> handleColor() {
        FilterModel[] filterArray = new FilterModel[]{
                //调整颜色 Handle Color
                new FilterModel(R.string.label_filter_brightness, new GPUImageBrightnessFilter(), new BrightnessAdjuster(), 50),
                new FilterModel(R.string.label_filter_exposure, new GPUImageExposureFilter(), new ExposureAdjuster(), 0),
                new FilterModel(R.string.label_filter_contrast, new GPUImageContrastFilter(), new ContrastAdjuster(), 0),
                new FilterModel(R.string.label_filter_saturation, new GPUImageSaturationFilter()),
                new FilterModel(R.string.label_filter_gamma, new GPUImageGammaFilter()),
                new FilterModel(R.string.label_filter_color_invert, new GPUImageColorInvertFilter()),
                new FilterModel(R.string.label_filter_sepia, new GPUImageSepiaFilter()),
                new FilterModel(R.string.label_filter_levels, new GPUImageLevelsFilter()),
                new FilterModel(R.string.label_filter_grayscale, new GPUImageGrayscaleFilter()),
                new FilterModel(R.string.label_filter_rgb, new GPUImageRGBFilter()),
                new FilterModel(R.string.label_filter_tone_curve, new GPUImageToneCurveFilter()),
                new FilterModel(R.string.label_filter_monochrome, new GPUImageMonochromeFilter()),
                new FilterModel(R.string.label_filter_Opacity, new GPUImageOpacityFilter()),
                new FilterModel(R.string.label_filter_highlight_shadow, new GPUImageHighlightShadowFilter()),

                new FilterModel(R.string.label_filter_false_color, new GPUImageFalseColorFilter()),
                new FilterModel(R.string.label_filter_hue, new GPUImageHueFilter()),
                //new FilterModel(R.string.label_filter_chroma_key, new GPUImageChromaKeyFilter()),
                new FilterModel(R.string.label_filter_white_balance, new GPUImageWhiteBalanceFilter()),
                //new FilterModel(R.string.label_filter_average_color, new GPUImageAverageColor()),
                //new FilterModel(R.string.label_filter_solid_color, new GPUImageSolidColorGenerator()),
                new FilterModel(R.string.label_filter_luminosity, new GPUImageLuminosityBlendFilter()),
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
                new FilterModel(R.string.label_filter_transform, new GPUImageTransformFilter()),
        };
        return Arrays.asList(filterArray);
    }

    private static List<FilterModel> visualEffect() {
        FilterModel[] filterArray = new FilterModel[]{
                new FilterModel(R.string.label_filter_sketch, new GPUImageSketchFilter()),
        };
        return Arrays.asList(filterArray);
    }

    private static List<FilterModel> blend() {
        FilterModel[] filterArray = new FilterModel[]{
                new FilterModel(R.string.label_filter_multiply_blend, new GPUImageMultiplyBlendFilter()),
        };
        return Arrays.asList(filterArray);
    }

    private static List<FilterModel> custom() {
        return null;
    }


    public static List<FilterModel> transform(@StringRes int groupName, List<FilterModel> models) {
        List<FilterModel> data = new ArrayList<>();
        data.add(new FilterModel(groupName));
        data.addAll(models);
        return data;
    }


}
