package com.example.inventarioxyz;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

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

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.myViewHolder> {
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options){
        super(options);

    }
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model){
        holder.nombre.setText(model.getNombre());
        holder.precio.setText(model.getPrecio());
        holder.stock.setText(model.getStock());

        Glide.with(holder.img.getContext())
                .load(model.getImgURL())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.ventana_emergente))
                        .setExpanded(true, 1200)
                        .create();
                View view = dialogPlus.getHolderView();
                EditText nombre = view.findViewById(R.id.nombreText);
                EditText precio = view.findViewById(R.id.precioText);
                EditText stock = view.findViewById(R.id.stockText);
                EditText imgURL = view.findViewById(R.id.img1);

                Button actualizar = view.findViewById(R.id.btn_actualizar);

                nombre.setText(model.getNombre());
                precio.setText(model.getPrecio());
                stock.setText(model.getStock());
                imgURL.setText(model.getImgURL());
                dialogPlus.show();

                actualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("Nombre", nombre.getText().toString());
                        map.put("Precio", precio.getText().toString());
                        map.put("Stock", stock.getText().toString());
                        map.put("imgURL", imgURL.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("InventarioXYZ")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.nombre.getContext(), "Actualización Correcta", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.nombre.getContext(), "Error en la Actualizacion ", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });
            }
        });
        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.nombre.getContext());
                builder.setTitle("Estas seguro de Eliminar");
                builder.setMessage("Eliminar");

                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        FirebaseDatabase.getInstance().getReference().child("InventarioXYZ")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Toast.makeText(holder.nombre.getContext(), "Cancelar", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new myViewHolder(view);
    }
    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView nombre, precio, stock;
        Button editar, eliminar;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img1);
            nombre = itemView.findViewById(R.id.nombre);
            precio = itemView.findViewById(R.id.precio);
            stock = itemView.findViewById(R.id.stock);

            editar = itemView.findViewById(R.id.btn_editar);
            eliminar = itemView.findViewById(R.id.btn_eliminar);
        }
    }


}
