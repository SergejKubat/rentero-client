package com.metropolitan.rentero_client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.metropolitan.rentero_client.R;
import com.metropolitan.rentero_client.model.Question;

import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.MyViewHolder> {

    private Context context;
    private List<Question> questionList;

    public FAQAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faq_item, parent, false);
        return new FAQAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQAdapter.MyViewHolder holder, int position) {
        Question question = questionList.get(position);

        holder.faqHeading.setText(question.getHeading());
        holder.faqQuestion.setText(question.getQuestion());
        holder.faqAnswer.setText(question.getAnswer());

    }

    @Override
    public int getItemCount() {
        if (questionList == null) {
            return 0;
        }
        return questionList.size();
    }

    public void setTasks(List<Question> itemList) {
        questionList = itemList;
        notifyDataSetChanged();
    }

    public List<Question> getTasks() {
        return questionList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView faqHeading, faqQuestion, faqAnswer;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            faqHeading = itemView.findViewById(R.id.faqHeading);
            faqQuestion = itemView.findViewById(R.id.faqQuestion);
            faqAnswer = itemView.findViewById(R.id.faqAnswer);

        }

    }

}