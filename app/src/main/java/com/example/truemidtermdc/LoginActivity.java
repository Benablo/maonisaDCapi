package com.example.truemidtermdc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truemidtermdc.api.RequestPlaceholder;
import com.example.truemidtermdc.api.RetrofitBuilder;
import com.example.truemidtermdc.pojos.Login;
import com.google.android.material.button.MaterialButton;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public EditText username, password;
    public MaterialButton loginBtn;
    public TextView register;

    public RetrofitBuilder retrofitBuilder;
    public RequestPlaceholder requestPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        register = findViewById(R.id.register);

        retrofitBuilder = new RetrofitBuilder();
        requestPlaceholder = retrofitBuilder.getRetrofit().create(RequestPlaceholder.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v) {
                if (username.getText() != null && password.getText() != null ) {
                    Call<Login> LoginCall =requestPlaceholder.login(new Login(null, username.getText().toString(), null, null, password.getText().toString()));

                    LoginCall.enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 404) {
                                    Toast.makeText(LoginActivity.this, "User not found!!", Toast.LENGTH_SHORT).show();
                                    Log.e( "LOGGING ERROR", response.message());
                                } else {
                                    Toast.makeText(LoginActivity.this, "There was an error upon logging in the API", Toast.LENGTH_SHORT).show();
                                    Log.e( "LOGGING ERROR", response.message());
                                }
                            } else {
                                if (response.code() == 200) {
                                    Login loginResponse = response.body();
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("USERID", loginResponse.getId());
                                    intent.putExtra("USERNAME", loginResponse.getUsername());
                                    intent.putExtra("TOKEN", loginResponse.getToken());

                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "There was an error upon logging in the API", Toast.LENGTH_SHORT).show();
                            Log.e( "LOGGING ERROR", t.getMessage());
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Please supply all the fields to login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}