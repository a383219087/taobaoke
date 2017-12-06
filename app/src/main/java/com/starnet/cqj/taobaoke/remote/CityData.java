package com.starnet.cqj.taobaoke.remote;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.ResultWrapper;
import com.starnet.cqj.taobaoke.model.city.AreaBean;
import com.starnet.cqj.taobaoke.model.city.CityBean;
import com.starnet.cqj.taobaoke.model.city.CityResult;
import com.starnet.cqj.taobaoke.model.city.DBManager;
import com.starnet.cqj.taobaoke.model.city.ProvinceBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by Administrator on 2017/12/02.
 */

public class CityData {

    public static Observable<CityResult> getCity(final Application application) {
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
                    String pro_name = cursor.getString(2);
                    ProvinceBean provinceBean = new ProvinceBean(String.valueOf(pro_id), pro_name);
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
                        String name = cursor1.getString(3);
                        CityBean cityBean = new CityBean(String.valueOf(cityid), province_id, name);
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

    public static Single<CityResult> getRemoteCity() {
        final CityResult cityResult = new CityResult();
        final ArrayList<String> provinceStrList = new ArrayList<>();
        return getProvince()
                .flatMap(new Function<JsonCommon<ResultWrapper<ProvinceBean>>, ObservableSource<ProvinceBean>>() {
                    @Override
                    public ObservableSource<ProvinceBean> apply(JsonCommon<ResultWrapper<ProvinceBean>> resultWrapperJsonCommon) throws Exception {
                        if (resultWrapperJsonCommon.getCode().equals("200")) {
                            List<ProvinceBean> list = resultWrapperJsonCommon.getData().getList();
                            cityResult.options1Items = (ArrayList<ProvinceBean>) list;
                            return Observable.fromIterable(list);
                        }
                        return null;
                    }
                })
                .filter(new Predicate<ProvinceBean>() {
                    @Override
                    public boolean test(ProvinceBean provinceBeen) throws Exception {
                        return provinceBeen != null;
                    }
                })
                .flatMap(new Function<ProvinceBean, ObservableSource<JsonCommon<ResultWrapper<CityBean>>>>() {
                    @Override
                    public ObservableSource<JsonCommon<ResultWrapper<CityBean>>> apply(ProvinceBean provinceBean) throws Exception {
                        provinceStrList.add(provinceBean.getPro_name());
                        return getCity(provinceBean.getPro_id());
                    }
                })
                .map(new Function<JsonCommon<ResultWrapper<CityBean>>, ArrayList<CityBean>>() {
                    @Override
                    public ArrayList<CityBean> apply(JsonCommon<ResultWrapper<CityBean>> resultWrapperJsonCommon) throws Exception {
                        if ("200".equals(resultWrapperJsonCommon.getCode())) {
                            List<CityBean> list = resultWrapperJsonCommon.getData().getList();
                            if (list == null) {
                                return null;
                            }
                            ArrayList<String> cityStr = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                cityStr.add(list.get(i).getName());
                            }
                            cityResult.Citystr.add(cityStr);
                            return (ArrayList<CityBean>) list;
                        }
                        return null;
                    }
                })
                .filter(new Predicate<List<CityBean>>() {
                    @Override
                    public boolean test(List<CityBean> cityBeen) throws Exception {
                        return cityBeen != null;
                    }
                })
                .toList()
                .map(new Function<List<ArrayList<CityBean>>, CityResult>() {
                    @Override
                    public CityResult apply(List<ArrayList<CityBean>> lists) throws Exception {
                        cityResult.Provincestr = provinceStrList;
                        cityResult.options2Items = (ArrayList<ArrayList<CityBean>>) lists;
                        return cityResult;
                    }
                });
    }

    private static Observable<JsonCommon<ResultWrapper<ProvinceBean>>> getProvince() {
        return RemoteDataSourceBase.INSTANCE.getUserService()
                .province();
    }

    private static Observable<JsonCommon<ResultWrapper<CityBean>>> getCity(String parentId) {
        return RemoteDataSourceBase.INSTANCE.getUserService()
                .region(parentId);
    }
}
