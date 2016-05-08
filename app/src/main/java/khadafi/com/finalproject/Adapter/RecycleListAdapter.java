package khadafi.com.finalproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import khadafi.com.finalproject.R;
import khadafi.com.finalproject.Trailer;

/**
 * Created by KHADAFI on 5/5/2016.
 */
public class RecycleListAdapter extends RecyclerView.Adapter<RecycleListAdapter.ViewHolder> {
    private final Context context;
    private  List<Trailer> urls = new ArrayList<Trailer>();
    private Activity activity;

    public RecycleListAdapter(Context context, List<Trailer> urls) {
        this.context = context;
        this.urls = urls;
    }

    public RecycleListAdapter(Context context, List<Trailer> urls, Activity activity) {
        this.context = context;
        this.urls = urls;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_list_trailer, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
       final Trailer trailer = urls.get(position);
        holder.vTitle.setText(trailer.getTitle());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getUrl())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView vTitle;
        public View view;
        public ViewHolder(View v) {
            super(v);
            this.view = v;
            vTitle =  (TextView) v.findViewById(R.id.txt_tittle);
        }


    }
}
