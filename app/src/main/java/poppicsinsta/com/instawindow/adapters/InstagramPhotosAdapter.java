package poppicsinsta.com.instawindow.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import poppicsinsta.com.instawindow.CircleTransform;
import poppicsinsta.com.instawindow.InstagramPhoto;
import poppicsinsta.com.instawindow.R;

/**
 * Created by snarielwala on 2/6/16.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {


    //Context and the data source

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context,android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //get data item for this position
        InstagramPhoto photo = getItem(position);

        //check if we have a recycled view, if not we need to inflate
        //lookup views for populating data
        //return created item as a view
        if(convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        TextView tvUserName= (TextView) convertView.findViewById(R.id.tvUname);
        TextView tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);
        TextView tvLikes=(TextView)convertView.findViewById(R.id.tvLikes);
        ImageView ivPhoto= (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivProfilePhoto = (ImageView) convertView.findViewById(R.id.ivProfilePhoto);

        tvUserName.setText(photo.getUserName());
        tvCaption.setText(photo.getCaption());
        tvLikes.setText(photo.getLikesCount()+" likes");

        ivPhoto.setImageResource(0);
        ivProfilePhoto.setImageResource(0);
        //insert image using picasso
        Picasso.with(getContext()).load(photo.getImageUrl()).fit().centerInside().into(ivPhoto);
        Picasso.with(getContext()).load(photo.getProfilePictureUrl()).transform(new CircleTransform()).into(ivProfilePhoto);

        return convertView;
    }

}
