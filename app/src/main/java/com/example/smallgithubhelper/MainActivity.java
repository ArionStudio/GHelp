package com.example.smallgithubhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText inpUserName;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Init usable elements
        initElements();
        //Init action
        actionInit();
    }

    private void actionInit() {
        //Action sent to api request checking that the user is valid
        button.setOnClickListener(v -> {
            ReposService.getInstance(MainActivity.this);
            ReposService.userExist(this.inpUserName.getText().toString(), new ReposService.UserResponseListener() {
                @Override
                public void onResponse(String username) {
                    // Clear error msg
                    errorText.setText("");
                    // reference to next activity with extra data
                    Intent intent = new Intent(MainActivity.this, RepoList.class);
                    intent.putExtra("Username", username);
                    startActivity(intent);
                }

                @Override
                public void onResponseError(int errorCode) {
                    switch (errorCode) {
                        case 1: {
                            errorText.setText(R.string.EmptyUserInp);
                            break;
                        }

                        case 2: {
                            errorText.setText(R.string.InvalidUserName);
                            break;
                        }

                        case 403: {
                            errorText.setText(R.string.WaitMsg);
                            break;
                        }

                        case 404: {
                            errorText.setText(R.string.MissingUserInGitDataBase);
                            break;
                        }

                        default: errorText.setText(R.string.UnknownErrorMsg);

                    }
                }
            });
        });
    }

    private void initElements() {
        button = findViewById(R.id.button);
        inpUserName = findViewById(R.id.inpUserName);
        errorText = findViewById(R.id.errorText);
    }


}

