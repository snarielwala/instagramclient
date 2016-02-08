package poppicsinsta.com.instawindow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import poppicsinsta.com.instawindow.CircleTransform;
import poppicsinsta.com.instawindow.DeviceDimensionsHelper;
import poppicsinsta.com.instawindow.InstagramPhoto;
import poppicsinsta.com.instawindow.R;

/**
 * Created by snarielwala on 2/6/16.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    //Context and the data source
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //view holder cache for the view items
        ViewHolder holder;

        //get data item for this position
        InstagramPhoto photo = getItem(position);

        //check if we have a recycled view, if not we need to inflate
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        //Setting Text View texts from the model
        holder.tvUserName.setText(photo.getUserName());
        holder.tvCaption.setText(photo.getCaption());
        holder.tvLikes.setText(photo.getLikesCount() + " likes");

        //Clearing out the image resource
        holder.ivPhoto.setImageResource(0);
        holder.ivProfilePhoto.setImageResource(0);

       // int displayWidth = DeviceDimensionsHelper.getDisplayWidth(getContext());
       // int displayHeight = (displayWidth)*((photo.getImgHeight())/photo.getImgWidth());

      //      holder.ivPhoto.getLayoutParams().height = displayHeight;

        //Setting profile picture and image using picasso
        Picasso.with(getContext()).load(photo.getImageUrl()).placeholder(R.drawable.placeholder).into(holder.ivPhoto);
        Picasso.with(getContext()).load(photo.getProfilePictureUrl()).transform(new CircleTransform()).into(holder.ivProfilePhoto);

        //return created view
        return convertView;
    }

    //static innter class facilitates usage of butterknife
    static class ViewHolder {

        @Bind(R.id.tvUname)
        TextView tvUserName;

        @Bind(R.id.tvCaption)
        TextView tvCaption;

        @Bind(R.id.tvLikes)
        TextView tvLikes;

        @Bind(R.id.ivPhoto)
        ImageView ivPhoto;

        @Bind(R.id.ivProfilePhoto)
        ImageView ivProfilePhoto;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

}
