package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-24 09:50
 */
@Data
public class BrandVO implements Serializable {
    private String brandId;

    private String brandName;

    private boolean isActive;
}
