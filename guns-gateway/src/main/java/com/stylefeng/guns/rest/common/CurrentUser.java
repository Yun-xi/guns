package com.stylefeng.guns.rest.common;

import com.stylefeng.guns.api.user.UserInfoModel;

/**
 * @author xieyaqi
 * @mail 987159036@qq.com
 * @date 2019-10-14 22:39
 */
public class CurrentUser {

    /*
    // 线程绑定的存储空间
    private static final ThreadLocal<UserInfoModel> threadLocal = new ThreadLocal<>();

    // 将用户信息放入存储空间
    public static void saveUserId(UserInfoModel userInfoModel) {
        threadLocal.set(userInfoModel);
    }

    // 将用户信息取出
    public static UserInfoModel getCurrentUser() {
        return threadLocal.get();
    }
    */

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void saveUserId(String userId) {
        threadLocal.set(userId);
    }

    public static String getCurrentUserId() {
        return threadLocal.get();
    }

}
