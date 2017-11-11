package com.dpckou.agoston.timetale;

import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.FacebookSdk;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;


/*
interaction logic to add a new friend into the event.
 */
public class AddFriendFragment extends Fragment {

    private View myList;
    private static String GRAPH_QUERY = "/me/friends?fields=name,profile_pic";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_addfriend,container,false);

        return v;
    }
    public static AddFriendFragment newInstance(Bundle bundle){
        AddFriendFragment fragment = new AddFriendFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myList = getActivity().findViewById(R.id.myList);



    }


}
