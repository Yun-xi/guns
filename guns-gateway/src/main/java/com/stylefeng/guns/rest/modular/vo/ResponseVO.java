package com.stylefeng.guns.rest.modular.vo;

import lombok.Data;

/**
 * @author xieyaqi
 * @mail 987159036@qq.com
 * @date 2019-10-14 22:32
 */
@Data
public class ResponseVO<M> {

    // 返回状态(0-成功， 1-失败， 999-系统异常)
    private int status;

    // 返回信息
    private String msg;

    // 返回数据实体
    private M data;

    // 图片前缀
    private String imgPre;

    // 分页
    private int nowPage;
    private int totalPage;

    private ResponseVO(){}

    public static<M> ResponseVO success(int nowPage, int totalPage, String imgPre, M m) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(m);
        responseVO.setImgPre(imgPre);
        responseVO.setNowPage(nowPage);
        responseVO.setTotalPage(totalPage);

        return responseVO;
    }

    public static<M> ResponseVO success(String imgPre, M m) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(m);
        responseVO.setImgPre(imgPre);

        return responseVO;
    }

    public static<M> ResponseVO success(M m) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setData(m);

        return responseVO;
    }

    public static<M> ResponseVO success(String msg) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setMsg(msg);

        return responseVO;
    }

    public static<M> ResponseVO serviceFail(String message) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(0);
        responseVO.setMsg(message);

        return responseVO;
    }

    public static<M> ResponseVO appFail(String message) {
        ResponseVO responseVO = new ResponseVO();
        responseVO.setStatus(999);
        responseVO.setMsg(message);

        return responseVO;
    }

}
