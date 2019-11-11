package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.alipay.AliPayServiceAPI;
import com.stylefeng.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
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
    @Autowired
    private MoocCatDictTMapper moocCatDictTMapper;
    @Autowired
    private MoocYearDictTMapper moocYearDictTMapper;
    @Autowired
    private MoocSourceDictTMapper moocSourceDictTMapper;
    @Autowired
    private MoocFilmInfoTMapper moocFilmInfoTMapper;
    @Autowired
    private MoocActorTMapper moocActorTMapper;

//    @Reference(interfaceClass = AliPayServiceAPI.class, check = false)
    @Autowired
    private AliPayServiceAPI aliPayServiceAPI;

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
    public FilmVO getHotFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
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
            Page<MoocFilmT> page = null;
            // 根据sortId的不同，来组织不同的Page对象
            // 1: 热门  2: 时间  3: 评价
            switch (sortId) {
                case 1:
                    page = new Page<>(nowPage, nums, "film_box_office");
                    break;
                case 2:
                    page = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage, nums, "film_score");
                    break;
                default:
                    page = new Page<>(nowPage, nums, "film_box_office");
                    break;
            }

            // 如果sourceId, yearId, catId 不为99， 则表示要按照对应的编号进行查询
            if (sourceId != 99) {
                entityWrapper.eq("film_source", sourceId);
            }
            if (yearId != 99) {
                entityWrapper.eq("film_date", yearId);
            }
            if (catId != 99) {
                String catStr = "%#" + catId + "#%";
                entityWrapper.like("film_cats", catStr);
            }

            List<MoocFilmT> moocFilmTList = moocFilmTMapper.selectPage(page, entityWrapper);
            // 组织filmInfos
            filmInfos = getFilmInfos(moocFilmTList);
            filmVO.setFilNum(moocFilmTList.size());

            // 需要总页数 (totalCount / num) + 1
            int totalCounts = moocFilmTMapper.selectCount(entityWrapper);
            int totalPages = (totalCounts / nums) + 1;

            filmVO.setFilmInfoList(filmInfos);
            filmVO.setTotalPage(totalPages);
            filmVO.setNowPage(nowPage);
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
    public FilmVO getSoonFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");
        // 1 判断是否是首页需要的内容
        if (isLimit) {
            // 1.1 如果是，则限制条数、限制内容为即将上映影片
            Page<MoocFilmT> page = new Page(1, nums);
            List<MoocFilmT> moocFilmTList = moocFilmTMapper.selectPage(page, entityWrapper);

            // 组织filmInfos
            filmInfos = getFilmInfos(moocFilmTList);
            filmVO.setFilNum(moocFilmTList.size());
            filmVO.setFilmInfoList(filmInfos);
        } else {
            // 1.2 如果不是，则是列表页，同样需要限制内容为即将上映影片
            Page<MoocFilmT> page = null;
            switch (sortId) {
                case 1:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
                case 2:
                    page = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
                default:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
            }

            // 如果sourceId, yearId, catId 不为99， 则表示要按照对应的编号进行查询
            if (sourceId != 99) {
                entityWrapper.eq("film_source", sourceId);
            }
            if (yearId != 99) {
                entityWrapper.eq("film_date", yearId);
            }
            if (catId != 99) {
                String catStr = "%#" + catId + "#%";
                entityWrapper.like("film_cats", catStr);
            }

            List<MoocFilmT> moocFilmTList = moocFilmTMapper.selectPage(page, entityWrapper);
            // 组织filmInfos
            filmInfos = getFilmInfos(moocFilmTList);
            filmVO.setFilNum(moocFilmTList.size());

            // 需要总页数 (totalCount / num) + 1
            int totalCounts = moocFilmTMapper.selectCount(entityWrapper);
            int totalPages = (totalCounts / nums) + 1;

            filmVO.setFilmInfoList(filmInfos);
            filmVO.setTotalPage(totalPages);
            filmVO.setNowPage(nowPage);
        }
        filmVO.setFilmInfoList(filmInfos);
        return filmVO;
    }

    @Override
    public FilmVO getClassicFilms(int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "3");

        Page<MoocFilmT> page = null;
        switch (sortId) {
            case 1:
                page = new Page<>(nowPage, nums, "film_box_office");
                break;
            case 2:
                page = new Page<>(nowPage, nums, "film_time");
                break;
            case 3:
                page = new Page<>(nowPage, nums, "film_score");
                break;
            default:
                page = new Page<>(nowPage, nums, "film_box_office");
                break;
        }

        // 如果sourceId, yearId, catId 不为99， 则表示要按照对应的编号进行查询
        if (sourceId != 99) {
            entityWrapper.eq("film_source", sourceId);
        }
        if (yearId != 99) {
            entityWrapper.eq("film_date", yearId);
        }
        if (catId != 99) {
            String catStr = "%#" + catId + "#%";
            entityWrapper.like("film_cats", catStr);
        }

        List<MoocFilmT> moocFilmTList = moocFilmTMapper.selectPage(page, entityWrapper);
        // 组织filmInfos
        filmInfos = getFilmInfos(moocFilmTList);
        filmVO.setFilNum(moocFilmTList.size());

        // 需要总页数 (totalCount / num) + 1
        int totalCounts = moocFilmTMapper.selectCount(entityWrapper);
        int totalPages = (totalCounts / nums) + 1;

        filmVO.setFilmInfoList(filmInfos);
        filmVO.setTotalPage(totalPages);
        filmVO.setNowPage(nowPage);

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

    @Override
    public List<CatVO> getCats() {
        List<CatVO> cats = new ArrayList<>();

         // 查询实体对象 - MoocCatDictT
        List<MoocCatDictT> moocCats = moocCatDictTMapper.selectList(null);

        // 将实体对象转换为业务对象 - CatVO
        for (MoocCatDictT moocCat : moocCats) {
            CatVO catVO = new CatVO();
            catVO.setCatId(moocCat.getUuid() + "");
            catVO.setCatName(moocCat.getShowName());

            cats.add(catVO);
        }

        return cats;
    }

    @Override
    public List<SourceVO> getSources() {
        List<SourceVO> sources = new ArrayList<>();

        List<MoocSourceDictT> moocSources = moocSourceDictTMapper.selectList(null);

        for (MoocSourceDictT moocSource : moocSources) {
            SourceVO sourceVO = new SourceVO();

            sourceVO.setSourceId(moocSource.getUuid() + "");
            sourceVO.setSourceName(moocSource.getShowName());

            sources.add(sourceVO);
        }
        return sources;
    }

    @Override
    public List<YearVO> getYears() {
        List<YearVO> years = new ArrayList<>();

        List<MoocYearDictT> moocYears = moocYearDictTMapper.selectList(null);

        for (MoocYearDictT moocYear : moocYears) {
            YearVO yearVO = new YearVO();
            yearVO.setYearId(moocYear.getUuid() + "");
            yearVO.setYearName(moocYear.getShowName());

            years.add(yearVO);
        }

        return years;
    }

    @Override
    public FilmDetailVO getFilmDetail(int searchType, String searchParam) {
        FilmDetailVO filmDetailVO;

        // searchType 1 - 按名称   2 - 按ID
        if (searchType == 1) {
            filmDetailVO = moocFilmTMapper.getFilmDetailByName(searchParam);
        } else {
            filmDetailVO = moocFilmTMapper.getFilmDetailById(searchParam);
        }

        return filmDetailVO;
    }

    private MoocFilmInfoT getFilmInfo(String filmId) {
        MoocFilmInfoT moocFilmInfoT = new MoocFilmInfoT();
        moocFilmInfoT.setFilmId(filmId);

        moocFilmInfoT = moocFilmInfoTMapper.selectOne(moocFilmInfoT);

        return moocFilmInfoT;
    }

    @Override
    public FilmDescVO getFilmDesc(String filmId) {
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);

        FilmDescVO filmDescVO = new FilmDescVO();
        filmDescVO.setBiography(moocFilmInfoT.getBiography());
        filmDescVO.setFilmId(filmId);
        return filmDescVO;
    }

    @Override
    public ImgVO getImgs(String filmId) {
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);

        // 图片地址是五个以逗号为分隔的链接URL
        String filmImgStr = moocFilmInfoT.getFilmImgs();

        String[] filmImgs = filmImgStr.split(",");

        ImgVO imgVO = new ImgVO();
        imgVO.setMainImg(filmImgs[0]);
        imgVO.setImg01(filmImgs[1]);
        imgVO.setImg02(filmImgs[2]);
        imgVO.setImg03(filmImgs[3]);
        imgVO.setImg04(filmImgs[4]);

        return imgVO;
    }

    @Override
    public ActorVO getDectInfo(String filmId) {
        MoocFilmInfoT moocFilmInfoT = getFilmInfo(filmId);

        // 获取导演编号
        Integer directorId = moocFilmInfoT.getDirectorId();

        MoocActorT moocActorT = moocActorTMapper.selectById(directorId);

        ActorVO actorVO = new ActorVO();
        actorVO.setImgAddress(moocActorT.getActorImg());
        actorVO.setDirectorName(moocActorT.getActorName());

        return actorVO;
    }

    @Override
    public List<ActorVO> getActors(String filmId) {

        List<ActorVO> actors = moocActorTMapper.getActors(filmId);
        return actors;
    }

    @Override
    @Compensable(confirmMethod = "confirmGoToBuy", cancelMethod = "cancelGoToBuy")
    public String goToBuy(String msg) {
        System.out.println("this is consumer messgae : " + msg);

        String result = aliPayServiceAPI.buy(msg);
        System.out.println(result);

        return result;
    }

//    @Override
    public void confirmGoToBuy(String msg) {
        System.out.println("this is confirm messgae : " + msg);
    }

//    @Override
    public void cancelGoToBuy(String msg) {
        System.out.println("this is cancel messgae : " + msg);
    }
}
