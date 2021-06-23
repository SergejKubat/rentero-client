package com.metropolitan.rentero_client.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.metropolitan.rentero_client.R;
import com.metropolitan.rentero_client.model.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.MyViewHolder> {

    private Context context;
    private List<Reservation> reservationsList;

    public ReservationAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservation_item, parent, false);
        return new ReservationAdapter.MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.MyViewHolder holder, int position) {
        Reservation reservation = reservationsList.get(position);

        if (reservation.isEnabled()) {
            holder.reservationStatusIcon.setImageResource(R.drawable.ic_baseline_check_circle_24);

            int color = Color.parseColor("#28a745");
            holder.reservationStatusIcon.setColorFilter(color);

            holder.reservationStatusText.setText("Odobreno");
            holder.reservationStatusText.setTextColor(color);
        } else {
            holder.reservationStatusIcon.setImageResource(R.drawable.ic_baseline_access_time_24);

            int color = Color.parseColor("#17a2b8");
            holder.reservationStatusIcon.setColorFilter(color);

            holder.reservationStatusText.setText("Na čekanju");
            holder.reservationStatusText.setTextColor(color);
        }

        LocalDate startDate = LocalDate.parse(reservation.getStartDate());
        LocalDate endDate = LocalDate.parse(reservation.getEndDate());

        holder.reservationStartDate.setText(startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        holder.reservationEndDate.setText(endDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        holder.carBrandAndModel.setText(reservation.getCarBrandAndModel());
        holder.companyAddress.setText(reservation.getCompanyAddress());
        holder.reservationPrice.setText(String.format("%.2f", reservation.getPrice()) + "€");
    }

    @Override
    public int getItemCount() {
        if (reservationsList == null) {
            return 0;
        }
        return reservationsList.size();
    }

    public void setTasks(List<Reservation> itemList) {
        reservationsList = itemList;
        notifyDataSetChanged();
    }

    public List<Reservation> getTask() {
        return reservationsList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView reservationStatusIcon;
        TextView reservationStatusText, reservationStartDate, reservationEndDate,
                carBrandAndModel, companyAddress, reservationPrice;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            reservationStatusIcon = itemView.findViewById(R.id.reservationStatusIcon);

            reservationStatusText = itemView.findViewById(R.id.reservationStatusText);
            reservationStartDate = itemView.findViewById(R.id.reservationStartDate);
            reservationEndDate = itemView.findViewById(R.id.reservationEndDate);
            carBrandAndModel = itemView.findViewById(R.id.carBrandAndModel);
            companyAddress = itemView.findViewById(R.id.companyAddress);
            reservationPrice = itemView.findViewById(R.id.reservationPrice);

        }

    }

}
