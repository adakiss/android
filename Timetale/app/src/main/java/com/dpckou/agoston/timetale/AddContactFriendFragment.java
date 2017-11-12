package com.dpckou.agoston.timetale;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by agoston on 2017.11.12..
 */

public class AddContactFriendFragment extends Fragment {
    View view;
    AppCompatTextView tv;

    public static AddContactFriendFragment newInstance(ContactFriend friend){
        Bundle args = new Bundle();
        args.putParcelable("selected_friend", friend);
        return newInstance(args);
    }

    public static AddContactFriendFragment newInstance(Bundle bundle) {

        AddContactFriendFragment fragment = new AddContactFriendFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contact_list_item,
                container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tv = view.findViewById(R.id.oneFriend);
        if(getArguments() != null && getArguments().containsKey("selected_friend")){
            ContactFriend fr = getArguments().getParcelable("selected_friend");
            tv.setText(fr.getNickName());
        }
    }
}
