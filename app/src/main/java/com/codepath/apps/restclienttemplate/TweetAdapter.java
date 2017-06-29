package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by samerk on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    static private List<Tweet> mTweets;
    static Context context;

    // pass in tweet array into constructor
    public TweetAdapter(List<Tweet> tweets) {
        mTweets = tweets;
    }
    // for each row inflate layout and pass to viewholder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get data according to position
        Tweet tweet = mTweets.get(position);

        // populate views according to data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvTime.setText(TimeFormatter.getTimeDifference(tweet.createdAt));
        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // create viewholder class

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Override
        public void onClick(final View v) {
            // get position of item
            final int pos = getAdapterPosition();
            TwitterClient client = new TwitterClient(context);
            // make sure position is valid
            if (pos != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Tweet tweet = mTweets.get(pos);
                if (v.getId() == replyBtn.getId()) {
                    Toast.makeText(v.getContext(), "reply button clicked", Toast.LENGTH_LONG).show();
                }
                else if (v.getId() == rtBtn.getId()) {
                    Toast.makeText(v.getContext(), "retweet button clicked", Toast.LENGTH_LONG).show();
                }

                else if (v.getId() == favBtn.getId()) {

                    // check if tweet was favorited
                    if (!tweet.favorited) {

                        client.favTweet(tweet.uid, new JsonHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                try {
                                    Toast.makeText(v.getContext(), "favorite button clicked", Toast.LENGTH_SHORT).show();
                                    Tweet newTweet = Tweet.fromJSON(response);
                                    favBtn.setImageResource(R.drawable.ic_vector_heart);
//                                  mTweets.add(pos, newTweet);

                                } catch (JSONException e) {
                                    Log.e("Compose Activity", "Error forming new tweet", e);
                                }
                                // super.onSuccess(statusCode, headers, response);
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
                }
                else if (v.getId() == dmBtn.getId()) {
                    Toast.makeText(v.getContext(), "dm button clicked", Toast.LENGTH_LONG).show();
                }
                else {
                    // create intent for the new activity
                    Intent intent = new Intent(context, DetailsActivity.class);
                    // serialize the movie using parceler, use its short name as a key
                    intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                    // show the activity
                    context.startActivity(intent);
                }
            }
        }

        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvTime;
        public ImageButton replyBtn;
        public ImageButton rtBtn;
        public ImageButton favBtn;
        public ImageButton dmBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            // perform findViewById lookups
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            replyBtn = (ImageButton) itemView.findViewById(R.id.replyBtn);
            rtBtn = (ImageButton) itemView.findViewById(R.id.rtBtn);
            favBtn = (ImageButton) itemView.findViewById(R.id.favBtn);
            dmBtn = (ImageButton) itemView.findViewById(R.id.dmBtn);
            itemView.setOnClickListener(this);
            // add click listeners for each button
            replyBtn.setOnClickListener(this);
            rtBtn.setOnClickListener(this);
            favBtn.setOnClickListener(this);
            dmBtn.setOnClickListener(this);

        }


    }
}
