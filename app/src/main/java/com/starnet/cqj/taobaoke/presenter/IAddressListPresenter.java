package com.starnet.cqj.taobaoke.presenter;

import com.starnet.cqj.taobaoke.model.Address;

import java.util.List;

/**
 * Created by mini on 17/11/12.
 */

public interface IAddressListPresenter extends BasePresenter {

    void getAddress(String token);

    void deleteAddress(String token, Address address);

    void editDefault(String token, Address address);


    interface IView extends BasePresenter.IView {

        void onGetAddressDone(List<Address> addressList);

        void onDelete(Address address);

        void onEditDefault();

    }
}
