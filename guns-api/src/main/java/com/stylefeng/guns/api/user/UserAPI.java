package com.stylefeng.guns.api.user;

public interface UserAPI {

    boolean login(UserModel userModel);

    int login(String username, String password);

    boolean register(UserModel userModel);

    boolean checkUsername(String username);

    UserInfoModel getUserInfo(int uuid);

    UserInfoModel updateUserInfo(UserInfoModel userInfoModel);
}
