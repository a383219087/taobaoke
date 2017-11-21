package com.starnet.cqj.taobaoke.presenter;

import com.starnet.cqj.taobaoke.model.Message;

import java.util.List;

/**
 * Created by mini on 17/11/20.
 */

public interface IMessagePresenter extends BasePresenter {

    void getMessageList(String header);

    void deleteMessage(String header,String id);

    void allRead(String header);

    interface IView extends BasePresenter.IView{
        void setMessageList(List<Message> messageList);

        void onDelete();

        void onAllRead();

    }
}
