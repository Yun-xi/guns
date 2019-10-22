package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-22 16:21
 */
@Data
public class SourceVO implements Serializable {

    private String sourceId;

    private String sourceName;

    private boolean isActive;
}
