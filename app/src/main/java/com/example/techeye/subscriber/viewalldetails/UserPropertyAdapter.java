package com.example.techeye.subscriber.viewalldetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.techeye.R;
import com.example.techeye.subscriber.Url.URLS;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserPropertyAdapter extends RecyclerView.Adapter<UserPropertyAdapter.Viewholder> {

    Context context;
    ArrayList<UserProperty_ModelClass> userPropertyArrayList ;
    private List<UserProperty_ModelClass> contactListFiltered;
    private ContactsAdapterListener listener;

    public UserPropertyAdapter(Context context, ArrayList<UserProperty_ModelClass> userPropertyArrayList,
                               ContactsAdapterListener listener) {
        this.context = context;
        this.userPropertyArrayList = userPropertyArrayList;
        this.listener = listener;
        this.contactListFiltered = userPropertyArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public UserPropertyAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.userproperty_details,parent,false);
        return new Viewholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserPropertyAdapter.Viewholder holder, int position) {

        UserProperty_ModelClass userProperty = userPropertyArrayList.get (position);

        String address = userProperty.getUserAddress ();
        address = address.replace ("Address :","");
        holder.userId.setText (userProperty.getUserId ());
        holder.propertyid.setText (userProperty.getPropertyId ());
        holder.address.setText (address);
        holder.coordinetes.setText (userProperty.getCoordinetes ());


    }

    @Override
    public int getItemCount() {
        return userPropertyArrayList.size ();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView userId,propertyid,address,coordinetes;
        Button btn_delete;

        public Viewholder(@NonNull @NotNull View itemView) {
            super (itemView);

            userId = itemView.findViewById (R.id.userid);
            propertyid = itemView.findViewById (R.id.propertyid);
            address = itemView.findViewById (R.id.address);
            coordinetes = itemView.findViewById (R.id.coordinetes);
            btn_delete = itemView.findViewById (R.id.delete);

            btn_delete.setVisibility (View.GONE);

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface ContactsAdapterListener {
        void onContactSelected(UserProperty_ModelClass userProperty_modelClass);
    }
}
