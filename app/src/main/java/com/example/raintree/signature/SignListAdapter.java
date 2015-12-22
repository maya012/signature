package com.example.raintree.signature;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rainTree on 2015-12-03.
 */

public class SignListAdapter extends BaseAdapter {

    private Context mContext;

    private List<SignListItem> mItems = new ArrayList<SignListItem>();

    public SignListAdapter(Context context) {
        mContext = context;
    }

    public void clear() {
        mItems.clear();
    }

    public void addItem(SignListItem it) {
        mItems.add(it);
    }

    public int getCount() {
        return mItems.size();
    }

    public Object getItem(int position) {
        return mItems.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        SignListItemView itemView;
        if (convertView == null) {
            itemView = new SignListItemView(mContext);
        } else {
            itemView = (SignListItemView) convertView;
        }

        // set current item data
        itemView.setContents(0, ((String) mItems.get(position).getData(0)));
        itemView.setContents(1, ((String) mItems.get(position).getData(1)));
        itemView.setContents(2, ((String) mItems.get(position).getData(2)));
        itemView.setContents(3, ((String) mItems.get(position).getData(3)));

        return itemView;
    }

}
