package com.androidlearing.imagepicker.domain;

/**
 * @ProjectName: ImagePicker
 * @Package: com.androidlearing.imagepicker.domain
 * @ClassName: ImageItem
 * @Description: java类作用描述
 * @Author: 武神酱丶
 * @CreateDate: 2020/5/12 21:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/12 21:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ImageItem {
    private String path;
    private String title;
    private long date;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ImageItem{" +
                "path='" + path + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }

    public ImageItem(String path, String title, long date) {
        this.path = path;
        this.title = title;
        this.date = date;
    }
}
