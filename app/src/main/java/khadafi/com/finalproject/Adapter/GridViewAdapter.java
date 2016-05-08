package khadafi.com.finalproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import khadafi.com.finalproject.Movie;
import khadafi.com.finalproject.R;

/**
 * Created by KHADAFI on 5/5/2016.
 */
public class GridViewAdapter extends BaseAdapter {
    private final Context context;
    private  List<Movie> urls = new ArrayList<Movie>();


    public GridViewAdapter(Context context, List<Movie> urls) {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Movie getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_poster, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        // Get the image URL for the current position.
        Movie movie = getItem(position);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context) //
                .load(movie.getPoster_path()) //
                .placeholder(R.drawable.ic_hourglass_empty_black_36dp) //
                .error(R.drawable.ic_error_black_36dp) //
                .fit() //
                .tag(context) //
                .into(imageView);

        return convertView ;
    }
}
