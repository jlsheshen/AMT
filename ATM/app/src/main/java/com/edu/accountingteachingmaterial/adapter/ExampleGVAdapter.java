package com.edu.accountingteachingmaterial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;
import com.edu.accountingteachingmaterial.bean.ExampleBean;
import com.edu.accountingteachingmaterial.constant.ClassContstant;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

public class ExampleGVAdapter extends BaseAdapter {
    private Context context;

    private List<ExampleBean> beans;

    public ExampleGVAdapter(Context context) {
        super();
        this.context = context;
    }

    public void setBeans(List<ExampleBean> beans) {
        this.beans = beans;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return beans == null ? 0 : beans.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_example_gv, parent, false);
            AutoUtils.autoSize(convertView);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.titleView.setText(beans.get(position).getName());
        switch (beans.get(position).getType()) {
            case ClassContstant.MEADIA_TYPE:
//			Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(UriConstant.VIDEO_PATH + beans.get(position).getUrl(), MediaStore.Images.Thumbnails.MINI_KIND);

                //Bitmap bitmap = ThumbnailUtils.createVideoThumbnail("android.resource://aaa.mp4/raw/starttv", MediaStore.Images.Thumbnails.MINI_KIND);

//			bitmap = ThumbnailUtils.extractThumbnail(bitmap, 100, 60, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
//			viewHolder.cardIv.setImageBitmap(bitmap);
                viewHolder.cardIv.setImageResource(R.mipmap.capture);
                viewHolder.typeIv.setImageResource(R.mipmap.icon_shipin_n);
                break;
            case ClassContstant.PDF_TYPE:
                viewHolder.cardIv.setImageResource(R.mipmap.capture);
                viewHolder.typeIv.setImageResource(R.mipmap.icon_word_n);
                break;


            default:
                viewHolder.cardIv.setImageResource(R.drawable.ic_launcher);
                viewHolder.typeIv.setImageResource(R.drawable.ic_launcher);

                break;
        }

        // TODO Auto-generated method stub
        return convertView;
    }

    class ViewHolder {
        TextView titleView;
        ImageView typeIv, cardIv;

        public ViewHolder(View view) {
            super();
            titleView = (TextView) view.findViewById(R.id.item_example_name_tv);
            typeIv = (ImageView) view.findViewById(R.id.item_example_type_iv);
            cardIv = (ImageView) view.findViewById(R.id.item_example_card_iv);
        }

    }

}
