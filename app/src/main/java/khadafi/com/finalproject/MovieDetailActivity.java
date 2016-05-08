package khadafi.com.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    TextView txtYear;
    TextView txtDuration;
    TextView txtRelase;

    TextView txtSinopsis;

    ImageView imageView;

    int movie_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        // toolbar
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            actionBar.setTitle(intent.getStringExtra("title"));

        }

        txtYear = (TextView) findViewById(R.id.txt_year);
        txtDuration =(TextView) findViewById(R.id.txt_duration);
        txtRelase =(TextView) findViewById(R.id.txt_release);
        txtSinopsis = (TextView)findViewById(R.id.txt_sinopsis);
        imageView = (ImageView)findViewById(R.id.imageView2);



        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(getApplicationContext()) //
                .load(intent.getStringExtra("poster_path")) //
                //.placeholder(R.drawable.placeholder) //
                //.error(R.drawable.error) //
                .fit() //
                .into(imageView);

        String release_date = intent.getStringExtra("year");
        txtYear.setText(release_date.split("/")[0]);

        txtDuration.setText(intent.getStringExtra("duration"));

        txtRelase.setText(intent.getStringExtra("release"));
        txtSinopsis.setText(intent.getStringExtra("sinopsis"));

        movie_id = intent.getIntExtra("id",0);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
