package com.example.techeye.subscriber.viewalldetails;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.techeye.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.ViewHolder> {

    Context context;
    ArrayList<UserDetails_ModelClass> user;

    public UserDetailsAdapter(Context context, ArrayList<UserDetails_ModelClass> user) {
        this.context = context;
        this.user = user;
    }

    @NonNull
    @NotNull
    @Override
    public UserDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.activity_account_details,parent,false);
        return new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserDetailsAdapter.ViewHolder holder, int position) {

        UserDetails_ModelClass userdetails = user.get (position);

        holder.first_name.setText (userdetails.getName ());
        holder.mobile_No.setText (userdetails.getMobileNumber ());
        holder.Email_id.setText (userdetails.getEmailId ());
        holder.text_Country.setText (userdetails.getCountry ());
        holder.edit_state.setText (userdetails.getState ());
        holder.edit_city.setText (userdetails.getCity ());
        holder.edit_pinCode.setText (userdetails.getPincode ());
        holder.edit_aadharCard.setText (userdetails.getAadharNumber ());
        holder.edit_passportDl.setText (userdetails.getPassportNumber ());
        holder.billing_Address1.setText (userdetails.getBillingAddress1 ());
        holder.billing_Address2.setText (userdetails.getBillingAddress2 ());
        holder.billing_Country.setText (userdetails.getBillingCountry ());
        holder.billing_State.setText (userdetails.getBillingState ());
        holder.billing_City.setText (userdetails.getBillingCity ());
        holder.billing_Pincode.setText (userdetails.getBillingPincode ());


        String passport = userdetails.getPassportImage ();
        String aadhar = userdetails.getAadharImage ();

        String idproof_Image = aadhar.split(",")[1];

        byte[] decodedString = Base64.decode(idproof_Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imageView1.setImageBitmap (decodedByte);

        String idproof_Image1 = passport.split(",")[1];

        byte[] decodedString1 = Base64.decode(idproof_Image1, Base64.DEFAULT);
        Bitmap decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
        holder.imageView2.setImageBitmap (decodedByte1);



        //Glide.with(context).load("https://rentopool.com/Thirdeye/"+aadhar).into(holder.imageView1);
        //Glide.with(context).load("https://rentopool.com/Thirdeye/"+passport).into(holder.imageView2);



    }

    @Override
    public int getItemCount() {
        return user.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView first_name, Email_id, mobile_No,edit_state,edit_city, edit_aadharCard,
                edit_passportDl, edit_pinCode,text_Country,billing_Address1,billing_Address2,billing_Country,billing_State,billing_City,billing_Pincode;
        ImageView imageView1,imageView2;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super (itemView);

            first_name = itemView.findViewById(R.id.fname);
            Email_id= itemView.findViewById(R.id.emailid);
            mobile_No = itemView.findViewById(R.id.mobileno);
            edit_aadharCard = itemView.findViewById(R.id.aadharcard);
            edit_state = itemView.findViewById(R.id.state);
            edit_city = itemView.findViewById(R.id.city);
            edit_passportDl = itemView.findViewById(R.id.passportdl);
            edit_pinCode = itemView.findViewById(R.id.pincode);
            imageView1 = itemView.findViewById(R.id.imgPreview);
            imageView2 = itemView.findViewById(R.id.imgPreview1);
            text_Country = itemView.findViewById (R.id.country);
            billing_Address1 = itemView.findViewById (R.id.billingAddress1);
            billing_Address2 = itemView.findViewById (R.id.billingAddress2);
            billing_Country = itemView.findViewById (R.id.billingCountry);
            billing_State = itemView.findViewById (R.id.billingState);
            billing_City = itemView.findViewById (R.id.billingcity);
            billing_Pincode = itemView.findViewById (R.id.billingPincode);

        }
    }
}
