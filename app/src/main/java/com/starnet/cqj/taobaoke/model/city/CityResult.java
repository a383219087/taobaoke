package com.starnet.cqj.taobaoke.model.city;

import java.util.ArrayList;

/**
 * Created by mini on 17/11/12.
 */

public class CityResult {

    public ArrayList<ProvinceBean> options1Items;
    public ArrayList<ArrayList<CityBean>> options2Items;
    public ArrayList<ArrayList<ArrayList<AreaBean>>> options3Items;
    public ArrayList<String> Provincestr;
    public ArrayList<ArrayList<String>> Citystr;
    public ArrayList<ArrayList<ArrayList<String>>> Areastr;


    public CityResult() {

        options1Items = new ArrayList<>();
        options2Items = new ArrayList<>();
        options3Items = new ArrayList<>();
        Provincestr = new ArrayList<>();
        Citystr = new ArrayList<>();
        Areastr = new ArrayList<>();
    }
}
