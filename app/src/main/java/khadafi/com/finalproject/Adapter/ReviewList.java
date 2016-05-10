package khadafi.com.finalproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import khadafi.com.finalproject.Entity.Review;
import khadafi.com.finalproject.R;

/**
 * Created by KHADAFI on 5/10/2016.
 */
public class ReviewList extends RecyclerView.Adapter<ReviewList.ViewHolder> {

    private final Context context;
    private List<Review> content = new ArrayList<Review>();

    public ReviewList(Context context, List<Review> content) {
        this.context = context;
        this.content = content;
    }



    @Override
    public ReviewList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_list_review, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Review review= content.get(position);
        holder.author.setText(review.getAuthor());
        holder.content.setText(review.getContent());

    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return content.size();
    }



    public  class ViewHolder extends RecyclerView.ViewHolder  {

        public   TextView author;
        public TextView content;
        public View view;
        public ViewHolder(View v) {
            super(v);
            this.view = v;
            author = (TextView) v.findViewById(R.id.txt_title);
            content = (TextView) v.findViewById(R.id.txt_comment);
        }


    }
}
