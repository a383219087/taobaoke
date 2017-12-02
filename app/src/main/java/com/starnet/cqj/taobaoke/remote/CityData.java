package com.starnet.cqj.taobaoke.remote;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.starnet.cqj.taobaoke.model.city.AreaBean;
import com.starnet.cqj.taobaoke.model.city.CityBean;
import com.starnet.cqj.taobaoke.model.city.CityResult;
import com.starnet.cqj.taobaoke.model.city.DBManager;
import com.starnet.cqj.taobaoke.model.city.ProvinceBean;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Administrator on 2017/12/02.
 */

public class CityData {

    public static Observable<CityResult> getCity(final Application application){
        return Observable.create(new ObservableOnSubscribe<CityResult>() {
            @Override
            public void subscribe(ObservableEmitter<CityResult> e) throws Exception {
                CityResult cityResult = new CityResult();
                SQLiteDatabase db = DBManager.getdb(application);
                //省
                Cursor cursor = db.query("province", null, null, null, null, null,
                        null);
                while (cursor.moveToNext()) {
                    int pro_id = cursor.getInt(0);
                    String pro_code = cursor.getString(1);
                    String pro_name = cursor.getString(2);
                    String pro_name2 = cursor.getString(3);
                    ProvinceBean provinceBean = new ProvinceBean(pro_id, pro_code, pro_name, pro_name2);
                    cityResult.options1Items.add(provinceBean);//添加一级目录
                    cityResult.Provincestr.add(cursor.getString(2));
                    //查询二级目录，市区
                    Cursor cursor1 = db.query("city", null, "province_id=?", new String[]{pro_id + ""}, null, null,
                            null);
                    ArrayList<CityBean> cityBeanList = new ArrayList<>();
                    ArrayList<String> cityStr = new ArrayList<>();
                    //地区集合的集合（注意这里要的是当前省份下面，当前所有城市的地区集合我去）
                    ArrayList<ArrayList<AreaBean>> options3Items_03 = new ArrayList<>();
                    ArrayList<ArrayList<String>> options3Items_str = new ArrayList<>();
                    while (cursor1.moveToNext()) {
                        int cityid = cursor1.getInt(0);
                        int province_id = cursor1.getInt(1);
                        String code = cursor1.getString(2);
                        String name = cursor1.getString(3);
                        String provincecode = cursor1.getString(4);
                        CityBean cityBean = new CityBean(cityid, province_id, code, name, provincecode);
                        //添加二级目录
                        cityBeanList.add(cityBean);
                        cityStr.add(cursor1.getString(3));
                        //查询三级目录
                        Cursor cursor2 = db.query("area", null, "city_id=?", new String[]{cityid + ""}, null, null,
                                null);
                        ArrayList<AreaBean> areaBeanList = new ArrayList<>();
                        ArrayList<String> areaBeanstr = new ArrayList<>();
                        while (cursor2.moveToNext()) {
                            int areaid = cursor2.getInt(0);
                            int city_id = cursor2.getInt(1);
//                    String code0=cursor2.getString(2);
                            String areaname = cursor2.getString(3);
                            String citycode = cursor2.getString(4);
                            AreaBean areaBean = new AreaBean(areaid, city_id, areaname, citycode);
                            areaBeanList.add(areaBean);
                            areaBeanstr.add(cursor2.getString(3));
                        }
                        options3Items_str.add(areaBeanstr);//本次查询的存储内容
                        options3Items_03.add(areaBeanList);
                        cursor2.close();
                    }
                    cityResult.options2Items.add(cityBeanList);//增加二级目录数据
                    cityResult.Citystr.add(cityStr);
                    cityResult.options3Items.add(options3Items_03);//添加三级目录
                    cityResult.Areastr.add(options3Items_str);
                    cursor1.close();
                }
                cursor.close();
                e.onNext(cityResult);
                e.onComplete();
            }
        });
    }
}
