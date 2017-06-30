package com.codepath.apps.restclienttemplate;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.io.InputStream;
import java.net.URL;

import static com.codepath.apps.restclienttemplate.R.id.tvUserName;
import static com.codepath.apps.restclienttemplate.TweetAdapter.context;

public class DetailsActivity extends AppCompatActivity {

    Tweet tweet;
    TextView tvUsername;
    TextView tvBody;
    TextView tvAt;
    ImageView ivProfileImage;
    ImageView preImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvUsername = (TextView) findViewById(tvUserName);
        tvAt = (TextView) findViewById(R.id.tvAt);
        tvBody = (TextView) findViewById(R.id.tvBody);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        preImage = (ImageView) findViewById(R.id.preImage);

        // unwrap the movie passed in via intent, using its simple name as a key
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        tvUsername.setText(tweet.user.name);
        tvBody.setText(tweet.body);
        tvAt.setText("@" + tweet.user.screenName);
        Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
        // Glide.with(context).load()

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
