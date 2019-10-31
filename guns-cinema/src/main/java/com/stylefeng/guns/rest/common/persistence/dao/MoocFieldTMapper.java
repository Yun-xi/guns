package com.stylefeng.guns.rest.common.persistence.dao;

<<<<<<< HEAD
import com.stylefeng.guns.api.cinema.vo.HallInfoVO;
import com.stylefeng.guns.rest.common.persistence.model.MoocFieldT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
=======
import com.stylefeng.guns.api.cinema.vo.FilmInfoVO;
import com.stylefeng.guns.api.cinema.vo.HallInfoVO;
import com.stylefeng.guns.api.film.vo.FilmInfo;
import com.stylefeng.guns.rest.common.persistence.model.MoocFieldT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
>>>>>>> eda085a0f32aafe6ebb574dc73d8117489fbd52c

/**
 * <p>
  * 放映场次表 Mapper 接口
 * </p>
 *
 * @author xieyaqi
 * @since 2019-10-24
 */
public interface MoocFieldTMapper extends BaseMapper<MoocFieldT> {

    List<FilmInfoVO> getFilmInfos(@Param("cinemaId") int cinemaId);

    HallInfoVO getHallInfo(@Param("field") int field);

    FilmInfoVO getFilmInfoById(@Param("fieldId") int fieldId);
}