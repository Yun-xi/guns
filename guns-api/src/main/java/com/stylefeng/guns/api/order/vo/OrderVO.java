package com.stylefeng.guns.api.order.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-29 17:24
 */
@Data
public class OrderVO implements Serializable {

    private String orderId;

    private String filmName;

    private String fieldName;

    private String cinemaName;

    private String seatsName;

    private String orderPrice;

    private String orderTimestamp;

    private String orderStatus;
}
