package com.myapp.userreg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import java.util.HashMap;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.MyViewHolder> {

    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MyViewHolder holder, final int position, @NonNull MainModel model) {
        holder.flavor.setText(model.getFlavor());
        holder.price.setText(model.getPrice());
        holder.location.setText(model.getLocation());

        Glide.with(holder.img.getContext())
                .load(model.getImgurl())
                .placeholder(R.drawable.signin)
                .circleCrop()
                .error(R.drawable.error)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1000)
                        .create();

                View view = dialogPlus.getHolderView();
                EditText flavor = view.findViewById(R.id.txtFlavor);
                EditText price = view.findViewById(R.id.txtPrice);
                EditText location = view.findViewById(R.id.txtLocation);
                EditText imgurl = view.findViewById(R.id.txtImgurl);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);
                flavor.setText(model.getFlavor());
                price.setText(model.getPrice());
                location.setText(model.getLocation());
                imgurl.setText(model.getImgurl());
                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String updatedFlavor = flavor.getText().toString().trim();
                        String updatedPrice = price.getText().toString().trim();
                        String updatedLocation = location.getText().toString().trim();
                        String updatedImgurl = imgurl.getText().toString().trim();

                        if (updatedFlavor.isEmpty() || updatedPrice.isEmpty() || updatedLocation.isEmpty() || updatedImgurl.isEmpty()) {
                            Toast.makeText(holder.flavor.getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Map<String, Object> map = new HashMap<>();
                        map.put("flavor", updatedFlavor);
                        map.put("price", updatedPrice);
                        map.put("location", updatedLocation);
                        map.put("imgurl", updatedImgurl);

                        FirebaseDatabase.getInstance().getReference().child("cupcakes")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.flavor.getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.flavor.getContext(), "Data Not Updated Properly", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.flavor.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data cannot be undone");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Check if any field is empty before deleting
                        if (model.getFlavor().isEmpty() || model.getLocation().isEmpty() || model.getPrice().isEmpty() || model.getImgurl().isEmpty()) {
                            Toast.makeText(holder.flavor.getContext(), "Cannot delete data with empty fields", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        FirebaseDatabase.getInstance().getReference().child("cupcakes")
                                .child(getRef(position).getKey()).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.flavor.getContext(), "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.flavor.getContext(), "Data Not Deleted Properly", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.flavor.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView flavor, price, location;
        Button btnEdit, btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img1);
            flavor = itemView.findViewById(R.id.flavortext);
            price = itemView.findViewById(R.id.pricetext);
            location = itemView.findViewById(R.id.location);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
