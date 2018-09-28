package com.example.ktabe.beautifulbulldog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.RealmResults;

public class VotesAdapter extends RecyclerView.Adapter<VotesAdapter.VoteViewHolder> {

    private Context context;
    private RealmResults<Vote> votes;
    private RecyclerViewClickListener mListener;

    public VotesAdapter(Context context, RealmResults<Vote> dataSet) {
        this.context = context;
        this.votes = dataSet;
    }


    public static class VoteViewHolder extends RecyclerView.ViewHolder {
        public TextView voteDogName;
        public TextView voteUsername;
        public TextView voteRankView;
        public ImageView voteImageView;

        //private RecyclerViewClickListener mListener;
        public VoteViewHolder(View v) {
            super(v);
            voteDogName = v.findViewById(R.id.vote_dog_name);
            voteUsername = v.findViewById(R.id.vote_username);
            voteRankView = v.findViewById(R.id.vote_rank_view);
            voteImageView = v.findViewById(R.id.vote_image_view);
        }
    }

    @Override
    public VotesAdapter.VoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vote_cell, viewGroup, false);
        VoteViewHolder vh = new VoteViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(VotesAdapter.VoteViewHolder voteHolder, int pos) {
        voteHolder.voteDogName.setText(votes.get(pos).getBulldog().getName());
        voteHolder.voteUsername.setText(votes.get(pos).getOwner().getUsername());
        voteHolder.voteRankView.setText(String.valueOf(votes.get(pos).getRating()));

        if (votes.get(pos).getBulldog().getImage() != null){
            Bitmap bm = BitmapFactory.decodeByteArray(votes.get(pos).getBulldog().getImage(),0,votes.get(pos).getBulldog().getImage().length);
            voteHolder.voteImageView.setImageBitmap(bm);
        }
    }

    @Override
    public int getItemCount() {
        return votes.size();
    }
}
