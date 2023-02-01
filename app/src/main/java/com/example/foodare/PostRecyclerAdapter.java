package com.example.foodare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodare.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;


class PostViewHolder extends RecyclerView.ViewHolder {
    TextView username;
    TextView restaurantName;
    TextView mealName;
    TextView mealRate;
    List<Post> data;
    Button editBtn;
    ImageView mealImage;
    String url = "";

    public PostViewHolder(@NonNull View itemView, PostRecyclerAdapter.OnItemClickListener listener, List<Post> data) {
        super(itemView);
        this.data = data;
        username = itemView.findViewById(R.id.post_list_row_username_tv);
        restaurantName = itemView.findViewById(R.id.post_list_row_restaurant_tv);
        mealName = itemView.findViewById(R.id.post_list_row_meal_tv);
        mealRate = itemView.findViewById(R.id.post_list_row_rate_tv);
        editBtn = itemView.findViewById(R.id.post_list_row_edit_btn);
        mealImage = itemView.findViewById(R.id.postlistrow_avatar_img);

        if (editBtn != null) {
            editBtn.setOnClickListener(view -> {
                Post clickedPost = data.get(getAdapterPosition());
                MyPostListFragmentDirections.ActionMyPostListFragmentToEditPostFragment action =
                        MyPostListFragmentDirections.actionMyPostListFragmentToEditPostFragment(clickedPost.getRestaurant(),
                                clickedPost.getMeal(), clickedPost.getRate(), clickedPost.getDescription(), clickedPost.getImageUrl(), clickedPost.getId());
                Navigation.findNavController(view).navigate((NavDirections) action);
            });
        }

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
        url = post.getImageUrl();
        if (!Objects.equals(url, "") && url != null) {
            Picasso.get().load(url).placeholder(R.drawable.hamburger).into(mealImage);
        } else {
            mealImage.setImageResource(R.drawable.hamburger);
        }
    }
}

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostViewHolder> {
    private final int rowLayout;
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

    public PostRecyclerAdapter(LayoutInflater inflater, List<Post> data, int rowLayout) {
        this.inflater = inflater;
        this.data = data;
        this.rowLayout = rowLayout;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(rowLayout, parent, false);
        return new PostViewHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = data.get(position);
        holder.bind(post, position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

}

