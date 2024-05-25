package com.satdroid.weather_app_demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FavCityAdapter extends RecyclerView.Adapter<FavCityAdapter.ViewHolder> {

    private ArrayList<favcitymodal> FavcitymodalArrayList;
     Context context;

    public FavCityAdapter(ArrayList<favcitymodal> favcitymodalArrayList, Context context) {
        FavcitymodalArrayList = favcitymodalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public FavCityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_city_rcv_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavCityAdapter.ViewHolder holder, int position) {
        favcitymodal favcitydatamodal=FavcitymodalArrayList.get(position);

        holder.citysequence.setText(""+(position+1));
        holder.cityname.setText(favcitydatamodal.getCity());
        DBhandler dBhandler=new DBhandler(context);
        holder.deletecity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dBhandler.deleteCity(holder.cityname.getText().toString());
                Toast.makeText(context,"City removed",Toast.LENGTH_SHORT).show();
                FavcitymodalArrayList.remove(position);
                notifyItemRemoved(position);

            }
        });
        holder.viewCityWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,MainActivity.class);
                intent.putExtra("CityName",holder.cityname.getText().toString());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return FavcitymodalArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView citysequence, cityname,deletecity,viewCityWeather;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            citysequence=itemView.findViewById(R.id.city_sequence_rcv);
            cityname=itemView.findViewById(R.id.city_name_rcv);
            deletecity=itemView.findViewById(R.id.delete_city_rcv);
            viewCityWeather=itemView.findViewById(R.id.view_city_rcv);
        }
    }

}
