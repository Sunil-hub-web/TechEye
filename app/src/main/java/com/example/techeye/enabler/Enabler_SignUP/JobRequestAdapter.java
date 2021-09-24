package com.example.techeye.enabler.Enabler_SignUP;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techeye.R;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class JobRequestAdapter extends RecyclerView.Adapter<JobRequestAdapter.ViewHolder> {

    Context context;
    ArrayList<JobRequest_ModelClass> job;

    public JobRequestAdapter(Context context, ArrayList<JobRequest_ModelClass> job) {
        this.context = context;
        this.job = job;
    }

    @NonNull
    @NotNull
    @Override
    public JobRequestAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (parent.getContext ( )).inflate (R.layout.jobrequest,parent,false);
        return new ViewHolder (view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull @NotNull JobRequestAdapter.ViewHolder holder, int position) {

        JobRequest_ModelClass jobreq = job.get (position);

        holder.text_PropertyId.setText (Html.fromHtml("<font color='#606060'><b>Property Id :</b><br></font>\n"+jobreq.getPropertyId ( )));
        holder.text_EnablerId.setText (Html.fromHtml("<font color='#606060'><b>Enabler Id :</b><br></font>\n"+jobreq.getEnablerId ( )));
        holder.text_AssignDate.setText (Html.fromHtml("<font color='#606060'><b>Assign Date :</b><br></font>\n"+jobreq.getAssignDate ( )));
        holder.text_StartDate.setText (Html.fromHtml("<font color='#606060'><b>Start Date :</b><br></font>\n"+jobreq.getStartDate ( )));
        holder.text_EndDate.setText (Html.fromHtml("<font color='#606060'><b>End Date :</b><br></font>\n"+jobreq.getEndDate ( )));
        holder.text_Statues.setText (Html.fromHtml("<font color='#606060'><b>Statues :</b><br></font>\n"+jobreq.getStatues ( )));
        holder.text_JobId.setText (Html.fromHtml("<font color='#606060'><b>Job id :</b><br></font>\n"+jobreq.getId ( )));

        holder.btn_Accept.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( context,ShowPropertyDetails.class );
                intent.putExtra ("propertyid",jobreq.getPropertyId ( ));
                intent.putExtra ("id",jobreq.getId ( ));
                context.startActivity (intent );
            }
        });
    }

    @Override
    public int getItemCount() {
        return job.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_PropertyId,text_EnablerId,text_AssignDate,text_StartDate,text_EndDate,text_Statues,text_JobId;
        Button btn_Accept;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super (itemView);

            text_PropertyId = itemView.findViewById (R.id.propertyid);
            text_EnablerId = itemView.findViewById (R.id.enablerid);
            text_AssignDate = itemView.findViewById (R.id.assigndate);
            text_StartDate = itemView.findViewById (R.id.startdate);
            text_EndDate = itemView.findViewById (R.id.enddate);
            text_Statues = itemView.findViewById (R.id.status);
            text_JobId = itemView.findViewById (R.id.jobid);
            btn_Accept = itemView.findViewById (R.id.accept);

        }
    }
}
