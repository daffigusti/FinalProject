package khadafi.com.finalproject.Entity;

import com.orm.SugarRecord;

/**
 * Created by KHADAFI on 5/8/2016.
 */
public class Favorite extends SugarRecord {
    int movieId;
    String note;

    public Favorite() {
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
