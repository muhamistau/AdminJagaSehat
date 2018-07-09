package com.example.islam.adminjagasehat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DataActivity extends AppCompatActivity {

    ListView listView;
    FirebaseListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        listView = (ListView) findViewById(R.id.list_view);
        Query query = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jagasehat-fd156.firebaseio.com/users");
        FirebaseListOptions<People> options = new FirebaseListOptions.Builder<People>()
                .setLayout(R.layout.person_data)
                .setQuery(query, People.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView number = v.findViewById(R.id.number);
                TextView email = v.findViewById(R.id.email_text_view);
                TextView jk = v.findViewById(R.id.jk_text_view);
                TextView pekerjaan = v.findViewById(R.id.pekerjaan_text_view);
                TextView pendidikan = v.findViewById(R.id.pendidikan_text_view);
                TextView umur = v.findViewById(R.id.umur_text_view);

                People people = (People) model;
                number.setText("Data ke-" + (position + 1));
                email.setText(people.getEmail().toString());
                jk.setText(people.getJk().toString());
                pekerjaan.setText(people.getPekerjaan().toString());
                pendidikan.setText(people.getPendidikan().toString());
                umur.setText(people.getUmur().toString());
            }
        };

        listView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
