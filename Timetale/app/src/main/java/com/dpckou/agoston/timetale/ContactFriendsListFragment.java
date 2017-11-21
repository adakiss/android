package com.dpckou.agoston.timetale;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dpckou.agoston.timetale.weekday.EventBundle;
import com.google.android.gms.games.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agoston on 2017.11.12..
 */

public class ContactFriendsListFragment extends Fragment {
    ListView listView;
    private static final int MY_PERMISSIONS_READ_CONTACTS = 1000;
    List<ContactFriend> friends = new ArrayList<>();

    private List<String> alreadySelected = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listView = (ListView) inflater.inflate(R.layout.fragment_add_contact_friend,
                container, false);
        return listView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle b = this.getArguments();
        if(b != null){
            Object _e = b.getParcelable(EventBundle.NAME);
            if(_e != null){
                alreadySelected = ((EventBundle)_e).getEvent().getFriends();
            }
        }

        initializeFragment();
    }

    private void setAdapterToList(){
        final AddContactFriendAdapter adapter = new AddContactFriendAdapter(friends);
        listView.setAdapter(adapter);

        for (int i = 0; i < adapter.getCount();i++){
            ContactFriend cf = (ContactFriend) adapter.getItem(i);
            View v = getViewByPosition(i,listView);
            colorizeFriendBg(v, cf);
            adapter.notifyDataSetChanged();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactFriend fr = (ContactFriend) adapter.getItem(position);
                addToSelected(fr);
                //TODO: make it switch some BG colour when clicked. use getViewByPosition.
                View v = getViewByPosition(position,listView);
                colorizeFriendBg(v, fr);
            }
        });
    }

    /**
     * Just tells what to do when a friend was clicked on.
     * @param friend longboii
     */
    private void addToSelected(ContactFriend friend){
        //in this case I just switched the selection bool.
        friend.setSelected(!friend.isSelected());
    }

    private void colorizeFriendBg(View v, ContactFriend friend){
        if(friend.isSelected()){
            v.setBackgroundColor(0x993399ff);
        }else{
            v.setBackgroundColor(0x00000000);
        }
        v.invalidate();

    }

    private View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    private void initializeFragment(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(),new String[]{
                    Manifest.permission.READ_CONTACTS
            },MY_PERMISSIONS_READ_CONTACTS);
        }else{
            getContactList();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch(requestCode){
            case MY_PERMISSIONS_READ_CONTACTS:{
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getContactList();
                    Log.d("PERMISSION_GRANTED", "Contacts were loaded.");
                }
                if(grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_DENIED){
                    Log.d("PERMISSION_DENIED","Do some shit, e.g. add a textbox for strings.");
                }
            }
        }
    }

    private void getContactList() {

        ContentResolver cr = getContext().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                //now actually adding the name in a new ContactFriend:
                ContactFriend cf = new ContactFriend(name);
                /*

                NOTE**NOTE**NOTE**NOTE**NOTE**NOTE**NOTE**NOTE**NOTE**NOTE**NOTE**

                here I use match by name which is wrong. match by phone number would be preferable.
                but that would require redesigning half the shit.
                 */
                friends.add(cf);
                if(alreadySelected != null){
                    for (String s : alreadySelected){
                        if(cf.getNickName().equals(s)){
                            cf.setSelected(true);
                            break;
                        }
                    }
                }

                Log.d("newContactFriend", cf.getNickName());
            }
            setAdapterToList();
        }else Log.d("newContactFriend", "The current query cursor was null.");
        if(cur!=null){
            cur.close();
        }
    }

    public static ContactFriendsListFragment newInstance(){
        ContactFriendsListFragment fragment = new ContactFriendsListFragment();
        return fragment;
    }
}
