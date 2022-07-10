package com.example.smallgithubhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Repo extends AppCompatActivity {
    private TextView txtRepoName, txtOwner, txtDesc, txtVis, txtLangs, txtStar, txtEye;
    private Button GITHUB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);

        //Init usable fields
        initView();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String txtUser = extras.getString("Username");
            String txtRepo = extras.getString("Reponame");
            GITHUB.setOnClickListener(v -> {
                Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + txtUser + "/" + txtRepo));
                startActivity(browse);
            });
            ReposService.getInstance(this);
            ReposService.getSingleRepoFromUserName(txtUser, txtRepo, new ReposService.SingleRepoResponseListener() {
                @Override
                public void onResponse(RepoCard repoCard) {
                    // Fill fields with requested data
                    txtRepoName.setText(repoCard.getName());
                    txtOwner.setText(String.format("Owner: %s", txtUser));
                    txtDesc.setText(String.format("%s", repoCard.getDesc().equals("null") ? "": repoCard.getDesc()));
                    txtVis.setText(String.format("Type: %s", repoCard.getType()));
                    txtStar.setText(String.format("%s", number(repoCard.getStar())));
                    txtEye.setText(String.format("%s", number(repoCard.getEye())));
                    // Request and service data to get lang
                    ReposService.getLangsFromLink(repoCard.getUrl(), new ReposService.LangsResponseListener() {
                        @Override
                        public void onResponse(ArrayList<String> langs, ArrayList<String> bytes) {
                            //Create lang string to field
                            StringBuilder langText = new StringBuilder();
                            for(int i = 0; i < langs.size(); i++){
                                langText.append(langs.get(i)).append(": ").append(bytes.get(i)).append("B\n");
                            }
                            if(langs.size() == 0){
                                langText.append("Lang: Unspecified");
                            }
                            txtLangs.setText(langText);
                        }

                        @Override
                        public void onResponseError(String message) {
                            Toast.makeText(Repo.this, message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Repo.this, RepoList.class));
                        }
                    });
                }

                @Override
                public void onResponseError(String message) {
                    Toast.makeText(Repo.this, "Error", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Repo.this, RepoList.class));
                }
            });
        }
    }

    private String number(int number){
        return number + "";
    }

    private void initView() {
        txtRepoName = findViewById(R.id.txtRepoName);
        txtOwner = findViewById(R.id.txtAuthor);
        txtDesc = findViewById(R.id.txtDescription);
        txtVis = findViewById(R.id.txtVis);
        txtLangs = findViewById(R.id.txtLangs);
        txtStar = findViewById(R.id.txtStar);
        txtEye = findViewById(R.id.txtEye);
        GITHUB = findViewById(R.id.GITHUB);
    }
}