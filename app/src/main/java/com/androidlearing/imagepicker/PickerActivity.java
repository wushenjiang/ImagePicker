package com.androidlearing.imagepicker;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearing.imagepicker.adapter.ImageListAdapter;
import com.androidlearing.imagepicker.domain.ImageItem;
import com.androidlearing.imagepicker.utils.PickerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: ImagePicker
 * @Package: com.androidlearing.imagepicker
 * @ClassName: PickerActivity
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/5/12 21:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/12 21:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PickerActivity extends AppCompatActivity implements ImageListAdapter.OnItemSelectedChangeListener {
    private static final String TAG = "PickerActivity";

    public static final int LOADER_ID = 1;
    private List<ImageItem> mImageItems = new ArrayList<>();
    private ImageListAdapter mImageListAdapter;
    private TextView mFinishView;
    private PickerConfig mPickerConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        initLoaderManager();
        initView();
        initEvent();
        initConfig();
    }

    private void initConfig() {
        mPickerConfig = PickerConfig.getInstance();
        int maxSelectedCount = mPickerConfig.getMaxSelectedCount();
        mImageListAdapter.setMaxSelectedCount(maxSelectedCount);
    }

    private void initEvent() {
        mImageListAdapter.setOnItemSelectedChangedListener(this);
        mFinishView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取所选择的数据
                List<ImageItem> result = new ArrayList<>();
                result.addAll(mImageListAdapter.getSelectItems());
                mImageListAdapter.release();
                //通知其他地方
                //取得主窗口设置的监听器
                PickerConfig.OnImageSelectedFinishListener imageSelectedFinishedListener = mPickerConfig.getImageSelectedFinishedListener();
                //为监听器设置数据
                if (imageSelectedFinishedListener != null) {
                    imageSelectedFinishedListener.onSelectedFinished(result);
                }
                //结束界面
                finish();
            }
        });
        findViewById(R.id.back_press_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mFinishView = this.findViewById(R.id.finish_tv);
        RecyclerView listView = this.findViewById(R.id.image_list_view);
        listView.setLayoutManager(new GridLayoutManager(this,3));
        //设置适配器
        mImageListAdapter = new ImageListAdapter();
        listView.setAdapter(mImageListAdapter);
    }

    private void initLoaderManager() {
        mImageItems.clear();
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @NonNull
            @Override
            public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
                if (id == LOADER_ID) {
                    return new CursorLoader(PickerActivity.this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "title", "date_added"}
                            , null, null, "date_added DESC");
                }
                return null;
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String[] columnNames = cursor.getColumnNames();
                        while (cursor.moveToNext()) {
                            String path = cursor.getString(0);
                            String title = cursor.getString(1);
                            long date = cursor.getLong(2);
                            ImageItem imageItem = new ImageItem(path,title,date);
                            mImageItems.add(imageItem);
                        }
                    }
                    cursor.close();
//                    for (ImageItem imageItem : mImageItems) {
//                        Log.d(TAG,"images ==>"+imageItem);
//                    }
                    mImageListAdapter.setData(mImageItems);
                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Cursor> loader) {

            }
        });
    }

    @Override
    public void onItemSelectedChange(List<ImageItem> selectedItems) {
        //所选择的数据发生变化
        mFinishView.setText("("+selectedItems.size()+"/"+mImageListAdapter.getMaxSelectedCount()+")完成");
    }
}
