package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-22 10:49
 */
@Data
public class FilmInfo implements Serializable {

    private String filmId;

    private int filmType;

    private String imgAddress;

    private String filmName;

    private String filmScore;

    private int expectNum;

    private String showTime;

    private int boxNum;

    private String score;
}
