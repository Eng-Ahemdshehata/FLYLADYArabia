package com.ashehata.flyladyarabia.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashehata.flyladyarabia.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class IconAdapter extends RecyclerView.Adapter<IconAdapter.ImageViewHolder> {


    private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemResId , View view);
    }

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Integer> listIcons;

    //getting the context and product list with constructor
    public IconAdapter(Context mCtx, List<Integer> listIcons, ListItemClickListener listener) {
        this.mCtx = mCtx;
        this.listIcons = listIcons;
        this.mOnClickListener = listener ;
        Log.v("  main  ",listener.toString()+"");

    }


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_user_icon, null);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        //getting the product of the specified position
        int icon = listIcons.get(position);
        holder.circleImageView.setImageResource(icon);

    }


    @Override
    public int getItemCount() {
        return listIcons.size();
    }


    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView circleImageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.image_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(listIcons.get(clickedPosition), view );
            Log.v("  main  ","adpter");


        }
    }

}
