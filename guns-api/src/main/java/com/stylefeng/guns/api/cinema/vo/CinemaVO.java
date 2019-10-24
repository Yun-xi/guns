package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-24 09:49
 */
@Data
public class CinemaVO implements Serializable {

    private String uuid;

    private String cinemaName;

    private String address;

    private String minimumPrice;
}
