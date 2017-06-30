package com.codepath.apps.restclienttemplate;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.InputStream;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

import static com.codepath.apps.restclienttemplate.R.id.tvUserName;
import static com.codepath.apps.restclienttemplate.TweetAdapter.context;

public class DetailsActivity extends AppCompatActivity {

    Tweet tweet;
    TextView tvUsername;
    TextView tvBody;
    TextView tvAt;
    TextView tvFavs;
    ImageView ivProfileImage;
    ImageButton favoBtn;
    // ImageView preImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvUsername = (TextView) findViewById(tvUserName);
        tvAt = (TextView) findViewById(R.id.tvAt);
        tvBody = (TextView) findViewById(R.id.tvBody);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvFavs = (TextView) findViewById(R.id.tvFavs);
        favoBtn = (ImageButton) findViewById(R.id.favoBtn);
        //preImage = (ImageView) findViewById(R.id.preImage);

        // unwrap the movie passed in via intent, using its simple name as a key
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        tvUsername.setText(tweet.user.name);
        tvBody.setText(tweet.body);
        tvFavs.setText(tweet.favoriteCount + "");
        tvAt.setText("@" + tweet.user.screenName);
        Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
        // Glide.with(context).load()

    }

    public void favTweet() {
        TwitterClient client = TwitterApp.getRestClient();
        if (!tweet.favorited) {
            client.favTweet(tweet.uid, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    favoBtn.setImageResource(R.drawable.ic_vector_heart);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }
        else if (tweet.favorited) {

        }
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}
