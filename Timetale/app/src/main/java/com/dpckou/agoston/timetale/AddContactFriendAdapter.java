package com.dpckou.agoston.timetale;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by agoston on 2017.11.12..
 */

class AddContactFriendAdapter extends BaseAdapter {

    private List<ContactFriend> items;

    public AddContactFriendAdapter(List<ContactFriend> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items != null ? this.items.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return items != null ? this.items.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = View.inflate(viewGroup.getContext(),R.layout.contact_list_item,
                    null);
        }
        TextView titleTextView = view.findViewById(R.id.oneFriend);
        ContactFriend current = items.get(i);

        titleTextView.setText(current.getNickName());
        return view;
    }
}
