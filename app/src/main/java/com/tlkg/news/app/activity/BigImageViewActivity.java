package com.tlkg.news.app.activity;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.tlkg.news.app.NewsClientApplication;
import com.tlkg.news.app.R;
import com.tlkg.news.app.adapter.BigImageViewPagerAdapter;
import com.tlkg.news.app.base.BaseActivity;
import com.tlkg.news.app.util.DataUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.InjectView;

/**
 * 查看图片界面
 */
public class BigImageViewActivity extends BaseActivity {

    private static final String TAG = "BigImageViewActivity";

    public static final String IMAGE_URLS = "image_urls";

    public static final String IMAGE_POSITION = "image_position";

    public static final String IMAGE_TYPE = "image_type";

    @InjectView(R.id.activity_big_image_viewpager)
    ViewPager viewPager;

    @InjectView(R.id.activity_big_image_index_tv)
    TextView indexTv;

    @InjectView(R.id.activity_big_image_setting_wallpaper_tv)
    TextView setWallpaperTv;

    @InjectView(R.id.activity_big_image_save_tv)
    TextView saveTv;

    @InjectView(R.id.activity_big_image_pb)
    ProgressBar progressBar;

    private int mType;

    private int mPosition;

    private List<String> imageUrls;

    private BigImageViewPagerAdapter mAdapter;

    public static void startActivity(Activity activity, ArrayList<String> list) {
        startActivity(activity, list, 0);
    }

    public static void startActivity(Activity activity, ArrayList<String> list, int position) {
        startActivity(activity, list, position, 0);
    }

    public static void startActivity(Activity activity, ArrayList<String> list, int position, int type) {
        Intent intent = new Intent(activity, BigImageViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(IMAGE_URLS, list);
        bundle.putInt(IMAGE_POSITION, position);
        bundle.putInt(IMAGE_TYPE, type);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    public void initData(Bundle bundle) {
        if (bundle == null) return;
        mType = bundle.getInt(IMAGE_TYPE);
        mPosition = bundle.getInt(IMAGE_POSITION);
        imageUrls = bundle.getStringArrayList(IMAGE_URLS);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_big_image_view;
    }

    @Override
    protected void initViews() {
        mAdapter = new BigImageViewPagerAdapter(this, imageUrls);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(mPosition);
        setIndex(mPosition);
    }

    @Override
    public void initEvent() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                setIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                final BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 子线程获得图片路径
                        final String imagePath = getImagePath(imageUrls.get(mPosition));
                        // 主线程更新
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (imagePath != null) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
                                    if (bitmap != null) {
                                        saveImageToGallery(BigImageViewActivity.this, bitmap);
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(BigImageViewActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    }
                }).start();
            }
        });
        setWallpaperTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 子线程获得图片路径
                        final String imagePath = getImagePath(imageUrls.get(mPosition));
                        if (imagePath != null) setWallPaper(imagePath);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(BigImageViewActivity.this, getString(R.string.setting_success), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private void setIndex(int currentPosition) {
        indexTv.setText((currentPosition + 1) + " / " + imageUrls.size());
    }

    /**
     * Glide 获得图片缓存路径
     */
    private String getImagePath(String imgUrl) {
        String path = null;
        FutureTarget<File> future = Glide.with(this)
                .load(imgUrl)
                .downloadOnly(500, 500);
        try {
            File cacheFile = future.get();
            path = cacheFile.getAbsolutePath();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 保存图片至相册
     */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), NewsClientApplication.getAppContext().getResources().getString(R.string.save_dir_name));
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = DataUtils.getYMDHS(System.currentTimeMillis()) + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsoluteFile())));
    }

    //设置壁纸
    public void setWallPaper(String path) {
        WallpaperManager mWallManager = WallpaperManager.getInstance(this);
        try {
            mWallManager.setBitmap(BitmapFactory.decodeFile(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
