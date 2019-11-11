package com.ashehata.flyladyarabia.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.ashehata.flyladyarabia.R;
import com.ashehata.flyladyarabia.data.room.roomTable.RoomEntity;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */


public class  RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ProductViewHolder> {


    private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex , String title);
    }

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<RoomEntity> roomlist;

    //getting the context and product list with constructor
    public RoomAdapter(Context mCtx, List<RoomEntity> roomlist, ListItemClickListener listener) {
        this.mCtx = mCtx;
        this.roomlist = roomlist;
        this.mOnClickListener = listener ;
        Log.v("  main  ",listener.toString()+"");
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_item, null);

        return new ProductViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {


        //getting the product of the specified position
        RoomEntity room = roomlist.get(position);

        //Typeface custom_font = Typeface.createFromAsset(mCtx.getAssets(), "fonts/english.otf");
        //binding the data with the viewholder views
        holder.textViewTitle.setText(room.getTitle());
        //holder.textViewTitle.setTypeface(custom_font);

        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(room.getImage()));

    }


    @Override
    public int getItemCount() {
        return roomlist.size();
    }


     class  ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTitle ;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text);
            imageView = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition,roomlist.get(clickedPosition).getTitle());
            Log.v("  main  ","adpter");

        }
    }

    // Clean all elements of the recycler
    public  void clear() {
        roomlist.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public  void addAll(List<RoomEntity> list) {
        roomlist.addAll(list);
        notifyDataSetChanged();
    }

}
