package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-24 09:53
 */
@Data
public class CinemaInfoVO implements Serializable {

       private String cinemaId;
       private String imgUrl;
       private String cinemaName;
       private String cinemaAdress;
       private String cinemaPhone;
}
