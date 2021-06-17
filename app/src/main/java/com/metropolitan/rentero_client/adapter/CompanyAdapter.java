package com.metropolitan.rentero_client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.metropolitan.rentero_client.R;
import com.metropolitan.rentero_client.model.Company;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.MyViewHolder> {

    private Context context;
    private List<Company> companiesList;

    private CompanyAdapter.ItemClickListener itemClickListener;

    public CompanyAdapter(Context context, CompanyAdapter.ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.company_item, parent, false);
        return new CompanyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyAdapter.MyViewHolder holder, int position) {
        Company company = companiesList.get(position);

        holder.companyName.setText(company.getName());
        holder.companyAddress.setText(company.getAddress());
        holder.companyCity.setText(company.getCity());
        holder.companyPhoneNumber.setText(company.getPhoneNumber());
        holder.companyEmail.setText(company.getEmail());
        Picasso.get()
                .load(company.getImageUrl())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .resize(420, 360)
                .centerCrop()
                .into(holder.companyImage);

        holder.itemView.setOnClickListener(v -> {
            itemClickListener.onItemClick(company);
        });
    }

    @Override
    public int getItemCount() {
        if (companiesList == null) {
            return 0;
        }
        return companiesList.size();
    }

    public void setTasks(List<Company> itemList) {
        companiesList = itemList;
        notifyDataSetChanged();
    }

    public List<Company> getTasks() {
        return companiesList;
    }

    public interface ItemClickListener {
        void onItemClick(Company company);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView companyName, companyAddress, companyCity, companyPhoneNumber, companyEmail;
        ImageView companyImage;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            companyName = itemView.findViewById(R.id.companyName);
            companyAddress = itemView.findViewById(R.id.companyAddress);
            companyCity = itemView.findViewById(R.id.companyCity);
            companyPhoneNumber = itemView.findViewById(R.id.companyPhoneNumber);
            companyEmail = itemView.findViewById(R.id.companyEmail);

            companyImage = itemView.findViewById(R.id.companyImage);

        }

    }

}