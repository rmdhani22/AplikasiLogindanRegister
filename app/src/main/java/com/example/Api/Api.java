package com.example.Api;

import com.example.data.Login.Login;
import com.example.data.Register.Registrasi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
        @FormUrlEncoded
        @POST("login.php")
        Call<Login> loginResponse(
                @Field("username") String username,
                @Field("password") String password
        );

        @FormUrlEncoded
        @POST("register.php")
        Call<Registrasi> registerResponse(
                @Field("username") String username,
                @Field("name") String name,
                @Field("password") String password
        );
    }
