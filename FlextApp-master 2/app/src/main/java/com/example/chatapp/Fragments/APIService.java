package com.example.chatapp.Fragments;

import com.example.chatapp.Notifications.MyResponse;
import com.example.chatapp.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService
{
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAi-Oxx88:APA91bG_gz2u5l6G2NZ7veDnTLOxCPI5fCZ9ZVm1yW6YNPqQaop3RSeE2uhKlhbaB-7wg6fko0jyJMmGSVMwPw3-ZltGuVSizKMaYXb2CBTKKczgu555Y4QGKsLUfMFTqvgA0CfsVxHg"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
