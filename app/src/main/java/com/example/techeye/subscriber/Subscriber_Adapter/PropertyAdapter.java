package com.example.techeye.subscriber.Subscriber_Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.techeye.R;
import com.example.techeye.subscriber.Subsrciber_ModelClass.Property_ModelClass;
import com.example.techeye.subscriber.geofence.DirectionMapForGeofence;


import java.util.ArrayList;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.Viewholder> {

  private Context context;
  private ArrayList<Property_ModelClass> listProperty;

    public PropertyAdapter(Context context, ArrayList<Property_ModelClass> listProperty) {
        this.context = context;
        this.listProperty = listProperty;
    }

    @NonNull

    @Override
    public PropertyAdapter.Viewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.property_details,parent,false);
        return new Viewholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull  PropertyAdapter.Viewholder holder, int position) {

        Property_ModelClass prop = listProperty.get (position);

        holder.userid.setText(prop.getId());
        holder.email.setText(prop.getEmail());
        holder.reason.setText(prop.getReason_for_third_eye());
        holder.squarefeet.setText(prop.getPlot_size());
        holder.country_name.setText(prop.getCountry());
        holder.statename.setText(prop.getState());
        holder.cityname.setText(prop.getCity());
        holder.postalcode.setText(prop.getPincode());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DirectionMapForGeofence.class);
                intent.putExtra("userid",holder.userid.getText().toString());
                intent.putExtra("country_name",holder.country_name.getText().toString());
                intent.putExtra("statename",holder.statename.getText().toString());
                intent.putExtra("cityname",holder.cityname.getText().toString());
                intent.putExtra("postalcode",holder.postalcode.getText().toString());
                context.startActivity (intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return listProperty.size ();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView userid,email,reason,squarefeet,country_name,statename,cityname,postalcode;
        CardView card;
        public Viewholder(@NonNull  View itemView) {
            super (itemView);

            userid = itemView.findViewById (R.id.userid);
            email = itemView.findViewById (R.id.email);
            reason = itemView.findViewById (R.id.reason);
            squarefeet = itemView.findViewById (R.id.squarefeet);
            country_name = itemView.findViewById (R.id.country_name);
            statename = itemView.findViewById (R.id.statename);
            cityname = itemView.findViewById (R.id.cityname);
            postalcode = itemView.findViewById (R.id.postalcode);
            card = itemView.findViewById (R.id.cardproperty);
        }
    }
}
