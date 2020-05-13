package com.androidlearing.imagepicker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearing.imagepicker.adapter.ResultImageAdapter;
import com.androidlearing.imagepicker.domain.ImageItem;
import com.androidlearing.imagepicker.utils.PickerConfig;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity implements PickerConfig.OnImageSelectedFinishListener {
    public static final int MAX_SELECTED_COUNT = 9;
    private static final String TAG = "MainActivity";
    private RecyclerView mResultListView;
    private ResultImageAdapter mResultImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        checkPermissions();
        initPickerConfig();
    }

    private void initView() {
        mResultListView = this.findViewById(R.id.result_list);
        mResultImageAdapter = new ResultImageAdapter();
        mResultListView.setAdapter(mResultImageAdapter);
    }

    private void initPickerConfig() {
        PickerConfig pickerConfig = PickerConfig.getInstance();
        pickerConfig.setMaxSelectedCount(MAX_SELECTED_COUNT);
        pickerConfig.setOnImageSelectedFinishListener(this);
    }

    private void checkPermissions() {
        int readPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        if(readPermission!= PackageManager.PERMISSION_GRANTED){
            //没有权限
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0){
            if(grantResults.length==1&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //有权限
            }else{
                //无权限,根据交互进行处理
            }
        }
    }
    public void pickImages(View v){
        //TODO:打开另外一个界面
        startActivity(new Intent(this,PickerActivity.class));
    }

    @Override
    public void onSelectedFinished(List<ImageItem> result) {
//        //所选择的图片列表回来了
//        for(ImageItem imageItem:result){
//            Log.d(TAG,"item -->"+imageItem);
//        }
        int horizontalCount;
        if(result.size()<3){
            horizontalCount = result.size();
        }else{
            horizontalCount = 3;
        }
        mResultListView.setLayoutManager(new GridLayoutManager(this,horizontalCount));
        mResultImageAdapter.setData(result,horizontalCount);
    }
}
