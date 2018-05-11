package com.example.jocke.projekt;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import static com.example.jocke.projekt.DetailActivity.QUERY;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener {
    public static final String URL = "URL";
    public static final String CREATOR = "CREATOR";
    public static final String CREATOR_URL = "CREATOR URL";
    public static final String LIKES = "LIKES";
    public static final String VIEWS = "VIEWS";
    public static final String DOWNLOADS = "DOWNLOADS";

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private ArrayList<Picture> mPictureList;
    private RequestQueue mRequestQueue;
    private MenuItem searchItem;
    private MenuItem filterItem;
    private static String imageType;
    private static String mOrientation;
    private static Boolean mSafesearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPictureList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);

        imageType = "all";
        mOrientation = "all";
        mSafesearch = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String query;
        if (intent.getStringExtra(QUERY) == null) {
            query = "puppy";
        } else {
            query = intent.getStringExtra(QUERY);
        }

        parseJSON(query);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.homeMenu) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        searchItem = menu.findItem(R.id.searchMenu);
        SearchView searchView = (SearchView) searchItem.getActionView();
        filterItem = menu.findItem(R.id.filterMenu);

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                parseJSON(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        filterItem.setOnMenuItemClickListener( new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(getApplicationContext(), FilterActivity.class));
                return false;
            }
        });

        return true;
    }

    public void parseJSON(String searchTerm) {
        String url = "https://pixabay.com/api/?key=8954619-7f011a57ce5ae185a502c4ecf&q="+searchTerm+"&image_type="+imageType+"&orientation="+mOrientation+"&safesearch="+mSafesearch;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");
                            mPictureList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String creatorName = hit.getString("user");
                                String creatorUrl = hit.getString("pageURL");
                                String imageUrl = hit.getString("webformatURL");
                                int views = hit.getInt("views");
                                int downloads = hit.getInt("downloads");
                                int likes = hit.getInt("likes");

                                mPictureList.add(new Picture(imageUrl, creatorName, creatorUrl, views, downloads, likes));
                            }

                            mAdapter = new Adapter(MainActivity.this, mPictureList);
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.setOnItemClickListener(MainActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent (this, DetailActivity.class);
        Picture picture = mPictureList.get(position);
        intent.putExtra(URL, picture.getUrl());
        intent.putExtra(CREATOR, picture.getCreator());
        intent.putExtra(DOWNLOADS, picture.getDownloads());
        intent.putExtra(VIEWS, picture.getViews());
        intent.putExtra(LIKES, picture.getLikes());
        intent.putExtra(CREATOR_URL, picture.getCreatorUrl());

        startActivity(intent);
    }

    public static void changeFilter(int type, int orientation, boolean safesearch) {

        switch (type) {
            case R.id.type_illustration:
                imageType = "illustration";
                break;
            case R.id.type_photo:
                imageType = "photo";
                break;
            case R.id.type_vector:
                imageType = "vector";
                break;
            default: imageType = "all";
        }

        switch (orientation) {
            case R.id.orientation_horizontal:
                mOrientation = "horizontal";
                break;
            case R.id.orientation_vertical:
                mOrientation = "vertical";
                break;
            default: mOrientation = "all";
        }

        mSafesearch = safesearch;


    }
}
