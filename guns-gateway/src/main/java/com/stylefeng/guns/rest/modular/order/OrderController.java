package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.alipay.AliPayServiceAPI;
import com.stylefeng.guns.api.alipay.vo.AliPayInfoVO;
import com.stylefeng.guns.api.alipay.vo.AliPayResultVO;
import com.stylefeng.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.mengyun.tcctransaction.api.Compensable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-29 16:02
 */
@Slf4j
@RestController
@RequestMapping(value = "/order/")
public class OrderController {

    private static final String IMG_PRE = "http://img.mettingshop.cn/";

    @Reference(interfaceClass = OrderServiceAPI.class, check = false, group = "order2018")
    private OrderServiceAPI orderServiceAPI;
    @Reference(interfaceClass = OrderServiceAPI.class, check = false, group = "order2017")
    private OrderServiceAPI orderServiceAPI2017;
    @Reference(interfaceClass = AliPayServiceAPI.class, check = false)
    private AliPayServiceAPI aliPayServiceAPI;
    @Reference(interfaceClass = FilmServiceApi.class, check = false, timeout = 50000)
    private FilmServiceApi filmServiceApi;


    @PostMapping("buyTickets")
    public ResponseVO buyTickets(Integer fieldId, String soldSeats, String seatsName) {

        try {
            // 验证售出的票是否为真
            boolean isTrue = orderServiceAPI.isTrueSeats(fieldId + "", soldSeats);

            // 已经销售的座位里，有没有这些座位
            boolean isNotSold = orderServiceAPI.isNotSoldSeats(fieldId + "", soldSeats);

            // 创建订单信息
            if (isTrue && isNotSold) {
                String userId = CurrentUser.getCurrentUserId();
                if (userId == null || userId.trim().length() == 0) {
                    return ResponseVO.serviceFail("用户未登录");
                }
                OrderVO orderVO = orderServiceAPI.saveOrderInfo(fieldId, soldSeats, seatsName, Integer.parseInt(userId));

                if (orderVO == null) {
                    log.error("购票未成功");
                    return ResponseVO.serviceFail("购票业务异常");
                } else {
                    return ResponseVO.success(orderVO);
                }
            } else {
                return ResponseVO.serviceFail("订单中座位有问题");
            }
        } catch (Exception e) {
            log.error("购票业务异常", e);
            return ResponseVO.serviceFail("购票业务异常");
        }
    }

    @PostMapping("getOrderInfo")
    public ResponseVO getOrderInfo(
            @RequestParam(name = "nowPage", required = false, defaultValue = "1") Integer nowPage,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize) {

        // 获取当前登录人的信息
        String userId = CurrentUser.getCurrentUserId();

        // 使用当前登录人获取已经购买的订单
        Page<OrderVO> page = new Page<>(nowPage, pageSize);
        if (userId != null && userId.trim().length() > 0) {
            Page<OrderVO> result = orderServiceAPI.getOrderByUserId(Integer.parseInt(userId), page);

            Page<OrderVO> result2017 = orderServiceAPI2017.getOrderByUserId(Integer.parseInt(userId), page);

            // 合并结果
            int totalPages = result.getPages() + result2017.getPages();
            // 2017和2018的订单总数合并
            List<OrderVO> orderVOList = new ArrayList<>();
            orderVOList.addAll(result.getRecords());
            orderVOList.addAll(result2017.getRecords());

            return ResponseVO.success(nowPage, totalPages, "", orderVOList);
        } else {
            return ResponseVO.serviceFail("用户未登录");
        }
    }

    @PostMapping("getPayInfo")
    public ResponseVO getPayInfo(@RequestParam("orderId") String orderId) {
        String userId = CurrentUser.getCurrentUserId();
        if (userId == null || userId.trim().length() == 0) {
            return ResponseVO.serviceFail("抱歉，用户未登录");
        }

        // 订单二维码返回结果
        AliPayInfoVO aliPayInfoVO = aliPayServiceAPI.getQrCode(orderId);
        return ResponseVO.success(IMG_PRE, aliPayInfoVO);
    }


    @PostMapping("getPayResult")
    public ResponseVO getPayResult(@RequestParam("orderId") String orderId,
                                   @RequestParam(value = "tryNums", required = false, defaultValue = "1") Integer tryNums) {
        String userId = CurrentUser.getCurrentUserId();
        if (userId == null || userId.trim().length() == 0) {
            return ResponseVO.serviceFail("抱歉，用户未登录");
        }

        // 将当前登录人的信息传递给后端
        RpcContext.getContext().setAttachment("userId", userId);

        // 判断是否支付超时
        if (tryNums >= 4) {
            return ResponseVO.serviceFail("订单支付失败，请稍后再试");
        } else {
            AliPayResultVO aliPayResultVO = aliPayServiceAPI.getOrderStatus(orderId);
            if (aliPayResultVO == null || ToolUtil.isEmpty(aliPayResultVO.getOrderId())) {
                AliPayResultVO serviceFailVO = new AliPayResultVO();
                serviceFailVO.setOrderId(orderId);
                serviceFailVO.setOrderStatus(0);
                serviceFailVO.setOrderMsg("未成功支付");
                return ResponseVO.success(serviceFailVO);
            }
            return ResponseVO.success(aliPayResultVO);
        }
    }

    @GetMapping("buy")
    @Compensable
    public ResponseVO buy(String msg) {
        String s = filmServiceApi.goToBuy(msg);
        return ResponseVO.success(s);
    }
}
