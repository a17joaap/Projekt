package com.example.jocke.projekt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Jocke on 10-May-18.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Picture> mPictureList;

    public Adapter(Context context, ArrayList<Picture> pictureList) {
        mContext = context;
        mPictureList = pictureList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.picture, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picture currentPicture = mPictureList.get(position);

        String imageUrl = currentPicture.getUrl();
        String creatorName = currentPicture.getCreator();
        int likes = currentPicture.getLikes();
        int downloads = currentPicture.getDownloads();
        int views = currentPicture.getViews();

        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.imageView);
        holder.creatorTextView.setText(creatorName);
        holder.likesTextView.setText("Likes: "+likes);
        holder.downloadsTextView.setText("Downloads: "+downloads);
        holder.viewsTextView.setText("Views: "+views);
    }

    @Override
    public int getItemCount() {
        return mPictureList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView creatorTextView;
        public TextView likesTextView;
        public TextView viewsTextView;
        public TextView downloadsTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            creatorTextView = itemView.findViewById(R.id.creatorTextView);
            likesTextView = itemView.findViewById(R.id.likesTextView);
            viewsTextView = itemView.findViewById(R.id.viewsTextView);
            downloadsTextView = itemView.findViewById(R.id.downloadsTextView);
        }
    }
}
