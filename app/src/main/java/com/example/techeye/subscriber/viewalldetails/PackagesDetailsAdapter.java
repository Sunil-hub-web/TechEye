package com.example.techeye.subscriber.viewalldetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techeye.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PackagesDetailsAdapter extends RecyclerView.Adapter<PackagesDetailsAdapter.ViewHolder> {

    Context context;
    ArrayList<PackagesDetails_ModelClass> packag;
    int duration,default_price,weekly,fotnightly,monthly;
    double total;

    public PackagesDetailsAdapter(Context context, ArrayList<PackagesDetails_ModelClass> packag) {
        this.context = context;
        this.packag = packag;
    }

    @NonNull
    @NotNull
    @Override
    public PackagesDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.view_packages,parent,false);
        return new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PackagesDetailsAdapter.ViewHolder holder, int position) {

        PackagesDetails_ModelClass packages = packag.get (position);

        holder.userId.setText (packages.getUserid ());
        holder.paymentId.setText (packages.getPaymentid ());
        holder.emailid.setText (packages.getEmailid ());
        holder.duration.setText (packages.getDuration ());
        holder.frequency.setText (packages.getFrequency ());
        holder.statues.setText (packages.getStatues ());
        holder.price.setText (packages.getTotal ());

        String dur = packages.getDuration ();
        String serv = packages.getFrequency ();

      /*  if (dur.equals ("6 Months")){

            duration = 6;
            default_price = 7;
        }else if (dur.equals ("12 Months")){

            duration = 12;
            default_price = 6;
        }else {

            duration=24;
            default_price=5;
        }

        if (serv.equals ("Onace In a Week")){

            weekly = (duration*default_price*4)+5;
            total = Double.parseDouble (String.valueOf (weekly))*1.18;

        }else if (serv.equals ("Once in Every 15 Days")){

            fotnightly = (duration*default_price*2)+5;
            total = Double.parseDouble (String.valueOf (fotnightly))*1.18;
        }else {
            monthly = (duration*default_price*1)+5;
            total = Double.parseDouble (String.valueOf (monthly))*1.18;
        }

        double total_price = Double.parseDouble (String.valueOf (total)) * 73;
        String pricevalue = String.valueOf (total_price);
        //float IntValue = (float) Float.parseFloat (price);

         holder.price.setText (pricevalue);*/
    }

    @Override
    public int getItemCount() {
        return packag.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userId,paymentId,emailid,frequency,duration,statues,price;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super (itemView);

            userId = itemView.findViewById (R.id.userid);
            paymentId = itemView.findViewById (R.id.paymentid);
            emailid = itemView.findViewById (R.id.txtemailid);
            frequency = itemView.findViewById (R.id.textfrequency);
            duration = itemView.findViewById (R.id.textduration);
            price = itemView.findViewById (R.id.textprice);
            statues = itemView.findViewById (R.id.statues);
        }
    }
}
