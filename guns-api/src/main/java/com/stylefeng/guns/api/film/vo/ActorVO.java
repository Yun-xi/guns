package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-23 10:53
 */
@Data
public class ActorVO implements Serializable {

    private String imgAddress;

    private String directorName;

    private String roleName;
}
