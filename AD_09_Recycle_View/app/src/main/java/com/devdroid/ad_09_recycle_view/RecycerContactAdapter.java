package com.devdroid.ad_09_recycle_view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder> {

    Context context;
    ArrayList<ContactModel> arrContacts;

    public RecyclerContactAdapter(Context context, ArrayList<ContactModel> arrContacts) {
        this.context = context;
        this.arrContacts = arrContacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactModel contact = arrContacts.get(position);
        holder.txtName.setText(contact.name);
        holder.txtNumber.setText(contact.number);
        holder.imgContact.setImageResource(contact.img);

        // Edit on click
        holder.itemView.setOnClickListener(v -> {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_edit_contact);

            EditText edtName = dialog.findViewById(R.id.edtName);
            EditText edtNumber = dialog.findViewById(R.id.edtNumber);
            Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

            edtName.setText(contact.name);
            edtNumber.setText(contact.number);

            btnUpdate.setOnClickListener(view -> {
                if (!edtName.getText().toString().isEmpty() && !edtNumber.getText().toString().isEmpty()) {
                    contact.name = edtName.getText().toString();
                    contact.number = edtNumber.getText().toString();
                    notifyItemChanged(position);
                    dialog.dismiss();
                }
            });

            dialog.show();
        });

        // Delete on long press
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Contact")
                    .setMessage("Are you sure you want to delete this contact?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        arrContacts.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, arrContacts.size());
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return arrContacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtNumber;
        ImageView imgContact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            imgContact = itemView.findViewById(R.id.imgContact);
        }
    }
}
