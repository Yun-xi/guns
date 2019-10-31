package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.cinema.vo.CinemaInfoVO;
import com.stylefeng.guns.api.cinema.vo.FilmInfoVO;
import lombok.Data;

import java.util.List;


/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-28 16:34
 */
@Data
public class CinemaFieldsResponseVO {

    private CinemaInfoVO cinemaInfo;

    private List<FilmInfoVO> filmList;
}
