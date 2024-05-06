package com.example.aplikasilogindanregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.Api.Api;
import com.example.Api.ApiService;
import com.example.data.Register.Registrasi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
        String Username, Name, Password;
        EditText etUsernameRegister, etNameRegister, etPasswordRegister;
        Button btnRegister;
        TextView tvAlreadyHave;
        Api apiInterfaces;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_register);

            etUsernameRegister = findViewById(R.id.etRegisterUsername);
            etNameRegister = findViewById(R.id.etRegisterName);
            etPasswordRegister = findViewById(R.id.etRegisterPassword);
            tvAlreadyHave = findViewById(R.id.tvLoginHere);
            btnRegister = findViewById(R.id.btnRegister);

            tvAlreadyHave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Username = etUsernameRegister.getText().toString();
                    Name = etNameRegister.getText().toString();
                    Password = etPasswordRegister.getText().toString();
                    register(Username, Name, Password);
                }
            });

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        private void register(String username, String name, String password) {

            apiInterfaces = ApiService.getClient().create(Api.class);
            Call<Registrasi> call = apiInterfaces.registerResponse(username, name, password);
            call.enqueue(new Callback<Registrasi>() {
                @Override
                public void onResponse(Call<Registrasi> call, Response<Registrasi> response) {
                    if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Registrasi> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


