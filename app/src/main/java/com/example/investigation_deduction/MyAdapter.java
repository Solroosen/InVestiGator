package com.example.investigation_deduction;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Clue> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mCardView;
        public ViewHolder(CardView g) {
            super(g);
            mCardView = g;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Clue> myDataset) {
        mDataset = myDataset;
    }

    public void Update(ArrayList<Clue> clues) {
        mDataset = clues;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_recyclercard, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder((CardView) v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Clue clue = mDataset.get(position);

        ImageView iv_char = (ImageView)holder.mCardView.findViewById(R.id.r_imageChar);
        ImageView iv_loca = (ImageView)holder.mCardView.findViewById(R.id.r_imageLoca);
        ImageView iv_weap = (ImageView)holder.mCardView.findViewById(R.id.r_imageWeap);

        int[] i = clue.getDrawable();

        iv_char.setImageResource(i[0]);
        iv_char.setContentDescription(clue.name);
        iv_loca.setImageResource(i[1]);
        iv_loca.setContentDescription(clue.location);
        iv_weap.setImageResource(i[2]);
        iv_weap.setContentDescription(clue.weapon);

        if(clue.status.equals("innocent")){
            holder.mCardView.setBackgroundResource(R.drawable.innocentclue_background);
            //iv_char.setClickable(false);
        }
        else if(clue.status.equals("suspect")){
            holder.mCardView.setBackgroundResource(R.drawable.suspectclue_background);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

