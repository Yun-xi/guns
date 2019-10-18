package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.UserInfoModel;
import com.stylefeng.guns.api.user.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserImpl implements UserAPI {

    @Reference(interfaceClass = UserAPI.class, check = false)
    private UserAPI userAPI;

    @Override
    public boolean register(UserModel userModel) {
        return false;
    }

    @Override
    public int login(String username, String password) {
        return 0;
    }

    @Override
    public boolean checkUsername(String username) {
        return false;
    }

    @Override
    public UserInfoModel getUserInfo(int uuid) {
        return null;
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        return null;
    }
}
