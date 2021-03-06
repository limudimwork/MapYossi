package com.happytrees.mapyossi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris on 12/24/2017.
 */

public class LocationAdapter  extends RecyclerView.Adapter<LocationAdapter.MyViewHolder>{

    List<Location> allLocations;
    Context context;

    public LocationAdapter(List<Location> allLocations, Context context) {
        this.allLocations = allLocations;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewFromXML = LayoutInflater.from(context).inflate(R.layout.single_item,null);//"single_item" is xml template of single item in Recycler View
        MyViewHolder myViewHolder = new MyViewHolder(viewFromXML);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder  myViewHolder, int position) {
        Location currentLocation = allLocations.get(position);
        myViewHolder.bindMyWonderData(currentLocation);
    }

    @Override
    public int getItemCount() {
        return allLocations.size();
    }


    //INNER CLASS
    public class MyViewHolder extends RecyclerView.ViewHolder {

        View itemView;//DEFINE VIEW VARIABLE

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView= itemView;
        }

        //CUSTOM METHOD WE NEED TO DEFINE
        public void bindMyWonderData(final Location  currentLocation) {
            TextView itemText = itemView.findViewById(R.id.single_text_id);
            itemText.setText(currentLocation.name);

            //MAKE RECYCLER VIEW CLICKABLE
            //normal click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//CHANGES FRAGMENTS WHEN ITEM IS CLICKED
                   Toast.makeText(context,currentLocation.name,Toast.LENGTH_SHORT).show();
                    FragmentChanger fragmentChanger = (FragmentChanger)context;
                    fragmentChanger.changeFragments(currentLocation);
                }
            });
            //long click -> removes item
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                //REMOVE ITEM FROM DATABASE THEN FROM RECYCLER VIEW
                    //remove item from database
                    Location location = Location.findById(Location.class,allLocations.get(getAdapterPosition()).getId());
                    location.delete();
                    //standard code for removing item from recycler view -> we remove item from list after we removed it from database
                    allLocations.remove(getAdapterPosition());//we used  "getAdapterPosition()" to get item  position (int)
                    notifyItemRemoved(getAdapterPosition());//we used  "getAdapterPosition()" to get item  position (int)
                    notifyItemRangeChanged(getAdapterPosition(), allLocations.size());//we used  "getAdapterPosition()" to get item  position (int)

                    Toast.makeText(context,"item removed",Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }


        }
}
