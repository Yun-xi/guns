package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.api.film.vo.BannerVO;
import com.stylefeng.guns.api.film.vo.FilmInfo;
import com.stylefeng.guns.api.film.vo.FilmVO;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.MoocBannerTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MoocFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocBannerT;
import com.stylefeng.guns.rest.common.persistence.model.MoocFilmT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-22 14:15
 */
@Component
@Service(interfaceClass = FilmServiceApi.class)
public class DefaultFilmServiceImpl implements FilmServiceApi {

    @Autowired
    private MoocBannerTMapper moocBannerTMapper;
    @Autowired
    private MoocFilmTMapper moocFilmTMapper;


    @Override
    public List<BannerVO> getBanners() {
        List<BannerVO> result = new ArrayList<>();
        List<MoocBannerT> moocBanners = moocBannerTMapper.selectList(null);
        for (MoocBannerT moocBanner : moocBanners) {
            BannerVO bannerVO = new BannerVO();

            bannerVO.setBannerId("" + moocBanner.getUuid());
            bannerVO.setBannerUrl(moocBanner.getBannerUrl());
            bannerVO.setBannerAddress(moocBanner.getBannerAddress());
            result.add(bannerVO);
        }
        return result;
    }

    @Override
    public FilmVO getHotFilms(boolean isLimit, int nums) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");
        // 1 判断是否是首页需要的内容
        if (isLimit) {
            // 1.1 如果是，则限制条数、限制内容为热映影片
            Page<MoocFilmT> page = new Page(1, nums);
            List<MoocFilmT> moocFilmTList = moocFilmTMapper.selectPage(page, entityWrapper);

            // 组织filmInfos
            filmInfos = getFilmInfos(moocFilmTList);
            filmVO.setFilNum(moocFilmTList.size());
            filmVO.setFilmInfoList(filmInfos);
        } else {
            // 1.2 如果不是，则是列表页，同样需要限制内容为热映影片

        }
        filmVO.setFilmInfoList(filmInfos);
        return filmVO;
    }

    private List<FilmInfo> getFilmInfos(List<MoocFilmT> moocFilms) {
        List<FilmInfo> filmInfos = new ArrayList<>();
        for (MoocFilmT moocFilmT : moocFilms) {
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setScore(moocFilmT.getFilmScore());
            filmInfo.setImgAddress(moocFilmT.getImgAddress());
            filmInfo.setFilmType(moocFilmT.getFilmType());
            filmInfo.setFilmScore(moocFilmT.getFilmScore());
            filmInfo.setFilmName(moocFilmT.getFilmName());
            filmInfo.setFilmId("" +moocFilmT.getUuid());
            filmInfo.setExpectNum(moocFilmT.getFilmPreSaleNum());
            filmInfo.setBoxNum(moocFilmT.getFilmBoxOffice());
            filmInfo.setShowTime(DateUtil.getDay(moocFilmT.getFilmTime()));

            filmInfos.add(filmInfo);
        }

        return filmInfos;
    }

    @Override
    public FilmVO getSoonFilms(boolean isLimit, int nums) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");
        // 1 判断是否是首页需要的内容
        if (isLimit) {
            // 1.1 如果是，则限制条数、限制内容为热映影片
            Page<MoocFilmT> page = new Page(1, nums);
            List<MoocFilmT> moocFilmTList = moocFilmTMapper.selectPage(page, entityWrapper);

            // 组织filmInfos
            filmInfos = getFilmInfos(moocFilmTList);
            filmVO.setFilNum(moocFilmTList.size());
            filmVO.setFilmInfoList(filmInfos);
        } else {
            // 1.2 如果不是，则是列表页，同样需要限制内容为热映影片

        }
        filmVO.setFilmInfoList(filmInfos);
        return filmVO;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        // 条件 -> 正在上映的，票房前10名
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        Page<MoocFilmT> page = new Page<>(1, 10, "film_box_office");

        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);

        List<FilmInfo> filmInfos = getFilmInfos(moocFilmTS);
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        // 条件 -> 即将上映的，预售前10名
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");

        Page<MoocFilmT> page = new Page<>(1, 10, "film_preSaleNum");

        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);

        List<FilmInfo> filmInfos = getFilmInfos(moocFilmTS);
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getTop() {
        // 条件 -> 正在上映的，评分前10名
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        Page<MoocFilmT> page = new Page<>(1, 10, "film_score");

        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);

        List<FilmInfo> filmInfos = getFilmInfos(moocFilmTS);
        return filmInfos;
    }
}
