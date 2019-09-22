package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.UserAPI;
import org.springframework.stereotype.Component;

@Component
public class UserImpl implements UserAPI {

    private UserAPI userAPI;

    @Override
    public boolean login(String username, String password) {
        return true;
    }
}
