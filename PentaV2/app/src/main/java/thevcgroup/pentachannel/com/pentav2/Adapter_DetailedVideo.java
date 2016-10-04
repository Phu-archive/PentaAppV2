package thevcgroup.pentachannel.com.pentav2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by phu on 3/10/2559.
 */

public class Adapter_DetailedVideo extends RecyclerView.Adapter<Adapter_DetailedVideo.ViewHolder>{

    private ArrayList<DetailedVideo> detailedVideos;
    private Context mContext;
    private final int VIEW_ITEM = 1;
    Adapter_DetailedVideo.ViewHolder viewHolder;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView video_img;
        public TextView video_name;
        public ImageView ch_img;
        public TextView ch_name;
        public TextView date_created;
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            video_img = (ImageView) itemView.findViewById(R.id.detailedVideo_img);
            video_name = (TextView) itemView.findViewById(R.id.detailedVideo_name);
            ch_img = (ImageView) itemView.findViewById(R.id.detailedVideo_imgCh);
            ch_name = (TextView) itemView.findViewById(R.id.detailedVideo_nameCh);
            date_created = (TextView) itemView.findViewById(R.id.detailedVideo_dateCreated);
        }

        public ImageView getCh_img() {
            return ch_img;
        }

        public ImageView getVideo_img() {
            return video_img;
        }

        public TextView getCh_name() {
            return ch_name;
        }

        public TextView getDate_created() {
            return date_created;
        }

        public TextView getVideo_name() {
            return video_name;
        }
    }
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }

    public Adapter_DetailedVideo(Context context, ArrayList<DetailedVideo> videos){
        detailedVideos = videos;
        mContext = context;
    }

    @Override
    public Adapter_DetailedVideo.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_detailedvideo, parent, false);
        viewHolder = new Adapter_DetailedVideo.ViewHolder(contactView);
        return viewHolder;



//        if (viewType == VIEW_ITEM){
//            View contactView = inflater.inflate(R.layout.adapter_detailedvideo, parent, false);
//            viewHolder = new Adapter_DetailedVideo.ViewHolder(contactView);
//        }else{
//            View contactView = inflater.inflate(R.layout.progressbar, parent, false);
//            viewHolder = new Adapter_DetailedVideo.ProgressViewHolder(contactView);
//        }
//        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageView video_img = holder.getVideo_img();
        Picasso.with(mContext).load(detailedVideos.get(position).getUrl_video_img()).into(video_img);

        ImageView ch_img = holder.getCh_img();
        Picasso.with(mContext).load(detailedVideos.get(position).getUrl_channel_img()).into(ch_img);

        TextView ch_name = holder.getCh_name();
        ch_name.setText(detailedVideos.get(position).getName_channel());

        TextView video_name = holder.getVideo_name();
        video_name.setText(detailedVideos.get(position).getName_video());

        TextView date_created = holder.getDate_created();
        date_created.setText(detailedVideos.get(position).getCreated_time());
    }

    @Override
    public int getItemCount() {
        return detailedVideos.size();
    }

}
