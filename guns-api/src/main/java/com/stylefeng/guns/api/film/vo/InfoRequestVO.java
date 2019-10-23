package com.stylefeng.guns.api.film.vo;

import lombok.Data;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-23 14:17
 */
@Data
public class InfoRequestVO {

    private String biography;

    private ActorRequestVO actors;

    private ImgVO imgVO;

    private String filmId;
}
