package com.ashehata.flyladyarabia.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.ashehata.flyladyarabia.R;
import com.ashehata.flyladyarabia.data.room.AppDataBase;
import com.ashehata.flyladyarabia.data.room.notificationTable.NotificationEntity;
import com.ashehata.flyladyarabia.utility.AppUtility;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context mContext;
    private List<NotificationEntity> modelList;
    private OnItemClickListener mItemClickListener;


    public NotificationAdapter(Context context, List<NotificationEntity> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<NotificationEntity> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final NotificationEntity model = modelList.get(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;
            genericViewHolder.itemTxtTitle.setText(model.getTitle());
            genericViewHolder.itemTxtMessage.setText(R.string.more_detials);
            genericViewHolder.imgUser.setImageResource(model.getImage());
            genericViewHolder.itemRoutine.setText("الروتين "+model.getRoutine()+" | ");



            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Africa/Cairo"));
            cal.setTime(AppUtility.getCurrentTime());

            //genericViewHolder.itemDate.setText(cal.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.ALL_STYLES,)+"");





        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }


    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    /*
    private AbstractModel getItem(int position) {
        return modelList.get(position);
    }
    */


    public interface OnItemClickListener {
        void onItemClick(View view, int position, NotificationEntity model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUser;
        private TextView itemTxtTitle;
        private TextView itemTxtMessage;
        private TextView itemRoutine ;
        private TextView itemDate ;



        // @BindView(R.id.img_user)
        // ImageView imgUser;
        // @BindView(R.id.item_txt_title)
        // TextView itemTxtTitle;
        // @BindView(R.id.item_txt_message)
        // TextView itemTxtMessage;
        // @BindView(R.id.radio_list)
        // RadioButton itemTxtMessage;
        // @BindView(R.id.check_list)
        // CheckBox itemCheckList;
        public ViewHolder(final View itemView) {
            super(itemView);

            // ButterKnife.bind(this, itemView);

            this.imgUser = (ImageView) itemView.findViewById(R.id.img_user);
            this.itemTxtTitle = (TextView) itemView.findViewById(R.id.item_txt_title);
            this.itemTxtMessage = (TextView) itemView.findViewById(R.id.item_txt_message);
            this.itemRoutine = (TextView) itemView.findViewById(R.id.routine);
            //this.itemDate = (TextView) itemView.findViewById(R.id.notification_date);



            Log.v("item" ,itemView.toString());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //fix_it
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));


                }
            });

        }
    }
    public  void clear(){
        modelList.clear();
        notifyDataSetChanged();
    }

   public List<NotificationEntity> getList(){

        return modelList ;
   }
}

