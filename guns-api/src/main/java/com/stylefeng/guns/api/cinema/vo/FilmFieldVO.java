package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-24 09:56
 */
@Data
public class FilmFieldVO implements Serializable {
       private String fieldId;
       private String beginTime;
       private String endTime;
       private String language;
       private String hallName;
       private String price;
}
