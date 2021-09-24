package com.example.techeye.subscriber.viewalldetails;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.techeye.R;
import com.example.techeye.subscriber.Confirm;
import com.example.techeye.subscriber.SendMessage;
import com.example.techeye.subscriber.geofence.DirectionMapForGeofence;
import com.razorpay.Checkout;


import java.util.ArrayList;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.Viewholder> {

  private Context context;
  private ArrayList<Property_ModelClass> listProperty;
  String functions;

    public PropertyAdapter(Context context, ArrayList<Property_ModelClass> listProperty) {
        this.context = context;
        this.listProperty = listProperty;
    }

    @NonNull

    @Override
    public Viewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.property_details,parent,false);
        return new Viewholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Viewholder holder, int position) {

        Property_ModelClass prop = listProperty.get (position);

        holder.userid.setText(Html.fromHtml("<font color='#606060'><b>User Id :</b><br></font>\n"+prop.getId()));
        holder.email.setText(Html.fromHtml("<font color='#606060'><b>Email Id :</b><br></font>\n"+prop.getEmail()));
        holder.reason.setText(Html.fromHtml("<font color='#606060'><b>ReasonForThirdEye :</b><br></font>\n"+prop.getReason_for_third_eye()));
        //holder.reason.setText(Html.fromHtml("<font color='#606060'><b>Plot Size :</b><br></font>\n"+prop.getPlot_size()));
        holder.squarefeet.setText("Plot_size :\n"+prop.getPlot_size());
        holder.country_name.setText(Html.fromHtml("<font color='#606060'><b>Country Name :</b><br></font>\n"+prop.getCountry()));
        holder.statename.setText(Html.fromHtml("<font color='#606060'><b>State Name :</b><br></font>\n"+prop.getState()));
        holder.cityname.setText(Html.fromHtml("<font color='#606060'><b>City Name :</b><br></font>\n"+prop.getCity()));
        holder.postalcode.setText(Html.fromHtml("<font color='#606060'><b>Postal Code :</b><br></font>\n"+prop.getPincode()));
        holder.paymentid.setText(Html.fromHtml("<font color='#606060'><b>Payment id :</b><br></font>\n"+prop.getPaymentid ()));
        holder.paymentstatus.setText(Html.fromHtml("<font color='#606060'><b>PaymentStatus :</b><br></font>\n"+prop.getPaymentstatus ()));

        functions = prop.getFunction ();
        Log.d ("func",functions );



        if(prop.getPlot_size().equals ("null")){

            holder.squarefeet.setText ("Carpet Area :\n" + prop.getCarpet_area ());
        }

        if(!prop.getPaymentstatus ().equals ("Pending")){

            holder.btn_RequestRaised.setBackgroundResource (R.drawable.btn_activite);
            holder.btn_RequestRaised.setText ("Property Active");
        }

        if(functions.equals ("raised")){
            holder.btn_RequestRaised.setText ("Request Already Raised !! Click to know status");
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(prop.getPaymentstatus ().equals ("Pending")){

                    final AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(context)
                            .setMessage("Pay and Enjoy Our Services")
                            .setPositiveButton("Payment", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();

                                    Intent intent = new Intent ( context,Confirm.class );
                                    intent.putExtra ("id",prop.getId ());
                                    intent.putExtra ("name",prop.getName ());
                                    intent.putExtra ("email",prop.getEmail ());
                                    intent.putExtra ("mobileNumber",prop.getMobileNumber ());
                                    intent.putExtra ("mobileNumber1",prop.getWhatsappno ());
                                    intent.putExtra ("Reason_for_third_eye",prop.getReason_for_third_eye ());
                                    intent.putExtra ("Plot_size",prop.getPlot_size ());
                                    intent.putExtra ("Country",prop.getCountry ());
                                    intent.putExtra ("State",prop.getState ());
                                    intent.putExtra ("City",prop.getCity ());
                                    intent.putExtra ("Pincode",prop.getPincode ());
                                    intent.putExtra ("Paymentid",prop.getPaymentid ());
                                    intent.putExtra ("Paymentstatus",prop.getPaymentstatus ());
                                    intent.putExtra ("AadharNumber",prop.getAadharNumber ());
                                    intent.putExtra ("PassportNumber",prop.getPassportNumber ());
                                    intent.putExtra ("BillingAddress1",prop.getBillingAddress1 ());
                                    intent.putExtra ("BillingAddress2",prop.getBillingAddress2 ());
                                    intent.putExtra ("BillingCountry",prop.getBillingCountry ());
                                    intent.putExtra ("BillingState",prop.getBillingState ());
                                    intent.putExtra ("BillingCity",prop.getBillingCity ());
                                    intent.putExtra ("BillingPincode",prop.getBillingPincode ());
                                    intent.putExtra ("Frequency",prop.getFrequency ());
                                    intent.putExtra ("Duration",prop.getDuration ());
                                    intent.putExtra ("Declaration",prop.getDeclaration ());
                                    intent.putExtra ("Address1",prop.getAddress1 ());
                                    intent.putExtra ("message1","Update");
                                    intent.putExtra ("amount",prop.getAmount ());
                                    view.getContext().startActivity(intent);
                                }
                            });
                    AlertDialog alert11 = alertDialog.create();
                    alert11.show();
                    Checkout.preload(context.getApplicationContext ( ));
                }else{
                    String userid = holder.userid.getText().toString();
                    userid = userid.replace ("User Id :","");

                    Intent intent = new Intent(context, DirectionMapForGeofence.class);
                    intent.putExtra("userid",userid);
                    intent.putExtra("country_name",holder.country_name.getText().toString());
                    intent.putExtra("statename",holder.statename.getText().toString());
                    intent.putExtra("cityname",holder.cityname.getText().toString());
                    intent.putExtra("postalcode",holder.postalcode.getText().toString());
                    view.getContext().startActivity(intent);

                }
            }
        });

        holder.btn_RequestRaised.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                functions = prop.getFunction ();

                if(prop.getPaymentstatus ().equals ("Pending")){

                    if(functions.equals ("null")){

                        Intent intent = new Intent ( context, SendMessage.class);
                        intent.putExtra ("id",prop.getId ());
                        v.getContext().startActivity(intent);

                    }else{

                        Intent intent = new Intent ( context, ViewRequestRaised.class);
                        intent.putExtra ("id",prop.getId ());
                        v.getContext().startActivity(intent);
                    }

                }else{

                    Intent intent = new Intent ( context, SendMessage.class);
                    intent.putExtra ("id",prop.getId ());
                    v.getContext().startActivity(intent);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return listProperty.size ();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView userid,email,reason,squarefeet,country_name,statename,cityname,postalcode,paymentid,paymentstatus;
        CardView card;
        Button btn_RequestRaised;
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
            paymentid = itemView.findViewById (R.id.paymentid);
            paymentstatus = itemView.findViewById (R.id.paymentstatus);
            card = itemView.findViewById (R.id.cardproperty);
            btn_RequestRaised = itemView.findViewById (R.id.raised);
        }
    }
}
