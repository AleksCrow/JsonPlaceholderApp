package com.alexvoronkov.jsonplaceholderapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class CardAdapter extends RecyclerView.Adapter<CardAdapter.PostsViewHolder> {

    private List<Card> mList;
    private Listener listener;

    interface Listener {
        void onClick(int t, int position);
    }

    void setListener(Listener listener){
        this.listener = listener;
    }

    CardAdapter(ArrayList<Card> list){
        this.mList = list;
    }

    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_posts, parent, false);
        return new PostsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CardAdapter.PostsViewHolder holder, final int position) {

        final Card card = mList.get(position);
        holder.cardTitle.setText(card.title);
        holder.cardContent.setText(card.content);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int t = Integer.parseInt(String.valueOf(holder.cardEdit.getText())) - 1;
                if (listener != null) listener.onClick(t, position);
                holder.cardEdit.setText("");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class PostsViewHolder extends RecyclerView.ViewHolder{

        private TextView cardTitle;
        private TextView cardContent;
        private Button button;
        private EditText cardEdit;

        PostsViewHolder(View v) {
            super(v);
            cardEdit = (EditText)v.findViewById(R.id.edit);
            button = (Button)v.findViewById(R.id.confirm);
            cardTitle = (TextView)v.findViewById(R.id.title);
            cardContent = (TextView) v.findViewById(R.id.content);
        }
    }

    void editPosts(int position, Card value){
        mList.set(position, value);
        notifyItemChanged(position);
    }
}
