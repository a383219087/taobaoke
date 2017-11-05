package com.starnet.cqj.taobaoke.remote;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.MainMenu;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * Created by mini on 17/11/4.
 */

public interface IHomePageService {

    @POST("/category")
    Observable<JsonCommon<MainMenu>> getCategory();
}
