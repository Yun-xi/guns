package com.stylefeng.guns.api.alipay;

import com.stylefeng.guns.api.alipay.vo.AliPayInfoVO;
import com.stylefeng.guns.api.alipay.vo.AliPayResultVO;
import org.mengyun.tcctransaction.api.Compensable;

public interface AliPayServiceAPI {

    AliPayInfoVO getQrCode(String orderId);

    AliPayResultVO getOrderStatus(String orderId);

    @Compensable
    String buy(String msg);
}
