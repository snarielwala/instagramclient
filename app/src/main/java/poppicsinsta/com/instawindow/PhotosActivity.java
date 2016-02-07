package poppicsinsta.com.instawindow;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import poppicsinsta.com.instawindow.adapters.InstagramPhotosAdapter;

public class PhotosActivity extends AppCompatActivity {

    public static final String instagramApi="https://api.instagram.com/v1/media/popular?client_id=";
    public static final String client_id="e05c462ebd86446ea48a5af73769b602";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;

    //binding listview using butterknife
    @Bind(R.id.lvPhotos) ListView lvPhotos;
    @Bind(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_activity);
        ButterKnife.bind(this);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularPhotos();
            }
        });

        swipeContainer.setColorSchemeResources(R.color.colorPrimaryDark);

    //Send Out API Request to Photos
        photos=new ArrayList<>();

        //hook up the adapter to the data source
        aPhotos=new InstagramPhotosAdapter(this,photos);

        //bind adapter to listview
        lvPhotos.setAdapter(aPhotos);

        //bind the adapter to the list view
        fetchPopularPhotos();

        }

    //fetching popular photos and adding them to the data source
    private void fetchPopularPhotos(){

        //Sending a Request to the Instagram API
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(instagramApi + client_id, null, new JsonHttpResponseHandler() {

            //TA-DA .. it worked ! :)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                aPhotos.clear();

                JSONArray photosJSON = null;
                try {
                    //get the photos array
                    photosJSON = response.getJSONArray("data");

                    //iterate through the array
                    for (int i = 0; i < photosJSON.length(); i++) {

                        JSONObject photoJSON = photosJSON.getJSONObject(i);

                        InstagramPhoto photo = new InstagramPhoto();

                        //parsing the username from the json
                        photo.setUserName(photoJSON.getJSONObject("user").getString("username"));
                        //parsing the profile picture url from the json
                        photo.setProfilePictureUrl(photoJSON.getJSONObject("user").getString("profile_picture"));
                        //parsing the caption from the json
                        photo.setCaption(photoJSON.getJSONObject("caption").getString("text"));
                        //parsing the photo url from the json
                        photo.setImageUrl(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        //parsing the height of the image from the json
                        photo.setImgHeight(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
                        //parsing the width of the image from the json
                        photo.setImgWidth(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("width"));
                        //parsing the number of likes from the json
                        photo.setLikesCount(photoJSON.getJSONObject("likes").getInt("count"));

                        photos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                aPhotos.addAll(photos);
                //notify the adapter that dataset has changed and that the view should be updated
                aPhotos.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            //Oops...it failed :(
        });

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
