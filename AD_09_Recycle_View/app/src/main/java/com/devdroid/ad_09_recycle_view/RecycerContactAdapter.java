package com.devdroid.ad_09_recycle_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycerContactAdapter extends RecyclerView.Adapter<RecycerContactAdapter.ViewHolder> {

    Context context;
    ArrayList<ContactModel> arrContacts;
    RecycerContactAdapter(Context context , ArrayList<ContactModel> arrContacts){
        this.context=context;
        this.arrContacts=arrContacts;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.contact_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgContext.setImageResource(arrContacts.get(position).img);
        holder.txtName.setText(arrContacts.get(position).name);
        holder.txtNumber.setText(arrContacts.get(position).name);

    }

    @Override
    public int getItemCount() {
        return arrContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView  txtName,txtNumber;
        ImageView imgContext;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            imgContext = itemView.findViewById(R.id.imgContact);

        }
    }
}
