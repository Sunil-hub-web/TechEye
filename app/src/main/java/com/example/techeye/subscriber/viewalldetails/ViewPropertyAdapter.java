package com.example.techeye.subscriber.viewalldetails;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techeye.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewPropertyAdapter extends RecyclerView.Adapter<ViewPropertyAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Property_ModelClass> listProperty;

    public ViewPropertyAdapter(Context context, ArrayList<Property_ModelClass> listProperty) {
        this.context = context;
        this.listProperty = listProperty;
    }

    @NonNull
    @NotNull
    @Override
    public ViewPropertyAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.property_details,parent,false);
        return new ViewPropertyAdapter.ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewPropertyAdapter.ViewHolder holder, int position) {

        Property_ModelClass prop = listProperty.get (position);

        holder.userid.setText(Html.fromHtml("<font color='#606060'><b>User Id :</b><br></font>\n"+prop.getId()));
        holder.email.setText(Html.fromHtml("<font color='#606060'><b>Email Id :</b><br></font>\n"+prop.getEmail()));
        holder.reason.setText(Html.fromHtml("<font color='#606060'><b>ReasonForThirdEye :</b><br></font>\n"+prop.getReason_for_third_eye()));
        holder.squarefeet.setText("Plot_size :\n"+prop.getPlot_size());
        holder.country_name.setText(Html.fromHtml("<font color='#606060'><b>Country Name :</b><br></font>\n"+prop.getCountry()));
        holder.statename.setText(Html.fromHtml("<font color='#606060'><b>State Name :</b><br></font>\n"+prop.getState()));
        holder.cityname.setText(Html.fromHtml("<font color='#606060'><b>City Name :</b><br></font>\n"+prop.getCity()));
        holder.postalcode.setText(Html.fromHtml("<font color='#606060'><b>Postal Code :</b><br></font>\n"+prop.getPincode()));
        holder.paymentid.setText(Html.fromHtml("<font color='#606060'><b>Payment id :</b><br></font>\n"+prop.getPaymentid ()));
        holder.paymentstatus.setText(Html.fromHtml("<font color='#606060'><b>PaymentStatus :</b><br></font>\n"+prop.getPaymentstatus ()));

        if(prop.getPlot_size().equals ("null")){

            holder.squarefeet.setText ("Carpet Area :\n" + prop.getCarpet_area ());
        }

    }

    @Override
    public int getItemCount() {
        return listProperty.size ();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView userid,email,reason,squarefeet,country_name,statename,cityname,postalcode,paymentid,paymentstatus;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super (itemView);

            userid = itemView.findViewById (R.id.userid);
            email = itemView.findViewById (R.id.email);
            reason = itemView.findViewById (R.id.reason);
            squarefeet = itemView.findViewById (R.id.squarefeet);
            country_name = itemView.findViewById (R.id.country_name);
            statename = itemView.findViewById (R.id.statename);
            cityname = itemView.findViewById (R.id.cityname);
            postalcode = itemView.findViewById (R.id.postalcode);
            paymentid = itemView.findViewById (R.id.paymentid);
            paymentstatus = itemView.findViewById (R.id.paymentstatus);
        }
    }
}
