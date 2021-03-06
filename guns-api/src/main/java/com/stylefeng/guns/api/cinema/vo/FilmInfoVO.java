package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-24 09:55
 */
@Data
public class FilmInfoVO implements Serializable {
   private String filmId;
   private String filmName;
   private String filmLength;
   private String filmType;
   private String filmCats;
   private String actors;
   private String imgAddress;
   private List<FilmFieldVO> filmFields;
}
