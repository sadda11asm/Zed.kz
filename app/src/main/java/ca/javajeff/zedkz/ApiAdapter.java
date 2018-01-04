package ca.javajeff.zedkz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Саддам on 02.01.2018.
 */

public class ApiAdapter extends RecyclerView.Adapter<ApiAdapter.ApiAdapterViewHolder> {

    private ArrayList<String> mInfoData;

    private final ApiAdapterOnClickHandler mClickHandler;



    public interface ApiAdapterOnClickHandler {
        void onClick(String id);
    }


    public ApiAdapter(ApiAdapterOnClickHandler ClickHandler) {
        mClickHandler= ClickHandler;
    }


    public class ApiAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView infoView;

        public ApiAdapterViewHolder(View view) {
            super(view);
            infoView = (TextView) view.findViewById(R.id.info);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            int adapterPosition = getAdapterPosition();
            String id;
            try {
                JSONObject object = OpenAPIJsonUtils.Arr.getJSONObject(adapterPosition);
                id = object.getString("id");
                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("id", adapterPosition);
                context.startActivity(intent);
                Log.v("aid", id);
            } catch (JSONException e) {
                id="error";
                e.printStackTrace();
            }
            mClickHandler.onClick(id);
        }
    }

    @Override
    public ApiAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.info_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttach = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttach);
        return new ApiAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ApiAdapterViewHolder holder, int position) {
        String info= mInfoData.get(position);
        Log.v("element", info);
        holder.infoView.setText(info);

    }

    @Override
    public int getItemCount() {
        if (null == mInfoData) return 0;
        return mInfoData.size();
    }

    public void setInfoData(ArrayList<String> infoData) {
        mInfoData = infoData;
        notifyDataSetChanged();
        //Log.v("array",mInfoData.toString());
    }
}
