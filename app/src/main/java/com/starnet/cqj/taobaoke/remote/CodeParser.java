package com.starnet.cqj.taobaoke.remote;

/**
 * Created by Administrator on 2017/11/07.
 */

public class CodeParser {

    public static String parse(String codeStr){
        int code = 0;
        try {
            code = Integer.parseInt(codeStr);
        } catch (NumberFormatException ignored) {
        }

        switch (code){
            case 200:
                return "成功";
            case 401:
                return "未登录无权限进入";
            case 9000:
                return "微信未绑定";
            case 9001:
                return "token过期";
            default:
                return "发生错误";
        }
    }
}
