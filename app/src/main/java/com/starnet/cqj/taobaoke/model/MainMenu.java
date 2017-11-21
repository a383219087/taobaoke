package com.starnet.cqj.taobaoke.model;

import java.io.Serializable;

/**
 * Created by mini on 17/11/4.
 */

public class MainMenu implements Serializable{

    private int id;

    private String name;

    private String pic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
