package com.codepath.apps.restclienttemplate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;


public class ComposeActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 20;
    private TextView sms_count;
    EditText etCompose;
    Tweet tweet;

    // TwitterClient client = new TwitterClient(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        sms_count = (TextView) findViewById(R.id.tvChar);
        etCompose = (EditText) findViewById(R.id.etCompose);

        final TextWatcher txwatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sms_count.setText(String.valueOf(140 - s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etCompose.addTextChangedListener(txwatcher);
    }

    public void onSubmit(View v) {
        TwitterApp.getRestClient().sendTweet(etCompose.getText().toString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    tweet = new Tweet();
                    tweet = Tweet.fromJSON(response);
                    Intent i = new Intent(ComposeActivity.this, TimelineActivity.class);
                    i.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));

                    setResult(RESULT_OK, i); // set result code and bundle data for response
                    finish(); // closes the activity, pass data to parent


                } catch (JSONException e) {
                    Log.e("Compose Activity", "Error forming new tweet", e);
                }
                // super.onSuccess(statusCode, headers, response);
            }
        });


    }
}
