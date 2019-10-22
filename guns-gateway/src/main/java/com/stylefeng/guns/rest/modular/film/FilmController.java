package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-22 10:21
 */
@RestController
@RequestMapping("/film")
public class FilmController {

    private static final String IMG_PRE = "http://img.meetingshop.cn";

    @Reference(interfaceClass = FilmServiceApi.class)
    private FilmServiceApi filmServiceApi;

    /**
     * API网关：
     *      1、功能聚合【API聚合】
     *      好处：
     *          1、六个接口，一次请求，同一时刻节省了五次HTTP请求
     *          2、同一个接口对外暴露，降低了前后端分离开发的难度和复杂度
     *      坏处：
     *          1、一次获取数据过多，容易出现问题
     * @return
     */
    @RequestMapping(value = "getIndex", method = RequestMethod.GET)
    public ResponseVO getIndex() {
        FilmIndexVO filmIndexVO = new FilmIndexVO();

        // 获取banner信息
        filmIndexVO.setBanners(filmServiceApi.getBanners());

        // 获取正在热映的电影
        filmIndexVO.setHotFilm(filmServiceApi.getHotFilms(true, 8));

        // 即将上映的电影
        filmIndexVO.setSoonFilms(filmServiceApi.getSoonFilms(true, 8));

        // 票房排行榜
        filmIndexVO.setBoxRanking(filmServiceApi.getBoxRanking());

        // 获取受欢迎的榜单
        filmIndexVO.setExpectRanking(filmServiceApi.getExpectRanking());

        // 获取前一百
        filmIndexVO.setTop100(filmServiceApi.getTop());

        return ResponseVO.success(IMG_PRE, filmIndexVO);
    }

    @GetMapping("getConditionList")
    public ResponseVO getConditionList(@RequestParam(name = "catId", required = false, defaultValue = "99") String catId,
                                       @RequestParam(name = "sourceId", required = false, defaultValue = "99") String sourceId,
                                       @RequestParam(name = "yearId", required = false, defaultValue = "99") String yearId) {

        // 类型集合

        // 片源集合

        // 年代集合


        return null;
    }
}
