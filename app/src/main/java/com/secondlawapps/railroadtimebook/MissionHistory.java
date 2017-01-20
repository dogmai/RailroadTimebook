package com.secondlawapps.railroadtimebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MissionHistory extends AppCompatActivity {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    private RecyclerView missionHistory;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Query missionDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_history);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();


        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //Recycler View
        missionHistory = (RecyclerView)findViewById(R.id.mission_history);
        missionHistory.setHasFixedSize(true);
        missionHistory.setLayoutManager(new LinearLayoutManager(this));

        //getting the current logged in user
        FirebaseUser user = firebaseAuth.getCurrentUser();



        //Send a Query to the database to order the missions by earliest date
        database = FirebaseDatabase.getInstance();
        //myRef = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("missions");
        missionDates = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("missions").orderByChild("setDate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<UserInformation, BlogViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<UserInformation, BlogViewHolder>(
                        UserInformation.class,
                        R.layout.design_row,
                        BlogViewHolder.class,
                        missionDates) {
                    @Override
                    protected void populateViewHolder(BlogViewHolder viewHolder, UserInformation model, int position) {
                        viewHolder.setTrainSymbol(model.getTrainSymbol());
                        viewHolder.setEngineNumbers(model.getEngineNumbers());
                        viewHolder.setOnDutyLocation(model.getOnDutyLocation());
                        viewHolder.setOffDutyLocation(model.getOffDutyLocation());
                        viewHolder.setSetDate(model.getSetDate());
                        viewHolder.setOnDutyTime(model.getOnDutyTime());
                        viewHolder.setOffDutyTime(model.getOffDutyTime());
                        viewHolder.setSetPay(model.getSetPay());
                    }
                };

        missionHistory.setAdapter(firebaseRecyclerAdapter);
    }

    //View Holder For Recycler View

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }

        public void setTrainSymbol(String setTrainSymbol) {
            EditText post_setTrainSymbol = (EditText) mView.findViewById(R.id.editTextTrainSymbol);
            post_setTrainSymbol.setText(setTrainSymbol);
        }

        public void setEngineNumbers(String setEngineNumbers) {
            EditText post_setEngineNumbers = (EditText) mView.findViewById(R.id.editTextEngineNumbers);
            post_setEngineNumbers.setText(setEngineNumbers);
        }

        public void setOnDutyLocation(String onDutyLocation) {
            EditText post_onDutyLocation = (EditText) mView.findViewById(R.id.editTextOnDutyLocation);
            post_onDutyLocation.setText(onDutyLocation);
        }

        public void setOffDutyLocation(String offDutyLocation) {
            EditText post_offDutyLocation = (EditText) mView.findViewById(R.id.editTextOffDutyLocation);
            post_offDutyLocation.setText(offDutyLocation);
        }

        public void setSetDate(String setDate) {
            TextView post_setDate = (TextView)mView.findViewById(R.id.textViewSetDate);
            post_setDate.setText(setDate);
        }

        public void setOnDutyTime(String onDutyTime) {
            TextView post_onDutyTime = (TextView)mView.findViewById(R.id.textViewOndutyTime);
            post_onDutyTime.setText(onDutyTime);
        }

        public void setOffDutyTime(String offDutyTime) {
            TextView post_offDutyTime = (TextView)mView.findViewById(R.id.textViewOffDutyTime);
            post_offDutyTime.setText(offDutyTime);
        }

        public void setSetPay(String setPay) {
            EditText post_setPay = (EditText) mView.findViewById(R.id.editTextSetPay);
            post_setPay.setText(setPay);
        }
    }
}
