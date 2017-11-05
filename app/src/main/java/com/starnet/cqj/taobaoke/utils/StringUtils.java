package com.starnet.cqj.taobaoke.utils;

import android.text.TextUtils;

/**
 * Created by mini on 17/11/4.
 */

public class StringUtils {

    public static boolean isPhone(String mobiles) {
        String telRegex = "[1]\\d{10}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }
}
