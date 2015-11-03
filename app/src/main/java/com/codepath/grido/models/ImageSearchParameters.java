package com.codepath.grido.models;

import java.io.Serializable;

public class ImageSearchParameters implements Serializable {

    public ImageSizeParameter imageSize;
    public ImageColorParameter imageColor;
    public ImageTypeParameter imageType;
    public String domain;

    public ImageSearchParameters() {
        this.imageSize = ImageSizeParameter.ImageSizeAny;
        this.imageColor = ImageColorParameter.ImageColorAny;
        this.imageType = ImageTypeParameter.ImageTypeAny;
        this.domain = null;
    }

    public ImageSearchParameters(ImageSizeParameter imageSize, ImageColorParameter imageColor, ImageTypeParameter imageType, String domain) {
        this.imageSize = imageSize;
        this.imageColor = imageColor;
        this.imageType = imageType;
        this.domain = domain;
    }
 }
