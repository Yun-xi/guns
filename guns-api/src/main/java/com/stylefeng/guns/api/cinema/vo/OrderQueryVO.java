package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-30 11:19
 */
@Data
public class OrderQueryVO implements Serializable {

    private String cinemaId;

    private String filmPrice;
}
