package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xieyaqi
 * @mail 987159036@qq.com
 * @date 2019-10-22 22:47
 */
@Data
public class FilmDetailVO implements Serializable {

    private String filmName;

    private String filmEnName;

    private String imgAddress;

    private String score;

    private String scoreNum;

    private String totalBox;

    private String info01;

    private String info02;

    private String info03;
}
