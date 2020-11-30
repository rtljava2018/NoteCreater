package com.rkm.notbook.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rkm.notbook.R;
import com.rkm.notbook.db.model.NoteModel;

import java.util.ArrayList;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.DiseaseViewHolder> {

    private Context context;
    private ArrayList<NoteModel> listOfItems;
    private ItemOnClickListener itemOnClickListener;

    public NoteListAdapter(Context context, ArrayList<NoteModel> listOfItems){
        this.context = context;
        this.listOfItems = listOfItems;
    }

    @NonNull
    @Override
    public DiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new DiseaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseViewHolder holder, final int position) {
        holder.txttitle.setText(listOfItems.get(position).getNoteTitle());
        if (listOfItems.get(position).getNoteTagcolorcode()!=null && !listOfItems.get(position).getNoteTagcolorcode().equalsIgnoreCase("")){
            holder.mainlayout.setBackgroundColor(Color.parseColor(listOfItems.get(position).getNoteTagcolorcode()));
        }else {
            holder.mainlayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        holder.txtdes.setText(listOfItems.get(position).getNoteDes());
        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnClickListener.onClickItem(v, position, listOfItems.get(position), listOfItems);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfItems.size();
    }

    static class DiseaseViewHolder extends RecyclerView.ViewHolder {
        TextView txttitle;
        TextView txtdes;
        LinearLayout mainlayout;
        public DiseaseViewHolder(@NonNull View itemView) {
            super(itemView);
            txttitle = itemView.findViewById(R.id.txt_title_name);
            txtdes = itemView.findViewById(R.id.txt_title_des);
            mainlayout = itemView.findViewById(R.id.main_layout);
        }
    }

    public interface ItemOnClickListener{
        void onClickItem(View v, int position, NoteModel item, ArrayList<NoteModel> listOfItem);
    }

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public void setValue(ArrayList<NoteModel> listOfItems){
        this.listOfItems.clear();
        this.listOfItems.addAll(listOfItems);
        notifyDataSetChanged();
    }

}
