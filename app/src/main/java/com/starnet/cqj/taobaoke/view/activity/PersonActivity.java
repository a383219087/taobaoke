package com.starnet.cqj.taobaoke.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.starnet.cqj.taobaoke.R;
import com.starnet.cqj.taobaoke.model.JsonCommon;
import com.starnet.cqj.taobaoke.model.User;
import com.starnet.cqj.taobaoke.remote.RemoteDataSourceBase;
import com.starnet.cqj.taobaoke.view.BaseApplication;
import com.starnet.cqj.taobaoke.view.widget.GlideImageLoader;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class PersonActivity extends BaseActivity {


    public static final String KEY_USER = "user";
    public static final int REQUEST_CODE = 101;
    public static final int EDIT_NICK_REQUEST_CODE = 102;
    private static final int IMAGE_PICKER = 103;
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_nick_name)
    TextView mTvNickName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_address)
    TextView mTvAddress;

    @Override
    protected int getContentView() {
        return R.layout.activity_person;
    }

    @Override
    protected void init() {
        setTitleName(R.string.person_title);
        User user = (User) getIntent().getSerializableExtra(KEY_USER);
        if (!TextUtils.isEmpty(user.getAvatar())) {
            loadAvatar(user.getAvatar());
        }
        mTvNickName.setText(user.getNickname());
        mTvPhone.setText(user.getMobile());
        mTvAddress.setText(user.getProvince() + " " + user.getCity() + " " + user.getArea());
    }

    private void loadAvatar(String avatar) {
        Glide.with(this)
                .load(avatar)
                .into(mIvAvatar);
    }

    @OnClick({R.id.ll_change_nick, R.id.ll_change_phone, R.id.ll_edit_pwd, R.id.btn_logout, R.id.ll_change_avatar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_change_nick:
                EditNickNameActivity.start(this, mTvNickName.getText().toString(), EDIT_NICK_REQUEST_CODE);
                break;
            case R.id.ll_change_phone:
                BindPhoneActivity.start(this, REQUEST_CODE);
                break;
            case R.id.ll_edit_pwd:
                EditPwdActivity.start(this);
                break;
            case R.id.btn_logout:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确认退出登录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ((BaseApplication) getApplication()).setToken("");
                                finish();
                                RemoteDataSourceBase.INSTANCE.getUserService()
                                        .logout(((BaseApplication) getApplication()).getToken())
                                        .compose(PersonActivity.this.<JsonCommon<Object>>bindToLifecycle())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();
                break;
            case R.id.ll_change_avatar:
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
                imagePicker.setMultiMode(false);
                imagePicker.setShowCamera(true);  //显示拍照按钮
                imagePicker.setCrop(true);        //允许裁剪（单选才有效）
                imagePicker.setSaveRectangle(true); //是否按矩形区域保存
                imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
                imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
                imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
                imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
                imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                String phone = data.getStringExtra(BindPhoneActivity.KEY_RESULT);
                mTvPhone.setText(phone);
            } else if (requestCode == EDIT_NICK_REQUEST_CODE) {
                String nickName = data.getStringExtra(EditNickNameActivity.KEY_RESULT);
                mTvNickName.setText(nickName);
            }
        }
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0 && !TextUtils.isEmpty(images.get(0).path)) {
                    File file = new File(images.get(0).path);
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    RemoteDataSourceBase.INSTANCE.getUserService()
                            .updateAvatar(((BaseApplication) getApplication()).getToken(), body)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .compose(this.<JsonCommon<User>>bindToLifecycle())
                            .subscribe(new Consumer<JsonCommon<User>>() {
                                @Override
                                public void accept(JsonCommon<User> userJsonCommon) throws Exception {
                                    if ("200".equals(userJsonCommon.getCode())) {
                                        String avatar = userJsonCommon.getData().getAvatar();
                                        loadAvatar(avatar);
                                    } else {
                                        toast(userJsonCommon.getMessage());
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    throwable.printStackTrace();
                                    toast(R.string.upload_avatar_failed);
                                }
                            });
                }

            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void start(Context context, User user) {
        Intent starter = new Intent(context, PersonActivity.class);
        starter.putExtra(KEY_USER, user);
        context.startActivity(starter);
    }
}
