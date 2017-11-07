package com.starnet.cqj.taobaoke.remote.i;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.MainMenu;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * Created by mini on 17/11/4.
 */

public interface IHomePageService {

    @POST("/category")
    Observable<JsonCommon<List<MainMenu>>> getCategory();
}
