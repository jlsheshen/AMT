package com.edu.accountingteachingmaterial.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.accountingteachingmaterial.R;

/**
 * Created by Administrator on 2017/3/2.
 */

public class TextBookGvAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_textbook_gv,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.introTv.setText("asdadasdasdfggggggggggggggggggggggggggggggh");
        viewHolder.publisherTv.setText("王教授");
        viewHolder.organizationTv.setText("爱丁代表队");
        viewHolder.nameTv.setText("会计");
        viewHolder.bgIv.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }

    class ViewHolder{
        ImageView bgIv;
        TextView nameTv,organizationTv,publisherTv,introTv;
        public ViewHolder(View view) {
            bgIv = (ImageView) view.findViewById(R.id.item_textbook_bg_iv);
            nameTv = (TextView) view.findViewById(R.id.item_textbook_name_tv);
            organizationTv = (TextView) view.findViewById(R.id.item_textbook_organization_tv);
            publisherTv = (TextView) view.findViewById(R.id.item_textbook_publisher_tv);
            introTv = (TextView) view.findViewById(R.id.item_textbook_intro_tv);


        }
    }
}
