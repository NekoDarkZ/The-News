package cl.ucn.disc.dsm.smurquio.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

  /**
   * The News Adapter.
   */
  protected NewsAdapter newsAdapter;

  /**
   *
   * @param savedInstanceState the state.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Objects.requireNonNull(getSupportActionBar()).setTitle("The News");
    getSupportActionBar().setSubtitle("Universidad Católica del Norte");

    // Get the List(RecyclerView).
    final RecyclerView recyclerView = findViewById(R.id.am_rv_news);
    // The type of layout of RecyclerView.
    recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    // Build the Adapter.
    this.newsAdapter = new NewsAdapter();

    // Union of Adapter + RecyclerView
    recyclerView.setAdapter(this.newsAdapter);

    // Build the ViewModel
    NewsViewModel newsViewModel = ViewModelProvider // Provider
        .AndroidViewModelFactory // The Factory
        .getInstance(this.getApplication()) // The Singleton instance of Factory
        .create(NewsViewModel.class); // Call the Constructor of NewsViewModel

    // Watch the List of News
    newsViewModel.getNews().observe(this, news -> {
      // Set the news (from ViewModel)
      newsAdapter.setNews(news);
      // Refresh the Recycler (ListView)
      newsAdapter.notifyDataSetChanged();
    });
  }

  @Override
  protected void onStart() {
    super.onStart();

    //Run in the background.
    AsyncTask.execute(() -> {

      List<News> theNews;

      //Read the news.json
      try(final InputStream is = super.getApplication().getAssets().open("news.json")){

        //Get the type of List<News> with reflection
        final Type newsListType = new TypeToken<List<News>>(){}.getType();

        //The json to object convert
        final Gson gson = new GsonBuilder().create();

        //The Reader
        final Reader reader = new InputStreamReader(is);

        //Google gson Black magic
        theNews = gson.fromJson(reader, newsListType);

      }catch (IOException e) {
        e.printStackTrace();
        return;
      }

      // Sort by name
      theNews.sort(Comparator.comparing(News::getPublishedAt));

      // Populate the adapter.
      this.newsAdapter.setNews(theNews);
      // Notify / Update the GUI.
      runOnUiThread(() ->  {
        // Run in UI Thread.
        this.newsAdapter.notifyDataSetChanged();
      });
    });
  }
}