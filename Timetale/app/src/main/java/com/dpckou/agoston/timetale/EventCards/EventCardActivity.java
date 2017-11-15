package com.dpckou.agoston.timetale.EventCards;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dpckou.agoston.timetale.AddContactFriendAdapter;
import com.dpckou.agoston.timetale.ContactFriend;
import com.dpckou.agoston.timetale.MainActivity;
import com.dpckou.agoston.timetale.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agoston on 2017.11.14..
 */

public class EventCardActivity extends AppCompatActivity {

    public static final String TITLE_TAG = "eventTitle";
    public static final String FROM_TAG = "eventFrom";
    public static final String TO_TAG = "eventTo";
    public static final String LOCATION_TAG = "eventLocation";
    public static final String FRIENDS_TAG = "eventDescription";
    public static final String DESCRIPTION_TAG = "eventNameStrings";

    private TextView title;
    private TextView from;
    private TextView to;
    private TextView location;
    private ListView friends;
    private TextView description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_card);

        title = findViewById(R.id.title);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        location = findViewById(R.id.location);
        friends = findViewById(R.id.friends);
        description = findViewById(R.id.description);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationIcon(R.drawable.quantum_ic_forward_30_white_36);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Intent _creator = getIntent();
        try{
            title.setText(_creator.getStringExtra(TITLE_TAG));

            from.setText(_creator.getStringExtra(FROM_TAG));
            to.setText(_creator.getStringExtra(TO_TAG));
            location.setText(_creator.getStringExtra(LOCATION_TAG));
            description.setText(_creator.getStringExtra(DESCRIPTION_TAG));

            //TODO revise this shit
            String[] items = _creator.getStringArrayExtra(FRIENDS_TAG);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, items);
            if(adapter != null && adapter.getCount() > 0){
                friends.setAdapter(adapter);
            }else Log.d("EVENTCARD_ADAPTER", "The retrieved friends list was null or empty.");
        }catch(Exception ex){
            Log.d("Event card error", Log.getStackTraceString(ex));
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
