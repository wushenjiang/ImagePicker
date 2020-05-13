package com.androidlearing.imagepicker.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

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
 * @ClassName: ImageListAdapter
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/5/12 21:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/12 21:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.InnerHolder> {
    private List<ImageItem> mImageItems = new ArrayList<>();
    private   List<ImageItem> mSelectItems = new ArrayList<>();
    private OnItemSelectedChangeListener mItemSelectedChangeListener = null;
    public static final int MAX_SELECTED_COUNT = 9;
    private int maxSelectedCount = MAX_SELECTED_COUNT;

    public int getMaxSelectedCount() {
        return maxSelectedCount;
    }

    public void setMaxSelectedCount(int maxSelectedCount) {
        this.maxSelectedCount = maxSelectedCount;
    }

    public  List<ImageItem> getSelectItems() {
        return mSelectItems;
    }

    public  void setSelectItems(List<ImageItem> mSelectItems) {
        this.mSelectItems = mSelectItems;
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载ItemView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        Point point = new Point();
        ((WindowManager) parent.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(point);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(point.x/3,point.x/3);
        itemView.setLayoutParams(layoutParams);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        //绑定数据
        final View itemView = holder.itemView;
        ImageView imageView =itemView.findViewById(R.id.image_iv);
        final CheckBox checkBox = itemView.findViewById(R.id.image_check_box);
        final View cover = itemView.findViewById(R.id.image_cover);
        final ImageItem imageItem = mImageItems.get(position);
        Glide.with(imageView.getContext()).load(imageItem.getPath()).into(imageView);
        //根据数据状态显示内容
        //这里要与下面反着来,因为是真实的状态
        if(mSelectItems.contains(imageItem)){
            //包含内容,要把内容放到list里
            mSelectItems.add(imageItem);
            //修改UI
            checkBox.setChecked(true);
            cover.setVisibility(View.VISIBLE);
            checkBox.setButtonDrawable(itemView.getContext().getDrawable(R.mipmap.yes));
        }else{
            //不包含内容,要把内容移出list
            mSelectItems.remove(imageItem);
            //修改UI
            checkBox.setChecked(false);
            checkBox.setButtonDrawable(itemView.getContext().getDrawable(R.mipmap.circle));
            cover.setVisibility(View.GONE);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //是否选择上
                //如果选择上就变成取消
                //如果没选上就选上
                if(mSelectItems.contains(imageItem)){
                    //已经选上了,应取消选择
                    mSelectItems.remove(imageItem);
                    //修改UI
                    checkBox.setChecked(false);
                    checkBox.setButtonDrawable(itemView.getContext().getDrawable(R.mipmap.circle));
                    cover.setVisibility(View.GONE);
                }else{
                    if(mSelectItems.size()>=maxSelectedCount){
                        //给个提示
                        Toast toast = Toast.makeText(checkBox.getContext(), null, Toast.LENGTH_SHORT);
                        toast.setText("最多可以选择"+maxSelectedCount+"张图片");
                        toast.show();
                        return;
                    }
                    //没有选择上,应该选择
                    mSelectItems.add(imageItem);
                    //修改UI
                    checkBox.setChecked(true);
                    cover.setVisibility(View.VISIBLE);
                    checkBox.setButtonDrawable(itemView.getContext().getDrawable(R.mipmap.yes));
                }
                if(mItemSelectedChangeListener!=null){
                    mItemSelectedChangeListener.onItemSelectedChange(mSelectItems);
                }
            }
        });
    }
    public void setOnItemSelectedChangedListener(OnItemSelectedChangeListener listener){
        this.mItemSelectedChangeListener = listener;
    }

    public void release() {
        mSelectItems.clear();
    }

    public interface OnItemSelectedChangeListener{
        void onItemSelectedChange(List<ImageItem> selectedItems);
    }
    @Override
    public int getItemCount() {
        return mImageItems.size();
    }

    public void setData(List<ImageItem> imageItems) {
        mImageItems.clear();
        mImageItems.addAll(imageItems);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
