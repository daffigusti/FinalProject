package khadafi.com.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * An activity representing a single MovieItem detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MovieItemListActivity}.
 */
public class MovieItemDetailActivity extends AppCompatActivity {

    public static final String MOVIE_ID = "id";
    public static final String POSTER_PATH = "poster_path";
    public static final String BACKDROP_PATH = "backdrop_path";
    public static final String YEAR = "year";
    public static final String RELEASE = "release";
    public static final String SINOPSIS = "sinopsis";
    public static final String TITLE = "title";
    public static final String DURATION = "duration";
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movieitem_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(MovieItemDetailActivity.MOVIE_ID,
                    getIntent().getIntExtra(MovieItemDetailActivity.MOVIE_ID,0));
            arguments.putString(MovieItemDetailActivity.POSTER_PATH,
                    getIntent().getStringExtra(MovieItemDetailActivity.POSTER_PATH));
            arguments.putString(MovieItemDetailActivity.BACKDROP_PATH,
                    getIntent().getStringExtra(MovieItemDetailActivity.BACKDROP_PATH));
            arguments.putString(MovieItemDetailActivity.RELEASE,
                    getIntent().getStringExtra(MovieItemDetailActivity.RELEASE));
            arguments.putString(MovieItemDetailActivity.SINOPSIS,
                    getIntent().getStringExtra(MovieItemDetailActivity.SINOPSIS));
            arguments.putString(MovieItemDetailActivity.TITLE,
                    getIntent().getStringExtra(MovieItemDetailActivity.TITLE));
            arguments.putFloat(MovieItemDetailActivity.DURATION,
                    getIntent().getFloatExtra(MovieItemDetailActivity.DURATION,0));

            arguments.putString(MovieItemDetailActivity.YEAR,
                    getIntent().getStringExtra(MovieItemDetailActivity.YEAR));

            imageView = (ImageView) findViewById(R.id.img_background);

            if(imageView!=null){
                Picasso.with(this) //
                        .load(getIntent().getStringExtra("backdrop_path")) //
                        //.placeholder(R.drawable.placeholder) //
                        //.error(R.drawable.error) //
                        .fit() //
                        .into(imageView);
            }

            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movieitem_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, MovieItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
