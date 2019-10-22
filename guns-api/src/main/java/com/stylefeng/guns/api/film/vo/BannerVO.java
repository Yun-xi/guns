package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-22 10:40
 */
@Data
public class BannerVO implements Serializable {

    private String bannerId;

    private String bannerAddress;

    private String bannerUrl;
}
