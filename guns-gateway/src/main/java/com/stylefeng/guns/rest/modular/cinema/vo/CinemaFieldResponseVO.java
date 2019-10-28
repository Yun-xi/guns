package com.stylefeng.guns.rest.modular.cinema.vo;

import com.stylefeng.guns.api.cinema.vo.CinemaInfoVO;
import com.stylefeng.guns.api.cinema.vo.FilmInfoVO;
import com.stylefeng.guns.api.cinema.vo.HallInfoVO;
import lombok.Data;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-28 16:41
 */
@Data
public class CinemaFieldResponseVO {

    private CinemaInfoVO cinemaInfo;

    private FilmInfoVO filmInfo;

    private HallInfoVO hallInfo;

}
