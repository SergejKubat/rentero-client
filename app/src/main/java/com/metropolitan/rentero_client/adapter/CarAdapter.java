package com.metropolitan.rentero_client.adapter;

import com.metropolitan.rentero_client.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.metropolitan.rentero_client.model.Car;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder> {

    private Context context;
    private List<Car> carsList;

    private ItemClickListener itemClickListener;

    public CarAdapter(Context context, ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.car_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.MyViewHolder holder, int position) {
        holder.carBrandAndModel.setText(carsList.get(position).getBrand() + " " + carsList.get(position).getModel());
        holder.carDescription.setText(carsList.get(position).getDescription());
        holder.carCapacity.setText(String.valueOf(carsList.get(position).getCapacity()) + " putnika");
        holder.carNumberOfDoors.setText(String.valueOf(carsList.get(position).getDoors()) + " vrata");
        holder.carEngineSize.setText(String.valueOf(carsList.get(position).getEngineSize()) + " kubika");
        holder.carPrice.setText(String.format("%.2f", carsList.get(position).getPricePerDay()) + "€ / dan");
        Picasso.get()
                .load(carsList.get(position).getMainImageUrl())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .resize(420, 360)
                .centerCrop()
                .into(holder.carMainImage);

        holder.itemView.setOnClickListener(v -> {
            itemClickListener.onItemClick(carsList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        if (carsList == null) {
            return 0;
        }
        return carsList.size();
    }

    public void setTasks(List<Car> itemList) {
        carsList = itemList;
        notifyDataSetChanged();
    }

    public List<Car> getTasks() {
        return carsList;
    }

    public interface ItemClickListener {
        void onItemClick(Car car);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView carBrandAndModel, carDescription, carCapacity, carNumberOfDoors, carEngineSize, carPrice;
        ImageView carMainImage;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            carBrandAndModel = itemView.findViewById(R.id.carBrandAndModel);
            carDescription = itemView.findViewById(R.id.carDescription);
            carCapacity = itemView.findViewById(R.id.carCapacity);
            carNumberOfDoors = itemView.findViewById(R.id.carNumberOfDoors);
            carEngineSize = itemView.findViewById(R.id.carEngineSize);
            carPrice = itemView.findViewById(R.id.carPrice);

            carMainImage = itemView.findViewById(R.id.carMainImage);
        }

    }

}