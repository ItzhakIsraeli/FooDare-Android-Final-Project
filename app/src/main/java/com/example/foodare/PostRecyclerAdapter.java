package com.example.foodare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodare.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;


class PostViewHolder extends RecyclerView.ViewHolder {
    TextView username;
    TextView restaurantName;
    TextView mealName;
    TextView mealRate;
    List<Post> data;

    public PostViewHolder(@NonNull View itemView, PostRecyclerAdapter.OnItemClickListener listener, List<Post> data) {
        super(itemView);
        this.data = data;
        username = itemView.findViewById(R.id.post_list_row_username_tv);
        restaurantName = itemView.findViewById(R.id.post_list_row_restaurant_tv);
        mealName = itemView.findViewById(R.id.post_list_row_meal_tv);
        mealRate = itemView.findViewById(R.id.post_list_row_rate_tv);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }

    public void bind(Post post, int pos) {
        username.setText(post.username);
        restaurantName.setText(post.restaurant);
        mealName.setText(post.meal);
        mealRate.setText(post.rate);
//        if (post.getAvatarUrl() != "") {
//            Picasso.get().load(post.getAvatarUrl()).placeholder(R.drawable.avatar).into(avatarImage);
//        } else {
//            avatarImage.setImageResource(R.drawable.avatar);
//        }
    }
}

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostViewHolder> {
    OnItemClickListener listener;

    public static interface OnItemClickListener {
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Post> data;

    public void setData(List<Post> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public PostRecyclerAdapter(LayoutInflater inflater, List<Post> data) {
        this.inflater = inflater;
        this.data = data;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_list_row, parent, false);
        return new PostViewHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post st = data.get(position);
        holder.bind(st, position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

}

