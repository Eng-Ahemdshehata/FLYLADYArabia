package com.ashehata.flyladyarabia.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ashehata.flyladyarabia.R;
import com.ashehata.flyladyarabia.adapters.RoomAdapter;
import com.ashehata.flyladyarabia.data.room.AppDataBase;
import com.ashehata.flyladyarabia.data.room.roomTable.RoomEntity;

import java.util.List;

public class MainFragment extends Fragment implements RoomAdapter.ListItemClickListener {

    LinearLayoutManager linearLayoutManager ;
    RecyclerView recyclerView ;
    ProgressBar progressBar ;
    RoomAdapter roomAdapter ;
    SwipeRefreshLayout swipeContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        // Objects reference
        progressBar = view.findViewById(R.id.main_fragment_pb_indicator);
        recyclerView = view.findViewById(R.id.main_fragment_rv_display);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.main_fragment_swipeContainer);


        // Recycler view size
        recyclerView.setHasFixedSize(true);

        // How to display items in recycler view
        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true);

        recyclerView.setLayoutManager(linearLayoutManager);


        // Recycler view Adapter
        recyclerView.setAdapter(roomAdapter);

        // Set divider for recycler view
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
            //    linearLayoutManager.getOrientation());
        //recyclerView.addItemDecoration(dividerItemDecoration);


        // Passing context to RoomEntity
        RoomEntity roomEntity = new RoomEntity(getContext());

        // Swipe refresh layout
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Clear adapter
                roomAdapter.clear();

                // Clear data base and load it again
                AppDataBase.clearDataBase(getContext());

                // ...the data has come back, add new items to your adapter...
                TestAsync testAsync = new TestAsync();
                testAsync.execute();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // Now we call setRefreshing(false) to signal refresh has finished

                        if (swipeContainer.isRefreshing())
                            swipeContainer.setRefreshing(false);
                    }
                }, 1500);

            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));






        return view ;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Set Background thread
        if(savedInstanceState ==null){

            TestAsync testAsync = new TestAsync();
            testAsync.execute();
        }


        super.onViewCreated(view, savedInstanceState);
    }



    private class TestAsync extends AsyncTask<Void, Void, LiveData<List<RoomEntity>> > {

        @Override
        protected LiveData<List<RoomEntity>> doInBackground(Void... voids) {

            // Getting Live data from Room DB
            LiveData<List<RoomEntity>> list = AppDataBase.getInstance(getContext()).dataDao().getAllRooms() ;
            //Log.v("list",list.isEmpty()+"");
            return list;
        }

        @Override
        protected void onPostExecute(LiveData<List<RoomEntity>> roomEntities) {

            roomEntities.observe(getViewLifecycleOwner(), new Observer<List<RoomEntity>>() {
                @Override
                public void onChanged(List<RoomEntity> roomEntities) {

                    // Passing data to adapter
                    roomAdapter = new RoomAdapter(getContext(),roomEntities,MainFragment.this);
                    // Hide progress bar
                    progressBar.setVisibility(View.GONE);
                    // Update the UI when Db changes
                    recyclerView.setAdapter(roomAdapter);
                }
            });

        }


    }

    @Override
    public void onListItemClick(int clickedItemIndex , String title) {
        Log.v("  main  ",clickedItemIndex+"");
        Toast.makeText(getActivity().getBaseContext() , clickedItemIndex+"" + title, Toast.LENGTH_SHORT).show();
    }
}
