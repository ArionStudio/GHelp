package com.example.smallgithubhelper;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class RestRequest {
    private static RequestQueue queue;
    private static RestRequest instance;
    private static Context context;

    private RestRequest(Context context){
        RestRequest.context = context;
        queue = getQueue();
    }

    //Singleton
    public static synchronized RestRequest getInstance(Context context){
        if(instance == null){
            instance = new RestRequest(context);
        }
        return instance;
    }

    public RequestQueue getQueue() {
        if(queue == null){
            //Prevent from leaking
            queue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return queue;
    }

    public <T> void addToRequestQueue(Request<T> request){
        getQueue().add(request);
    }
}
