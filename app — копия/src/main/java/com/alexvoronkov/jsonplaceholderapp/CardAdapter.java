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

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.PostsViewHolder> {

    private List<Card> mList;
//    private Context context;
    private Listener listener;

    //интерфейс слушателя для обработки нажатий
    public interface Listener {
        void onClick(int t, int position);
    }

    //слушатель для фрагментов и активностей
    public void setListener(Listener listener){
        this.listener = listener;
    }

    public CardAdapter(/*Context context, */ArrayList<Card> list){
//        this.context = context;
        this.mList = list;
    }

//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }

    @Override
    public /*RecyclerView.ViewHolder*/PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_posts, parent, false);
        return new PostsViewHolder(v);

        /*switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_posts, parent, false);
                return new PostsViewHolder(v);
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_photo, parent, false);
                return new PhotoViewHolder(v);
        }
        return null;*/
    }

    @Override
    public void onBindViewHolder(/*final RecyclerView.ViewHolder holder*/final CardAdapter.PostsViewHolder holder, final int position) {

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
        /*if (card != null){
            switch (holder.getItemViewType()){
                case 0:
                    ((PostsViewHolder)holder).cardTitle.setText(card.title);
                    ((PostsViewHolder)holder).cardContent.setText(card.content);
                    ((PostsViewHolder)holder).button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int t = Integer.parseInt(String.valueOf(((PostsViewHolder)holder).cardEdit.getText())) - 1;
                            if (listener != null) listener.onClick(t);
                            ((PostsViewHolder)holder).cardEdit.setText("");
                        }
                    });
                    break;
                case 1:
                    ((PhotoViewHolder)holder).cardTitle.setText(card.title);
                    Picasso.with(context)  //Пикассо
                            .load(card.content)
                            .resize(300, 300)
                            .into(((PhotoViewHolder) holder).cardPhoto);
                    break;
            }
        }*/
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class PostsViewHolder extends RecyclerView.ViewHolder{

        private TextView cardTitle;
        private TextView cardContent;
        private Button button;
        private EditText cardEdit;

        public PostsViewHolder(View v) {
            super(v);
            cardEdit = (EditText)v.findViewById(R.id.edit);
            button = (Button)v.findViewById(R.id.confirm);
            cardTitle = (TextView)v.findViewById(R.id.title);
            cardContent = (TextView) v.findViewById(R.id.content);
        }
    }

    /*public static class PhotoViewHolder extends RecyclerView.ViewHolder{

        private TextView cardTitle;
        private ImageView cardPhoto;

        public PhotoViewHolder(View v) {
            super(v);
            cardTitle = (TextView)v.findViewById(R.id.title);
            cardPhoto = (ImageView) v.findViewById(R.id.photo);
        }
    }*/

    public void editPosts(int position, Card value){
        mList.set(position, value);
        notifyItemChanged(position);
    }
}
