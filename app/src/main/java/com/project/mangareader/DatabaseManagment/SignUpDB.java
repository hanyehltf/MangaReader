package com.project.mangareader.DatabaseManagment;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpDB {


    private Context context;


    public SignUpDB(Context context) {


        this.context = context;

    }


    public void addDataToDatabase(User user, final signUp signUp) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", user.getEmail());
            jsonObject.put("id", user.getId());
            jsonObject.put("password", user.getPassword());
            jsonObject.put("image", user.getImage());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://localhost/signup.php", jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    try {
                        int status = response.getInt("response");
                        signUp.onReceived(status);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    signUp.onReceived(0);
                }
            });


            Volley.newRequestQueue(context).add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }





    public interface signUp {
        void onReceived(int status);


    }




}
