package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.*;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaConditionResponseVO;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaFieldResponseVO;
import com.stylefeng.guns.rest.modular.cinema.vo.CinemaFieldsResponseVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.stylefeng.guns.rest.modular.film.FilmController.IMG_PRE;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-23 17:53
 */
@Slf4j
@RestController
@RequestMapping("/cinema/")
public class CinemaController {

    @Reference(interfaceClass =CinemaServiceAPI.class, check = true)
    private CinemaServiceAPI cinemaServiceAPI;

    @GetMapping("getCinemas")
    public ResponseVO getCinemas(CinemaQueryVO cinemaQueryVO) {

        try {
            // 按照五个条件筛选
            Page<CinemaVO> cinemas = cinemaServiceAPI.getCinemas(cinemaQueryVO);

            // 判断是否有满足条件的影院
            if (cinemas.getRecords() == null || cinemas.getRecords().size() == 0) {
                return ResponseVO.success("没有影院可查");
            } else {
                return ResponseVO.success(cinemas.getCurrent(), cinemas.getPages(), "", cinemas.getRecords());
            }

        } catch (Exception e) {
            // 如果出现异常，应该如何处理
            log.error("获取影院列表异常", e);
            return ResponseVO.serviceFail("查询影院列表失败");
        }
    }

    /**
     * 获取影院的查询条件
     *
     * 1、热点数据 -> 放缓存
     *
     * @param cinemaQueryVO
     * @return
     */
    @GetMapping("getCondition")
    public ResponseVO getCondition(CinemaQueryVO cinemaQueryVO) {

        try {
            // 获取三个集合，然后封装成一个对象返回即可
            List<BrandVO> brands = cinemaServiceAPI.getBrands(cinemaQueryVO.getBrandId());
            List<AreaVO> areas = cinemaServiceAPI.getAreas(cinemaQueryVO.getDistrictId());
            List<HallTypeVO> hallTypes = cinemaServiceAPI.getHallTypes(cinemaQueryVO.getHallType());

            CinemaConditionResponseVO cinemaConditionResponseVO = new CinemaConditionResponseVO();
            cinemaConditionResponseVO.setBrandList(brands);
            cinemaConditionResponseVO.setAreaList(areas);
            cinemaConditionResponseVO.setHalltypeList(hallTypes);

            return ResponseVO.success(cinemaConditionResponseVO);
        } catch (Exception e) {
            log.error("获取条件列表失败", e);
            return ResponseVO.serviceFail("获取条件列表失败");
        }
    }

    @GetMapping("getFields")
    public ResponseVO getFields(Integer cinemaId) {
        try {
            CinemaInfoVO cinemaInfoVO = cinemaServiceAPI.getCinemaInfoById(cinemaId);

            List<FilmInfoVO> filmInfoVOList = cinemaServiceAPI.getFilmInfoByCinemaId(cinemaId);

            CinemaFieldsResponseVO cinemaFieldsResponseVO = new CinemaFieldsResponseVO();
            cinemaFieldsResponseVO.setCinemaInfo(cinemaInfoVO);
            cinemaFieldsResponseVO.setFilmList(filmInfoVOList);

            return ResponseVO.success(IMG_PRE, cinemaFieldsResponseVO);
        } catch (Exception e) {
            log.error("获取播放场次失败", e);
            return ResponseVO.serviceFail("获取播放场次失败");
        }
    }

    @PostMapping("getFieldInfo")
    public ResponseVO getFieldInfo(Integer cinemaId, Integer fieldId) {
        try {

            CinemaInfoVO cinemaInfoVO = cinemaServiceAPI.getCinemaInfoById(cinemaId);
            FilmInfoVO filmInfoVO = cinemaServiceAPI.getFilmInfoByFieldId(fieldId);
            HallInfoVO hallInfoVO = cinemaServiceAPI.getFilmFieldInfo(fieldId);

            // 造几个销售的假数据，后续会对接订单接口
            hallInfoVO.setSoldSeats("1,2,3");

            CinemaFieldResponseVO cinemaFieldResponseVO = new CinemaFieldResponseVO();
            cinemaFieldResponseVO.setCinemaInfo(cinemaInfoVO);
            cinemaFieldResponseVO.setFilmInfo(filmInfoVO);
            cinemaFieldResponseVO.setHallInfo(hallInfoVO);

            return ResponseVO.success(IMG_PRE, cinemaFieldResponseVO);
        } catch (Exception e) {
            log.error("获取选座信息失败", e);
            return ResponseVO.serviceFail("获取选座信息失败");
        }
    }
}
