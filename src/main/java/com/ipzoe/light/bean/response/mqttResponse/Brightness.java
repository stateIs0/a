package com.ipzoe.light.bean.response.mqttResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by cxs on 2017/4/7.
 */
@ApiModel(value = "brightness", description = "亮度")
public class Brightness {

    @ApiModelProperty("三原色红色R 0-255")
    private Integer red;

    @ApiModelProperty("三原色绿色G 0-255")
    private Integer green;

    @ApiModelProperty("三原色蓝色B 0-255")
    private Integer blue;

    @ApiModelProperty("色温")
    private Integer colorTem;

    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getGreen() {
        return green;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public Integer getBlue() {
        return blue;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }

    public Integer getColorTem() {
        return colorTem;
    }

    public void setColorTem(Integer colorTem) {
        this.colorTem = colorTem;
    }
}
