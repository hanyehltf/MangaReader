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

public class LoginDB {


    private Context context;

    public LoginDB(Context context) {
        this.context = context;
    }


    public void login(String username, String password, final login login) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", username);
            jsonObject.put("password", password);


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://127.0.0.1/login.php", jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        boolean status = response.getBoolean("error");
                        JSONObject userJson = response.getJSONObject("user");

                        User user = new User();

                        if (status != true) {
                            user.setEmail(userJson.getString("email"));
                            user.setId(userJson.getString("id"));
                            user.setPassword(userJson.getString("password"));
                            user.setImage(userJson.getString("image"));
                        }
                        login.onReceived(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });




            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(context).add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public interface login {

        void onReceived(User user);
    }
}
