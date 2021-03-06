package com.metropolitan.rentero_client.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.metropolitan.rentero_client.R;
import com.metropolitan.rentero_client.model.Review;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private Context context;
    private List<Review> reviewsList;

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        return new ReviewAdapter.MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.MyViewHolder holder, int position) {
        Review review = reviewsList.get(position);

        Picasso.get()
                .load(review.getCustomerAvatar())
                .placeholder(R.drawable.user)
                .resize(64, 64)
                .centerCrop()
                .into(holder.customerAvatar);
        holder.customerName.setText(review.getCustomerName());

        LocalDate dateCreated = LocalDate.parse(review.getDateCreated());

        holder.reviewDate.setText(dateCreated.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        holder.reviewComment.setText(review.getComment());
        holder.reviewRatingBar.setRating(review.getMark());
    }

    @Override
    public int getItemCount() {
        if (reviewsList == null) {
            return 0;
        }
        return reviewsList.size();
    }

    public void setTasks(List<Review> itemList) {
        reviewsList = itemList;
        notifyDataSetChanged();
    }

    public List<Review> getTasks() {
        return reviewsList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView customerAvatar;
        TextView customerName, reviewDate, reviewComment;
        RatingBar reviewRatingBar;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            customerAvatar = itemView.findViewById(R.id.customerAvatar);

            customerName = itemView.findViewById(R.id.customerName);
            reviewDate = itemView.findViewById(R.id.reviewDate);
            reviewComment = itemView.findViewById(R.id.reviewComment);

            reviewRatingBar = itemView.findViewById(R.id.reviewRatingBar);

        }

    }

}