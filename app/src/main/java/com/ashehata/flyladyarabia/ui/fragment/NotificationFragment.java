package com.ashehata.flyladyarabia.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.ashehata.flyladyarabia.R;
import com.ashehata.flyladyarabia.adapters.NotificationAdapter;
import com.ashehata.flyladyarabia.data.room.AppDataBase;
import com.ashehata.flyladyarabia.data.room.notificationTable.NotificationEntity;
import com.ashehata.flyladyarabia.data.sharedpreference.AppPreference;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefreshRecyclerList;
    private NotificationAdapter mAdapter;
    BottomNavigationView navView ;
    View badge ;
    TextView notificationCount ;
    View alertView ;
    CheckBox checkBox ;
    LinearLayout missionLayout ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        findViews(view);

        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAdapter();

        swipeRefreshRecyclerList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //clear adapter
                mAdapter.clear();

                // Getting new data
                Async testAsync = new Async();
                testAsync.execute();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (swipeRefreshRecyclerList.isRefreshing()){
                            swipeRefreshRecyclerList.setRefreshing(false);

                        }
                    }
                }, 1500);


            }
        });


        // Set Background thread
        if(savedInstanceState ==null){

            Log.v("async","called");
            Async testAsync = new Async();
            testAsync.execute();
        }


        // Display notification num on activity
        setNotificationCount();

        // Delete item when swipe it
        setOnSwipeRecyclerViewItem();


    }

    private void setOnSwipeRecyclerViewItem() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.ANIMATION_TYPE_DRAG,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                final RecyclerView.ViewHolder mviewHolder = viewHolder ;

                //Get clicked item id
                int position = mviewHolder.getAdapterPosition();
                NotificationEntity notificationEntity = mAdapter.getList().get(position);
                final int id = notificationEntity.getId() ;


                // Inflates check box in alert dialog
                alertView = View.inflate(getContext(),R.layout.cb_view,null);
                checkBox = alertView.findViewById(R.id.notification_fragment_cb_message);

                // check box listener
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        // save to Preference
                        AppPreference.writeBoolean(AppPreference.DO_NOT_SHOW_MESSAGE,b);
                    }
                });


                //check if user wanna show dialog or not
                if(AppPreference.readBoolean(getContext(), AppPreference.DO_NOT_SHOW_MESSAGE)){
                    deleteItemFromDb(id);
                }else {
                    showAlertDialog(id);

                }

                 /*
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = mviewHolder.getAdapterPosition();
                        NotificationEntity notificationEntity = mAdapter.getList().get(position);
                         id = notificationEntity.getId() ;

                        Snackbar.make(navView, "تمت المهمـة",Snackbar.LENGTH_LONG).setAction("تراجـع", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //mAdapter.clear();
                                mAdapter.notifyDataSetChanged();

                            }
                        }).show();

                    }

                });

                // delete item if user doesn't undo

                    deleteItemFromDb();
                    */


            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private void showAlertDialog(final int itemId) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(getString(R.string.alert_dialog_title));
        dialog.setView(alertView);
        dialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                deleteItemFromDb(itemId);
            }
        });

        dialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAdapter.notifyDataSetChanged();

            }
        });
        dialog.setCancelable(false);
        dialog.show();

    }

    private void deleteItemFromDb(int id) {
        final int mId = id ;

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDataBase.getInstance(getContext()).dataDao().delete(mId);

                    }
                });

    }

    private void setNotificationCount() {

        // Notification Badge
        navView = getActivity().findViewById(R.id.nav_view);
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(1);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        //badge = LayoutInflater.from(getContext())
          //       .inflate(R.layout.unread_message_layout,bottomNavigationMenuView ,false);
        badge = View.inflate(getContext(),R.layout.unread_message_layout,null);
        notificationCount = badge.findViewById(R.id.tvUnreadChats);
        notificationCount.setBackgroundResource(R.drawable.notification_badge);
        // set margin
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) notificationCount.getLayoutParams();
        params.setMarginEnd((int) getResources().getDimension(R.dimen.dim_8dp));
//        ; params.leftMargin = 100; params.topMargin = 200;

        itemView.addView(badge);
    }


    private void findViews(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        swipeRefreshRecyclerList = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_recycler_list);
        swipeRefreshRecyclerList.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        missionLayout = view.findViewById(R.id.notification_fragment_ll_mission);

    }


    private void setAdapter() {

        //mAdapter = new NotificationAdapter(getActivity(), modelList);

        recyclerView.setHasFixedSize(true);

        // use a oldLayout layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setClickable(true);
        recyclerView.setAdapter(mAdapter);






    }

    public class Async extends AsyncTask<Void , Void ,LiveData<List<NotificationEntity>>>{


        @Override
        protected LiveData<List<NotificationEntity>> doInBackground(Void... voids) {

            // Getting data from DB
            LiveData<List<NotificationEntity>> list = AppDataBase.getInstance(getContext()).dataDao().getAllNotification();


            return list;
        }

        @Override
        protected void onPostExecute(LiveData<List<NotificationEntity>> notificationEntities) {

            notificationEntities.observe(getViewLifecycleOwner(), new Observer<List<NotificationEntity>>() {
                @Override
                public void onChanged(List<NotificationEntity> notificationEntities) {
                    mAdapter = new NotificationAdapter(getContext(),notificationEntities );

                    //Set Recycler view item Click listener
                    onItemClicked();

                    recyclerView.setAdapter(mAdapter);

                    // Getting num of notification and display it on badge
                    // Set empty view for recycler view

                    if (mAdapter.getItemCount()==0){

                        missionLayout.setVisibility(View.VISIBLE);
                        badge.setVisibility(View.GONE);
                    }else {
                        missionLayout.setVisibility(View.GONE);
                        badge.setVisibility(View.VISIBLE);
                        notificationCount.setText(mAdapter.getItemCount()+"");

                    }


                    Log.v("live data","done");

                }
            });

        }

        private void onItemClicked() {

            if(mAdapter != null){
                mAdapter.SetOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, NotificationEntity model) {
                        Log.v("Adapter","done");

                    }

                });

            }
        }
    }
}