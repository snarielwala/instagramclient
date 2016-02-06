package poppicsinsta.com.instawindow.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import poppicsinsta.com.instawindow.InstagramPhoto;

/**
 * Created by snarielwala on 2/6/16.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {


    //Context and the data source

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context,android.R.layout.simple_list_item_1, objects);
    }


}
