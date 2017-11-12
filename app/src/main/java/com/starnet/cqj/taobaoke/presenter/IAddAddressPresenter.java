package com.starnet.cqj.taobaoke.presenter;

import android.app.Application;

import com.starnet.cqj.taobaoke.model.Address;
import com.starnet.cqj.taobaoke.model.city.CityResult;

/**
 * Created by mini on 17/11/12.
 */

public interface IAddAddressPresenter extends BasePresenter {

    void initCity(Application application);

    void addAddress(String token,Address address);

    interface IView extends BasePresenter.IView{

        void onInitCity(CityResult cityResult);

        void onAddAddress();
    }
}
