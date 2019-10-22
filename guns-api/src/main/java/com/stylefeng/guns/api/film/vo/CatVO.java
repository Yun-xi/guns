package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-22 16:17
 */
@Data
public class CatVO implements Serializable {

    private String catId;

    private String catName;

    private boolean isActive;
}
