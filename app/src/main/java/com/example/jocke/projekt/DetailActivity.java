package com.example.jocke.projekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SearchView;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.Html;

import com.squareup.picasso.Picasso;

import static com.example.jocke.projekt.MainActivity.CREATOR;
import static com.example.jocke.projekt.MainActivity.CREATOR_URL;
import static com.example.jocke.projekt.MainActivity.DOWNLOADS;
import static com.example.jocke.projekt.MainActivity.LIKES;
import static com.example.jocke.projekt.MainActivity.URL;
import static com.example.jocke.projekt.MainActivity.VIEWS;

public class DetailActivity extends AppCompatActivity {
    public static final String QUERY = "";

    private MenuItem searchItem;
    private MenuItem homeItem;
    private MenuItem filterItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_detail);

        Intent intent = getIntent();
        String url = intent.getStringExtra(URL);
        String creator = intent.getStringExtra(CREATOR);
        String creatorUrl = intent.getStringExtra(CREATOR_URL);
        int views = intent.getIntExtra(VIEWS, 0);
        int downloads = intent.getIntExtra(DOWNLOADS, 0);
        int likes = intent.getIntExtra(LIKES, 0);

        ImageView imageView = findViewById(R.id.imageViewDetail);
        TextView creatorText = findViewById(R.id.creatorTextView);
        TextView likeText = findViewById(R.id.likesTextView);
        TextView downloadText = findViewById(R.id.downloadsTextView);
        TextView viewText = findViewById(R.id.viewsTextView);

        Picasso.with(this).load(url).fit().centerInside().into(imageView);

        creatorText.setText(Html.fromHtml("<a href='"+creatorUrl+"'>"+creator+"</a>"));
        creatorText.setMovementMethod(LinkMovementMethod.getInstance());

        likeText.setText("Likes: "+likes);
        downloadText.setText("Downloads: "+downloads);
        viewText.setText("Views: "+views);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        searchItem = menu.findItem(R.id.searchMenu);
        filterItem = menu.findItem(R.id.filterMenu);
        homeItem = menu.findItem(R.id.homeMenu);
        homeItem.setOnMenuItemClickListener( new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.homeMenu) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                return true;
            }

        });

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(QUERY, query);
                startActivity(intent);
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

}
