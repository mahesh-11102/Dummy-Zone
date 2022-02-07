package com.example.dummyzone;



    import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DummyZoneFeedRVAdapter extends RecyclerView.Adapter<DummyZoneFeedRVAdapter.ViewHolder> {

    // arraylist for our facebook feeds.
    private ArrayList<DummyZoneFeedModal> dummyZoneFeedModalArrayList;
    private Context context;

    // creating a constructor for our adapter class.
    public DummyZoneFeedRVAdapter(ArrayList<DummyZoneFeedModal> dummyZoneFeedModalArrayList, Context context) {
        this.dummyZoneFeedModalArrayList = dummyZoneFeedModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout for item of recycler view item.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // getting data from array list and setting it to our modal class.
        DummyZoneFeedModal modal = dummyZoneFeedModalArrayList.get(position);

        // setting data to each view of recyclerview item.
        Picasso.get().load(modal.getAuthorImage()).into(holder.authorIV);
        holder.authorNameTV.setText(modal.getAuthorName());
        holder.timeTV.setText(modal.getPostDate());
        holder.descTV.setText(modal.getPostDescription());
        Picasso.get().load(modal.getPostIV()).into(holder.postIV);
        holder.likesTV.setText(modal.getPostLikes());
        holder.commentsTV.setText(modal.getPostComments());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return dummyZoneFeedModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views
        // of recycler view items.
        private CircleImageView authorIV;
        private TextView authorNameTV, timeTV, descTV;
        private ImageView postIV;
        private TextView likesTV, commentsTV;
        private LinearLayout shareLL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our variables
            shareLL = itemView.findViewById(R.id.idLLShare);
            authorIV = itemView.findViewById(R.id.idCVAuthor);
            authorNameTV = itemView.findViewById(R.id.idTVAuthorName);
            timeTV = itemView.findViewById(R.id.idTVTime);
            descTV = itemView.findViewById(R.id.idTVDescription);
            postIV = itemView.findViewById(R.id.idIVPost);
            likesTV = itemView.findViewById(R.id.idTVLikes);
            commentsTV = itemView.findViewById(R.id.idTVComments);
        }
    }
}


