package com.starnet.cqj.taobaoke.presenter;

import com.starnet.cqj.taobaoke.model.Message;

public interface IMessageDetailPresenter extends BasePresenter {

    void getMessage(String header, String id);

    interface IView extends BasePresenter.IView{
        void setMessage(Message message);

    }
}
