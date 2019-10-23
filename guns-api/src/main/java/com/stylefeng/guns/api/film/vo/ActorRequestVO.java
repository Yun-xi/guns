package com.stylefeng.guns.api.film.vo;

import com.stylefeng.guns.api.film.vo.ActorVO;
import lombok.Data;

import java.util.List;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-23 14:16
 */
@Data
public class ActorRequestVO {

    private ActorVO director;

    private List<ActorVO> actors;
}
