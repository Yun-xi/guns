package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-22 10:49
 */
@Data
public class FilmVO implements Serializable {

    private int filNum;

    private List<FilmInfo> filmInfoList;
}
