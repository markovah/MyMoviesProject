package com.example.markokv.mymoviesproject; /**
 * Created by Marko Vähä-Erkkilä on 25.1.15.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;
import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME ="moviesDB.db";
    private static final String TABLE_MOVIES = "movies";
    //Columns
    private static final String COLUMN_ID="id";
    private static final String COLUMN_TITLE ="title";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_FORMAT = "format";
    private static final String COLUMN_LENGHT = "lenght";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_PATH = "path";

    public MyDBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    @Override //Creates new database
    public void onCreate(SQLiteDatabase db){
        String CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_MOVIES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_TITLE + " TEXT, " + COLUMN_YEAR + " INTEGER, " + COLUMN_FORMAT + " TEXT, " +
                COLUMN_LENGHT + " REAL, " + COLUMN_GENRE + " TEXT, " + COLUMN_PATH + " TEXT " + ")";
        db.execSQL(CREATE_MOVIE_TABLE);
    }
    @Override //Upgrades database
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_MOVIES);
        onCreate(db);
    }
    //Add Movie to database method
    public void addMovie(Movie movie){ //Adds movie to database
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_YEAR, movie.getYear());
        values.put(COLUMN_FORMAT, movie.getFormat());
        values.put(COLUMN_LENGHT, movie.getLenght());
        values.put(COLUMN_GENRE, movie.getGenre());
        values.put(COLUMN_PATH, movie.getPath());
        SQLiteDatabase db = this.getWritableDatabase();
        //Adding row
        db.insert(TABLE_MOVIES, null, values);
        db.close();
    }
    //Update Movie method
    public int updateMovie(Movie movie){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_YEAR, movie.getYear());
        values.put(COLUMN_FORMAT, movie.getFormat());
        values.put(COLUMN_LENGHT, movie.getLenght());
        values.put(COLUMN_GENRE, movie.getGenre());
        values.put(COLUMN_PATH, movie.getPath());
        SQLiteDatabase db = this.getWritableDatabase();
        //Updating row
        return db.update(TABLE_MOVIES, values, COLUMN_ID + "=?", new String[]{ String.valueOf(movie.getID()) } ) ;
    }
    //Get all Movies from database method
    public List<Movie> getAllMovies(){
        List<Movie> titleList = new ArrayList<Movie>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do {
                Movie movie = new Movie();
                movie.setID(Integer.parseInt(cursor.getString(0)));
                movie.setTitle(cursor.getString(1));
                movie.setYear(Integer.parseInt(cursor.getString(2)));
                movie.setFormat(cursor.getString(3));
                movie.setLenght(Integer.parseInt(cursor.getString(4)));
                movie.setGenre(cursor.getString(5));
                movie.setPath(cursor.getString(6));
                MainActivity.ArrayOfTitles.add(movie.getTitle());
                titleList.add(movie);
            }
            while (cursor.moveToNext());
        }
        // return Movie list
        return titleList;
    }
    //Query database for title (first match only, use SearchForTitles to get all matches!) method
    public Movie findTitle(String movieTitle){
        String query = "Select * FROM " + TABLE_MOVIES + " WHERE " + COLUMN_TITLE + " = \"" + movieTitle + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Movie movie = new Movie();
        //Limited only to first match
        if(cursor.moveToFirst()){
            movie.setID(Integer.parseInt(cursor.getString(0)));
            movie.setTitle(cursor.getString(1));
            movie.setYear(Integer.parseInt(cursor.getString(2)));
            movie.setFormat(cursor.getString(3));
            movie.setLenght(Integer.parseInt(cursor.getString(4)));
            movie.setGenre(cursor.getString(5));
            movie.setPath(cursor.getString(6));
        }
        else
        {
            movie = null ; //No match
        }
        db.close();
        return movie;
    }
    //Get all Movies containing search string from database method
    public List<Movie> SearchForTitles(String movieTitle){
        List<Movie> titleList = new ArrayList<Movie>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES + " WHERE " + COLUMN_TITLE + " = \"" + movieTitle + "";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do {
                Movie movie = new Movie();
                movie.setID(Integer.parseInt(cursor.getString(0)));
                movie.setTitle(cursor.getString(1));
                movie.setYear(Integer.parseInt(cursor.getString(2)));
                movie.setFormat(cursor.getString(3));
                movie.setLenght(Integer.parseInt(cursor.getString(4)));
                movie.setGenre(cursor.getString(5));
                movie.setPath(cursor.getString(6));
                titleList.add(movie);
            }
            while (cursor.moveToNext());
        }
        // return Movie list
        return titleList;
    }
    //Get Movie by ID method
    public Movie getMovieID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        //Database query
        Cursor cursor = db.query(TABLE_MOVIES, new String[]{ COLUMN_ID, COLUMN_TITLE, COLUMN_YEAR, COLUMN_FORMAT,
            COLUMN_LENGHT, COLUMN_GENRE, COLUMN_PATH } , COLUMN_ID + "=?", new String[] { String.valueOf( id )} , null,null,null,null ) ;
        if(cursor != null)
            cursor.moveToFirst();

        Movie movie = new Movie() ;
        movie.setID(Integer.parseInt(cursor.getString(0)));
        movie.setTitle(cursor.getString(1));
        movie.setYear(Integer.parseInt(cursor.getString(2)));
        movie.setFormat(cursor.getString(3));
        movie.setLenght(Integer.parseInt(cursor.getString(4)));
        movie.setGenre(cursor.getString(5));
        movie.setPath(cursor.getString(6));
        return movie ;
    }
    //Delete Movie from database by title method
    public boolean deleteMovie(String movieName) {
        boolean result = false;
        //Database query for title
        String query = "Select * FROM " + TABLE_MOVIES + " WHERE " + COLUMN_TITLE + " =  \"" + movieName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Movie movie = new Movie();

        if (cursor.moveToFirst()) {
            movie.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_MOVIES, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(movie.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    //Delete Movie from database by ID method
    public void deleteMovieID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }
    //Get Movies count method
    public int getMoviesCount(){
        String countQuery = "SELECT * FROM " + TABLE_MOVIES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        //Return number of movies
        return cursor.getCount();
    }
}
