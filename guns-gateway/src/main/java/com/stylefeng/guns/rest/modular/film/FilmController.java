package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.rest.modular.film.vo.FilmConditionVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmRequestVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    @RequestMapping(value = "/getIndex", method = RequestMethod.GET)
    public ResponseVO getIndex() {
        FilmIndexVO filmIndexVO = new FilmIndexVO();

        // 获取banner信息
        filmIndexVO.setBanners(filmServiceApi.getBanners());

        // 获取正在热映的电影
        filmIndexVO.setHotFilm(filmServiceApi.getHotFilms(true, 8, 1, 1, 99, 99, 99));

        // 即将上映的电影
        filmIndexVO.setSoonFilms(filmServiceApi.getSoonFilms(true, 8, 1, 1, 99, 99, 99));

        // 票房排行榜
        filmIndexVO.setBoxRanking(filmServiceApi.getBoxRanking());

        // 获取受欢迎的榜单
        filmIndexVO.setExpectRanking(filmServiceApi.getExpectRanking());

        // 获取前一百
        filmIndexVO.setTop100(filmServiceApi.getTop());

        return ResponseVO.success(IMG_PRE, filmIndexVO);
    }

    @GetMapping("/getConditionList")
    public ResponseVO getConditionList(@RequestParam(name = "catId", required = false, defaultValue = "99") String catId,
                                       @RequestParam(name = "sourceId", required = false, defaultValue = "99") String sourceId,
                                       @RequestParam(name = "yearId", required = false, defaultValue = "99") String yearId) {
        FilmConditionVO filmConditionVO = new FilmConditionVO();

        // 标识位
        boolean flag = false;

        // 类型集合
        List<CatVO> cats = filmServiceApi.getCats();
        List<CatVO> catResult = new ArrayList<>();
        CatVO cat = null;
        for (CatVO catVo : cats) {
            // 判断是否存在catId，如果存在，则将对应的实体变成active状态
            if (catVo.getCatId().equals("99")) {
                cat = catVo;
                continue;
            }
            if (catVo.getCatId().equals(catId)) {
                flag = true;
                catVo.setActive(true);
            } else {
                catVo.setActive(false);
            }
            catResult.add(catVo);
        }
        // 如果不存在，则默认将【全部】变成Active状态
        if (!flag) {
            cat.setActive(true);
            catResult.add(cat);
        } else {
            cat.setActive(false);
            catResult.add(cat);
        }


        // 片源集合
        flag = false;
        List<SourceVO> sources = filmServiceApi.getSources();
        List<SourceVO> sourceResult = new ArrayList<>();
        SourceVO source = null;
        for (SourceVO sourceVO : sources) {
            if (sourceVO.getSourceId().equals("99")) {
                source = sourceVO;
                continue;
            }
            if (sourceVO.getSourceId().equals(sourceId)) {
                flag = true;
                sourceVO.setActive(true);
            } else {
                sourceVO.setActive(false);
            }
            sourceResult.add(sourceVO);
        }
        if (!flag) {
            source.setActive(true);
            sourceResult.add(source);
        } else {
            source.setActive(false);
            sourceResult.add(source);
        }

        // 年代集合
        flag = false;
        List<YearVO> years = filmServiceApi.getYears();
        List<YearVO> yearResult = new ArrayList<>();
        YearVO year = null;
        for (YearVO yearVO : years) {
            if (yearVO.getYearId().equals("99")) {
                year = yearVO;
                continue;
            }
            if (yearVO.getYearId().equals(yearId)) {
                flag = true;
                yearVO.setActive(true);
            } else {
                yearVO.setActive(false);
            }
            yearResult.add(yearVO);
        }
        if (!flag) {
            year.setActive(true);
            yearResult.add(year);
        } else {
            year.setActive(false);
            yearResult.add(year);
        }

        filmConditionVO.setCatInfo(catResult);
        filmConditionVO.setSourceInfo(sourceResult);
        filmConditionVO.setYearInfo(yearResult);

        return ResponseVO.success(filmConditionVO);
    }

    @GetMapping("/getFims")
    public ResponseVO getFilms(FilmRequestVO filmRequestVO) {
        FilmVO filmVO = null;

        // 根据showType判断影片查询类型
        switch (filmRequestVO.getShowType()) {
            case 1 :
                filmVO = filmServiceApi.getHotFilms(false, filmRequestVO.getPageSize(), filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(), filmRequestVO.getSourceId(), filmRequestVO.getYearId(),
                        filmRequestVO.getCatId());
                break;
            case 2 :
                filmVO = filmServiceApi.getSoonFilms(false, filmRequestVO.getPageSize(), filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(), filmRequestVO.getSourceId(), filmRequestVO.getYearId(),
                        filmRequestVO.getCatId());
                break;
            case 3 :
                filmVO = filmServiceApi.getClassicFilms( filmRequestVO.getPageSize(), filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(), filmRequestVO.getSourceId(), filmRequestVO.getYearId(),
                        filmRequestVO.getCatId());
                break;
            default:
                filmVO = filmServiceApi.getHotFilms(false, filmRequestVO.getPageSize(), filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(), filmRequestVO.getSourceId(), filmRequestVO.getYearId(),
                        filmRequestVO.getCatId());
                break;
        }

        // 根据sortId排序
        // 添加各种条件查询
        // 判断当前是第几页

        return ResponseVO.success(filmVO.getNowPage(), filmVO.getTotalPage(), IMG_PRE, filmVO.getFilmInfoList());
    }

    @GetMapping("/films/{searchParam}")
    public ResponseVO films(@PathVariable("searchParam") String searchParam,
                            @RequestParam Integer searchType) {

        // 根据searchType，判断查询类型
        FilmDetailVO filmDetail = filmServiceApi.getFilmDetail(searchType, searchParam);

        // 不同的查询类型，传入的条件会略有不同

        // 查询影片的详细信息 -> Dubbo的异步获取
        return null;
    }
}
