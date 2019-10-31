package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.api.cinema.vo.HallInfoVO;
import com.stylefeng.guns.rest.common.persistence.model.MoocFieldT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  * 放映场次表 Mapper 接口
 * </p>
 *
 * @author xieyaqi
 * @since 2019-10-24
 */
public interface MoocFieldTMapper extends BaseMapper<MoocFieldT> {

    HallInfoVO getHallInfo(@Param("fieldId") int fieldId);
}