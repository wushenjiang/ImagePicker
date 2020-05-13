package com.androidlearing.imagepicker.adapter;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearing.imagepicker.R;
import com.androidlearing.imagepicker.domain.ImageItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: ImagePicker
 * @Package: com.androidlearing.imagepicker.adapter
 * @ClassName: ResultImageAdapter
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/5/13 15:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/13 15:08
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ResultImageAdapter extends RecyclerView.Adapter<ResultImageAdapter.InnerHolder> {
    private static final String TAG = "ResultImageAdapter";
    private List<ImageItem> mImageItems = new ArrayList<>();
    private int mHorizontalCount = 1;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        itemView.findViewById(R.id.image_check_box).setVisibility(View.GONE);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        final View itemView = holder.itemView;
        Point point = new Point();
        ((WindowManager) itemView.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(point);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(point.x/mHorizontalCount,point.x/mHorizontalCount);
        Log.d(TAG,"point -->"+point);
        itemView.setLayoutParams(layoutParams);
        ImageView imageView =itemView.findViewById(R.id.image_iv);
        ImageItem imageItem = mImageItems.get(position);
        Glide.with(imageView.getContext()).load(imageItem.getPath()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return mImageItems.size();
    }
    public void setData(List<ImageItem> result, int horizontalCount){
        this.mHorizontalCount = horizontalCount;
        mImageItems.clear();
        mImageItems.addAll(result);
        notifyDataSetChanged();

    }
    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
