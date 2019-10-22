package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-22 16:22
 */
@Data
public class YearVO implements Serializable {

    private String yearId;

    private String yearName;

    private boolean isActive;
}
