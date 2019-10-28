package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.cinema.vo.AreaVO;
import com.stylefeng.guns.api.cinema.vo.BrandVO;
import com.stylefeng.guns.api.cinema.vo.HallTypeVO;
import lombok.Data;

import java.util.List;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-28 16:29
 */
@Data
public class CinemaConditionResponseVO {

    private List<BrandVO> brandList;

    private List<AreaVO> areaList;

    private List<HallTypeVO> halltypeList;
}
