package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-23 17:56
 */
@Data
public class CinemaQueryVO implements Serializable {

    private Integer brandId = 99;

    private Integer districtId = 99;

    private Integer hallType = 99;

    private Integer pageSize = 12;

    private Integer nowPage = 1;
}
