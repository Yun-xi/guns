package com.stylefeng.guns.rest.modular.film.vo;

import lombok.Data;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-22 17:27
 */
@Data
public class FilmRequestVO {

    private Integer showType = 1;

    private Integer sortId = 1;

    private Integer sourceId = 99;

    private Integer catId = 99;

    private Integer yearId = 99;

    private Integer nowPage = 1;

    private Integer pageSize = 18;
}
