package khadafi.com.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import khadafi.com.finalproject.Adapter.GridViewAdapter;
import khadafi.com.finalproject.Entity.Favorite;

/**
 * An activity representing a list of MovieItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    GridView gv;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movieitem_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Popular Movies");
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);


        gv= (GridView) findViewById(R.id.movieitem_list);
        assert gv != null;

        FetchMovies fetchMovies = new FetchMovies();
        String url = "http://api.themoviedb.org/3/movie/popular";
        fetchMovies.execute(url);

        if (findViewById(R.id.movieitem_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.

            mTwoPane = true;
        }
    }

    public void setTitle(String title){
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        if(id == R.id.action_highest_rate){
            FetchMovies fetchMovies = new FetchMovies();
            String url = "http://api.themoviedb.org/3/movie/top_rated";
            setTitle("Top Rated movies");
            fetchMovies.execute(url);
            return true;
        }else  if(id == R.id.action_popular){
            FetchMovies fetchMovies = new FetchMovies();
            String url = "http://api.themoviedb.org/3/movie/popular";
            setTitle("Popular movies");
            fetchMovies.execute(url);
            return true;
        }else  if(id == R.id.action_upcoming){
            FetchMovies fetchMovies = new FetchMovies();
            String url = "http://api.themoviedb.org/3/movie/upcoming";
            setTitle("Up comings movies");
            fetchMovies.execute(url);
            return true;
        }else  if(id == R.id.action_now_playing){
            FetchMovies fetchMovies = new FetchMovies();
            String url = "http://api.themoviedb.org/3/movie/now_playing";
            setTitle("Now playing");
            fetchMovies.execute(url);
            return true;
        }else  if(id == R.id.action_favorite){
            FetchMoviesFavorite fetchMoviesFavorite = new FetchMoviesFavorite();
            String url = "";
            setTitle("Favorite Movies");
            fetchMoviesFavorite.execute(url);
            return true;
        }else  if(id == R.id.action_about){
            startActivity(new Intent(this,AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public class FetchMovies extends AsyncTask<String, Void, List<Movie>> {
        private final String LOG_TAG = FetchMovies.class.getSimpleName();
        final ProgressDialog progressDialog = new ProgressDialog(MovieItemListActivity.this);
        @Override
        protected void onPreExecute() {

            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading movies...");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            List<Movie> data  = new ArrayList<>();

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonResult = "";
            try {
                final String BASE_URL =params[0];

                final String API_KEY = "?api_key=20cf3f6e251a104268fd10c9412016e0";
                Uri builtUri = Uri.parse(BASE_URL+API_KEY).buildUpon()
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonResult = buffer.toString();
                JSONObject result = new JSONObject(jsonResult);


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    JSONArray data_json =result.getJSONArray("results");
                    for (int i=0;i<data_json.length();i++)
                    {
                        Movie movie  = new Movie();
                        JSONObject object = data_json.getJSONObject(i);

                        movie.setId(object.getInt("id"));
                        movie.setPoster_path("http://image.tmdb.org/t/p/w185"+object.getString("poster_path"));
                        movie.setBackdrop_path("http://image.tmdb.org/t/p/w780"+object.getString("backdrop_path"));
                        movie.setRelease_date(object.getString("release_date"));
                        movie.setTitle(object.getString("title"));
                        movie.setOverview(object.getString("overview"));
                        movie.setVote_average(object.getLong("vote_average"));
                        data.add(movie);
                    }
                }

            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(final List<Movie> movies) {
            super.onPostExecute(movies);
            progressDialog.hide();
            GridViewAdapter gridViewAdapter = new GridViewAdapter(getApplicationContext(),movies);
            gv.setAdapter(gridViewAdapter);

            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Movie movie = movies.get(position);
                    if (mTwoPane) {

                        Bundle arguments = new Bundle();
                        arguments.putString("poster_path",movie.getPoster_path());
                        arguments.putString("backdrop_path",movie.getBackdrop_path());
                        arguments.putString("year",movie.getRelease_date());
                        arguments.putString("release",movie.getRelease_date());
                        arguments.putString("sinopsis",movie.getOverview());
                        arguments.putString("title",movie.getTitle());
                        arguments.putFloat("duration",movie.getVote_average());
                        arguments.putInt("id",movie.getId());
                        MovieDetailFragment fragment = new MovieDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.movieitem_detail_container, fragment)
                                .commit();
                    } else {
                        Intent intent = new Intent(MovieItemListActivity.this,MovieItemDetailActivity.class);
                        intent.putExtra("poster_path",movie.getPoster_path());
                        intent.putExtra("backdrop_path",movie.getBackdrop_path());
                        intent.putExtra("year",movie.getRelease_date());
                        intent.putExtra("release",movie.getRelease_date());
                        intent.putExtra("sinopsis",movie.getOverview());
                        intent.putExtra("title",movie.getTitle());
                        intent.putExtra("duration",movie.getVote_average());
                        intent.putExtra("id",movie.getId());

                        startActivity(intent);
                    }



                }
            });
        }
    }

    public class FetchMoviesFavorite extends AsyncTask<String, Void, List<Movie>> {
        private final String LOG_TAG = FetchMovies.class.getSimpleName();
        final ProgressDialog progressDialog = new ProgressDialog(MovieItemListActivity.this);
        @Override
        protected void onPreExecute() {

            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading movies...");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected List<Movie> doInBackground(String... params) {
            List<Movie> data  = new ArrayList<>();

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonResult = "";
            try {
                List<Favorite> favorites = Favorite.listAll(Favorite.class);

                for (Favorite favorite :
                        favorites) {

                    final String BASE_URL ="http://api.themoviedb.org/3/movie/"+favorite.getMovieId()+"";

                    final String API_KEY = "?api_key=20cf3f6e251a104268fd10c9412016e0";

                    Uri builtUri = Uri.parse(BASE_URL+API_KEY).buildUpon()
                            .build();

                    URL url = new URL(builtUri.toString());

                    Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                    // Create the request to OpenWeatherMap, and open the connection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return null;
                    }
                    jsonResult = buffer.toString();
                    JSONObject result = new JSONObject(jsonResult);


                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        Movie movie  = new Movie();

                        movie.setId(result.getInt("id"));
                        movie.setPoster_path("http://image.tmdb.org/t/p/w185"+result.getString("poster_path"));
                        movie.setBackdrop_path("http://image.tmdb.org/t/p/w780"+result.getString("backdrop_path"));
                        movie.setRelease_date(result.getString("release_date"));
                        movie.setTitle(result.getString("title"));
                        movie.setOverview(result.getString("overview"));
                        movie.setVote_average(result.getLong("vote_average"));
                        data.add(movie);
                    }
                }
            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();

            }
            return data;
        }

        @Override
        protected void onPostExecute(final List<Movie> movies) {
            super.onPostExecute(movies);
            progressDialog.hide();
            GridViewAdapter gridViewAdapter = new GridViewAdapter(getApplicationContext(),movies);
            gv.setAdapter(gridViewAdapter);

            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Movie movie = movies.get(position);
                    if (mTwoPane) {

                        Bundle arguments = new Bundle();
                        arguments.putString("poster_path",movie.getPoster_path());
                        arguments.putString("backdrop_path",movie.getBackdrop_path());
                        arguments.putString("year",movie.getRelease_date());
                        arguments.putString("release",movie.getRelease_date());
                        arguments.putString("sinopsis",movie.getOverview());
                        arguments.putString("title",movie.getTitle());
                        arguments.putFloat("duration",movie.getVote_average());
                        arguments.putInt("id",movie.getId());
                        MovieDetailFragment fragment = new MovieDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.movieitem_detail_container, fragment)
                                .commit();
                    } else {
                        Intent intent = new Intent(MovieItemListActivity.this,MovieItemDetailActivity.class);
                        intent.putExtra("poster_path",movie.getPoster_path());
                        intent.putExtra("backdrop_path",movie.getBackdrop_path());
                        intent.putExtra("year",movie.getRelease_date());
                        intent.putExtra("release",movie.getRelease_date());
                        intent.putExtra("sinopsis",movie.getOverview());
                        intent.putExtra("title",movie.getTitle());
                        intent.putExtra("duration",movie.getVote_average());
                        intent.putExtra("id",movie.getId());

                        startActivity(intent);
                    }
                }
            });
        }
    }

}
