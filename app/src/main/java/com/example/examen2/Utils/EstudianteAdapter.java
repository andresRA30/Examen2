package com.example.examen2.Utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.examen2.R;

import com.example.examen2.UpdateRecordActivity;
import com.example.examen2.model.Estudiante;

import com.squareup.picasso.Picasso;

import java.util.List;



public class EstudianteAdapter extends RecyclerView.Adapter<EstudianteAdapter.ViewHolder> {
    private List<Estudiante> mEstudianteList;
    private Context mContext;
    private RecyclerView mRecyclerV;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView estudianteNombreTxtV;
        public TextView estudianteEdadTxtV;
        public TextView estudianteCorreoTxtV;
        public ImageView estudianteFotoImgV;


        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            estudianteNombreTxtV = (TextView) v.findViewById(R.id.name);
            estudianteEdadTxtV = (TextView) v.findViewById(R.id.age);
            estudianteCorreoTxtV = (TextView) v.findViewById(R.id.occupation);
            estudianteFotoImgV = (ImageView) v.findViewById(R.id.image);




        }
    }

    public void add(int position, Estudiante estudiante) {
        mEstudianteList.add(position, estudiante);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mEstudianteList.remove(position);
        notifyItemRemoved(position);
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public EstudianteAdapter(List<Estudiante> myDataset, Context context, RecyclerView recyclerView) {
        mEstudianteList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EstudianteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.single_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Estudiante estudiante = mEstudianteList.get(position);
        holder.estudianteNombreTxtV.setText("Name: " + estudiante.getNombre());
        holder.estudianteEdadTxtV.setText("Age: " + estudiante.getEdad());
        holder.estudianteCorreoTxtV.setText("Occupation: " + estudiante.getCorreo());
        Picasso.with(mContext).load(estudiante.getFoto()).placeholder(R.mipmap.ic_launcher).into(holder.estudianteFotoImgV);

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose option");
                builder.setMessage("Update or delete user?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(estudiante.getId());

                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EstudianteDBHelper dbHelper = new EstudianteDBHelper(mContext);
                        dbHelper.deleteEstudianteRecord(estudiante.getId(), mContext);

                        mEstudianteList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mEstudianteList.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });


    }

    private void goToUpdateActivity(long personId){
        Intent goToUpdate = new Intent(mContext, UpdateRecordActivity.class);
        goToUpdate.putExtra("USER_ID", personId);
        mContext.startActivity(goToUpdate);
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mEstudianteList.size();
    }



}