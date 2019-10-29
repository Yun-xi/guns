package com.stylefeng.guns.rest.modular.order.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.order.OrderServiceAPI;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrderTMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-29 17:33
 */
@Component
@Service(interfaceClass = OrderServiceAPI.class)
public class DefaultOrderServiceImpl implements OrderServiceAPI {

    private MoocOrderTMapper moocOrderTMapper;

    @Override
    public boolean isTrueSeats(String fieldId, String seats) {
        // 根据FieldId找到对应的座位位置图
        String seatPath = moocOrderTMapper.getSeatsByFieldId(fieldId);

        // 读取位置图，判断seats是否为真


        return false;
    }

    @Override
    public boolean isNotSoldSeats(String fieldId, String seats) {
        return false;
    }

    @Override
    public OrderVO saveOrderInfo(Integer fieldId, String soldSeats, String seatsName, Integer userId) {
        return null;
    }

    @Override
    public List<OrderVO> getOrderByUserId(Integer userId) {
        return null;
    }

    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        return null;
    }
}
