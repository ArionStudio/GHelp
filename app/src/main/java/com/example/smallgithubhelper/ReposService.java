package com.example.smallgithubhelper;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

//Class for control get data from GITHUB api
public class ReposService {
    private static final String QUERY_URL_FOR_USER_AND_REPOS = "https://api.github.com/users/";
    private static final String QUERY_URL_FOR_SINGLE_REPO = "https://api.github.com/repos/";
    private static ReposService instance;
    private static RestRequest queue;

    private ReposService(Context context){
        //Init API connection
        queue = RestRequest.getInstance(context);
    }

    //Singleton
    public static ReposService getInstance(Context context){
        if(instance == null){
            instance = new ReposService(context);
        }
        return instance;
    }



    // Callback for prevent thread error which may create Volley (api controller)
    // For bellow function
    public interface UserResponseListener{
        void onResponse(String username);
        void onResponseError(int errorCode);
    }

    //Function for getting from GITHUB API REPOSITORIES DATA
    public static void userExist(String username, UserResponseListener userResponseListener){
        //Check if is text in input
        if(username.equals("")){
            userResponseListener.onResponseError(1);
            return;
        }
        // Crate link for request
        String url = QUERY_URL_FOR_USER_AND_REPOS + username;

        // Request a object response from the provided URL.
        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                String name = response.getString("login");
                userResponseListener.onResponse(name);
            } catch (JSONException e) {
                e.printStackTrace();
                userResponseListener.onResponseError(2);
            }
        }, error -> userResponseListener.onResponseError(error.networkResponse.statusCode));//"Hold back!! \nWait a bit before you click me again :)"

        // Add the request to the RequestQueue.
        queue.addToRequestQueue(objRequest);
    }

    // Callback for prevent thread error which may create Volley (api controller)
    // For bellow function
    public interface ReposResponseListener{
        void onResponse(ArrayList<RepoCard> repoCards);
        void onResponseError(String message);
    }

    //Function for getting from GITHUB API repositories data
    public static void getReposDataFromUserName(String username, ReposResponseListener reposResponseListener){
        //Check if is text in input
        if(username.equals("")){
            reposResponseListener.onResponseError("Type name of user in input");
            return;
        }
        // Create url for request
        String url = QUERY_URL_FOR_USER_AND_REPOS + username + "/repos";

        // Request a array response from the provided URL.
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            try {
                // Create array with data to ease send element
                ArrayList<RepoCard> repoCards = new ArrayList<>();
                for(int i = 0; i < response.length(); i++){
                    JSONObject repoInfo = response.getJSONObject(i);
                    repoCards.add(new RepoCard(
                            repoInfo.getString("name"),
                            repoInfo.getString("visibility"),
                            repoInfo.getString("description"),
                            repoInfo.getString("language")
                    ));
                }
                reposResponseListener.onResponse(repoCards);


            } catch (JSONException e) {
                e.printStackTrace();
                reposResponseListener.onResponseError("Please type correct name of user");
            }
        }, error -> reposResponseListener.onResponseError("Something go wrong :("));

        // Add the request to the RequestQueue.
        queue.addToRequestQueue(arrayRequest);
    }

    // Callback for prevent thread error which may create Volley (api controller)
    // For bellow function
    public interface SingleRepoResponseListener{
        void onResponse(RepoCard repoCard);
        void onResponseError(String message);
    }


    //Function for getting from GITHUB API single repo data
    public static void getSingleRepoFromUserName(String username, String reponame, SingleRepoResponseListener singleRepoResponseListener){
        //Check if is text in input
        if(username.equals("") || reponame.equals("")){
            singleRepoResponseListener.onResponseError("Error");
            return;
        }
        // Create url for request
        String url = QUERY_URL_FOR_SINGLE_REPO + username + "/" + reponame;

        // Request a object response from the provided URL.
        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {

                RepoCard repoCard = new RepoCard(
                        response.getString("name"),
                        response.getString("visibility"),
                        response.getString("description"),
                        response.getString("languages_url"),
                        response.getInt("watchers"),
                        response.getInt("stargazers_count")
                );
                singleRepoResponseListener.onResponse(repoCard);


            } catch (JSONException e) {
                e.printStackTrace();
                singleRepoResponseListener.onResponseError("Error");
            }
        }, error -> singleRepoResponseListener.onResponseError("Something go wrong :("));

        // Add the request to the RequestQueue.
        queue.addToRequestQueue(objRequest);
    }


    // Callback for prevent thread error which may create Volley (api controller)
    // For bellow function
    public interface LangsResponseListener{
        void onResponse(ArrayList<String> langs, ArrayList<String> bytes);
        void onResponseError(String message);
    }

    //Function for getting from GITHUB API REPOSITORIES DATA
    public static void getLangsFromLink(String link, LangsResponseListener langsResponseListener){
        // Instantiate the RequestQueue.

        // Request a string response from the provided URL.
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, link, null, response -> {
            try {
                ArrayList<String> langs = new ArrayList<>();
                ArrayList<String> bytes = new ArrayList<>();
                Iterator<String> keys = response.keys();
                while (keys.hasNext()){
                    langs.add(keys.next());
                    Log.d("Key", langs.get(langs.size()-1));
                }
                for(int i = 0; i < langs.size(); i++){
                    bytes.add(response.getString(langs.get(i)));
                }
                langsResponseListener.onResponse(langs, bytes);


            } catch (Exception e) {
                e.printStackTrace();
                langsResponseListener.onResponseError("Please type correct name of user");
            }
        }, error -> langsResponseListener.onResponseError("Something go wrong :(" + link));

        // Add the request to the RequestQueue.
        queue.addToRequestQueue(objectRequest);
    }

}
