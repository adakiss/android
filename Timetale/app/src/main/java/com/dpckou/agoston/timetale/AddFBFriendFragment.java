package com.dpckou.agoston.timetale;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/*
interaction logic to add a new friend into the event.
 */
public class AddFBFriendFragment extends Fragment {

    private View myList;
    private static String GRAPH_QUERY = "/me/friends?fields=name,profile_pic";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_add_fb_friend,container,false);

        return v;
    }
    public static AddFBFriendFragment newInstance(Bundle bundle){
        AddFBFriendFragment fragment = new AddFBFriendFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myList = getActivity().findViewById(R.id.myList);



    }


}
