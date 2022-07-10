package com.example.smallgithubhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//Activity which display repos of user
public class RepoList extends AppCompatActivity {
    private RepoListAdapter repoListAdapter; //Adapter for handle CARDVIEW in RECYCLERVIEW bellow
    private RecyclerView repoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);

        Bundle extras = getIntent().getExtras();
        if(extras != null){ //Checking if is extra parameter and make request for all repo data
            // Set up name field
            String txtUser = extras.getString("Username");
            TextView txtUserName = findViewById(R.id.username);
            txtUserName.setText(txtUser);
            ReposService.getInstance(RepoList.this);
            ReposService.getReposDataFromUserName(txtUser, new ReposService.ReposResponseListener() {
                @Override
                public void onResponse(ArrayList<RepoCard> repoCards) {
                    repoListAdapter = new RepoListAdapter(RepoList.this, txtUser);
                    repoList = findViewById(R.id.repoList);
                    repoListAdapter.setRepoCards(repoCards);
                    repoList.setAdapter(repoListAdapter);
                    repoList.setLayoutManager(new LinearLayoutManager(RepoList.this));
                }

                @Override
                public void onResponseError(String message) {
                    Toast.makeText(RepoList.this, "Error with getting data", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RepoList.this, MainActivity.class));
                }
            });
        }else {
            startActivity(new Intent(RepoList.this, MainActivity.class));
        }
    }
}