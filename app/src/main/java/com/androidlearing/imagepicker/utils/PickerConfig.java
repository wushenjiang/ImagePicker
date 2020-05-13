package com.androidlearing.imagepicker.utils;

import com.androidlearing.imagepicker.adapter.ImageListAdapter;
import com.androidlearing.imagepicker.domain.ImageItem;

import java.util.List;

/**
 * @ProjectName: ImagePicker
 * @Package: com.androidlearing.imagepicker.utils
 * @ClassName: PickConfig
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/5/13 11:20
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/13 11:20
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PickerConfig {
        private PickerConfig(){}
        private static PickerConfig mPickerConfig;
        public static PickerConfig getInstance(){
            if(mPickerConfig ==null){
                mPickerConfig = new PickerConfig();
            }
            return mPickerConfig;
        }
    private int maxSelectedCount =1;
    private OnImageSelectedFinishListener mImageSelectedFinishedListener;

    public int getMaxSelectedCount() {
        return maxSelectedCount;
    }

    public void setMaxSelectedCount(int maxSelectedCount) {
        this.maxSelectedCount = maxSelectedCount;
    }
    public void setOnImageSelectedFinishListener(OnImageSelectedFinishListener listener){
        this.mImageSelectedFinishedListener = listener;
    }
    public OnImageSelectedFinishListener getImageSelectedFinishedListener(){
        return mImageSelectedFinishedListener;
    }
    public interface OnImageSelectedFinishListener{
        void onSelectedFinished(List<ImageItem> result);
    }
}
