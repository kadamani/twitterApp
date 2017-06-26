package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

/**
 * Created by samerk on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    private List<Tweet> mTweets;
    Context context;

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
    // bind the values based on position of element

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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);

            // perform findViewById lookups
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        }
    }
}
