package poppicsinsta.com.instawindow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import poppicsinsta.com.instawindow.adapters.InstagramPhotosAdapter;

public class PhotosActivity extends AppCompatActivity {

    public static final String client_id="e05c462ebd86446ea48a5af73769b602";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_activity);

        //Send Out API Request to Photos
        photos=new ArrayList<>();

        //hook up the adapter to the data source
        aPhotos=new InstagramPhotosAdapter(this,photos);

        //find the list view from the view
        ListView lvPhotos=(ListView)findViewById(R.id.lvPhotos);

        //bind adapter to listview
        lvPhotos.setAdapter(aPhotos);

        //bind the adapter to the list view
        fetchPopularPhotos();

        }

    //Sending a Request to the Instagram API
    private void fetchPopularPhotos(){
      /*Url:  https://api.instagram.com/v1/media/popular?client_id=e05c462ebd86446ea48a5af73769b602

        */
        String url = "https://api.instagram.com/v1/media/popular?client_id="+client_id;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url,null, new JsonHttpResponseHandler(){

            //TA-DA .. it worked ! :)

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            /*
            Type: data => [x] => type (“image” or “photo")
                Photo Url: data=>[x] => “images” => “standard_resoultion” => “url”
        Username: data => [x] => “caption” => “text”

        Caption: data => [x] => “user” => “username”
             */
                JSONArray photosJSON=null;
                try{
                    //get the photos array
                photosJSON = response.getJSONArray("data");

                    //iterate through the array
                    for(int i=0;i<photosJSON.length();i++){
                        JSONObject photoJSON=photosJSON.getJSONObject(i);
                        InstagramPhoto photo=new InstagramPhoto();
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        //photo.caption=photoJSON.getJSONObject("caption").getString("text");
                        //photo.imageUrl=photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        //photo.imgHeight=photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        //photo.likesCount=photoJSON.getJSONObject("likes").getInt("count");

                        photos.add(photo);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                Log.i("DEBUG",response.toString());
                aPhotos.notifyDataSetChanged();
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
