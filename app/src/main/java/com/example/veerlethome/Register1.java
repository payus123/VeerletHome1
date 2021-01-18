package com.example.veerlethome;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Register1 extends AppCompatActivity {
   private ImageView leftarrow;
    private Button next;
    private TextView Title;


    private TextInputLayout username;
    private TextInputLayout password;
    private TextInputLayout fullname;
    private TextInputLayout email;

    private String Username;
    private String Password;
    private String Fullname;
    private String Email;
    private String FirebasePassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        leftarrow=findViewById(R.id.leftarrow);
        next =findViewById(R.id.SignUpNext);
        Title =findViewById(R.id.CreateAcc);

        username =findViewById(R.id.UserName);
        password =findViewById(R.id.Password);
        fullname =findViewById(R.id.FullName);
        email =findViewById(R.id.Email);



    }

    public void CallNextSignUp(View view) {
//Collect details and put in JsonObject
        Username =username.getEditText().getText().toString();
        Password =password.getEditText().getText().toString();
        Fullname =fullname.getEditText().getText().toString();
        Email =email.getEditText().getText().toString();
        FirebasePassword=Password;
        JSONObject object = new JSONObject();

        try {object.put("id",10000);
            object.put("username", Username);
            object.put("password", Password);
            object.put("fullname", Fullname);
            object.put("email", Email);
            object.put("firebasePassword", FirebasePassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
////////////////////////////////////////////////////////////////////////////////////////////////////



       // RequestQ.q(this).add(new JsonObjectRequest("https://veerlethome.herokuapp.com/users/check",object,this::onCheckSuccess,this::onCheckError));

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://veerlethome.herokuapp.com/users/check",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              
            }
        });


    }




    private void onCheckSuccess(JSONObject object){
        Toast.makeText(this,"success",Toast.LENGTH_LONG).show();

        RequestQ.q(this).add(new JsonObjectRequest("https://veerlethome.herokuapp.com/users/save",object,this::onPostSuccess,this::onPostError));

    }
    private void onCheckError(VolleyError error){
        Log.e("App0", "[MainActivity] failed to post");


    }

private void onPostSuccess(JSONObject object){
    Toast.makeText(this,"success",Toast.LENGTH_LONG).show();

}
    private void onPostError(VolleyError error){
        Log.e("App0", "[MainActivity] failed to post");

        if (error == null) {
            Log.e("App1", "no error");
            Toast.makeText(this,"no error",Toast.LENGTH_LONG).show();
        }
        else if (error.networkResponse != null) {
            Log.e("App2", String.valueOf(error.networkResponse.statusCode));

            Toast.makeText(this,error.networkResponse.statusCode,Toast.LENGTH_LONG).show();

            if(error.networkResponse.data != null) {
                try {
                    Log.e("App", new String(error.networkResponse.data, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            Log.e("App3",error.getMessage() );
        }

    }

}