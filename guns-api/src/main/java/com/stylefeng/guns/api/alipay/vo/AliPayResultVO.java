package com.stylefeng.guns.api.alipay.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-11-04 14:45
 */
@Data
public class AliPayResultVO implements Serializable {

    private String orderId;

    private Integer orderStatus;

    private String orderMsg;
}
