package com.starnet.cqj.taobaoke.presenter;

import com.starnet.cqj.taobaoke.model.Address;


public interface IntegralProductDetailPresenter extends BasePresenter {

    void getAddress(String header);

    void exchange(String header,String score,String address,String phone,String integralId,String name);

    interface IView extends BasePresenter.IView{

        void onGetAddress(Address address);

        void onExchange();
    }

}
