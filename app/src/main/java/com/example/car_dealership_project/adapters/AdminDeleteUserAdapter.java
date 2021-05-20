package com.example.car_dealership_project.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_dealership_project.DatabaseHelper;
import com.example.car_dealership_project.R;
import com.example.car_dealership_project.models.User;

import java.util.ArrayList;
import java.util.List;

public class AdminDeleteUserAdapter extends RecyclerView.Adapter<AdminDeleteUserAdapter.ViewHolder> implements Filterable {

    private final List<User> users;
    DatabaseHelper dataBaseHelper;
    Context context;

    public AdminDeleteUserAdapter(Context context, List<User> users){
        this.users = new ArrayList<>(users);
        this.context = context;
    }

    /* <------------ Recycler View Stuff ------------> */
    @NonNull
    @Override
    public AdminDeleteUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_user_grid,parent,false);
        dataBaseHelper = new DatabaseHelper(parent.getContext(), "Project", null,1);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdminDeleteUserAdapter.ViewHolder holder, final int position) {
        final String email = users.get(position).getEmail();
        String firstName = users.get(position).getFirstName();
        String lastName = users.get(position).getLastName();

        holder.setData(email,firstName,lastName);
        holder.delete_button.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean result = dataBaseHelper.deleteUser(email);
                if(result){
                    users.remove(users.get(position));
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount() - position);
                    Toast toast = Toast.makeText(view.getContext(), "Deleted user", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    Toast toast = Toast.makeText(view.getContext(), "Failed to delete", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView email;
        private final TextView firstName;
        private final TextView lastName;
        private final Button delete_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.user_email);
            firstName = itemView.findViewById(R.id.user_firstname);
            lastName = itemView.findViewById(R.id.user_lastname);
            delete_button = itemView.findViewById(R.id.user_delete);
        }
        public void setData(String email, String firstName, String lastName){
            this.email.setText(email);
            this.firstName.setText(firstName);
            this.lastName.setText(lastName);
        }
    }

    /* <------------ Filtering Stuff ------------> */
    //Empty filter will not be used
    @Override
    public Filter getFilter() {
        return filter;
    }

    private final Filter filter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<User> filteredUsers = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredUsers.addAll(users);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (User user : users) {
                    if (user.getEmail().contains(filterPattern)) {
                        filteredUsers.add(user);
                    }
                }
            }
            FilterResults res = new FilterResults();
            res.values = filteredUsers;
            return res;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            users.clear();
            users.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

}
