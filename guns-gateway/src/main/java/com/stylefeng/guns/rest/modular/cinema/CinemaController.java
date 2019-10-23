package com.stylefeng.guns.rest.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.cinema.CinemaServiceAPI;
import com.stylefeng.guns.api.cinema.vo.CinemaQueryVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-10-23 17:53
 */
@RestController
@RequestMapping("/cinema/")
public class CinemaController {

    @Reference(interfaceClass =CinemaServiceAPI.class, check = true)
    private CinemaServiceAPI cinemaServiceAPI;

    @GetMapping("getCinemas")
    public ResponseVO getCinemas(CinemaQueryVO cinemaQueryVO) {

        return null;
    }

    @GetMapping("getCondition")
    public ResponseVO getCondition(CinemaQueryVO cinemaQueryVO) {
        return null;
    }

    @GetMapping("getFields")
    public ResponseVO getFields(Integer cinemaId) {
        return null;
    }

    @PostMapping("getFieldInfo")
    public ResponseVO getFieldInfo(Integer cinemaId, Integer fieldId) {
        return null;
    }
}
