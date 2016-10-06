package thevcgroup.pentachannel.com.pentav2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Adapter_ChannelDetailed extends RecyclerView.Adapter<Adapter_ChannelDetailed.ViewHolder>  {

    Context mContext;
    ArrayList<ChannelDetailed> channelDetailedArrayList = new ArrayList<>();


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView videoImg;
        TextView videoName;
        TextView videoDetail;

        public ViewHolder(View itemView) {
            super(itemView);

            videoImg = (ImageView) itemView.findViewById(R.id.channelDetailed_videoImg);
            videoName = (TextView) itemView.findViewById(R.id.channelDetailed_videoName);
            videoDetail = (TextView) itemView.findViewById(R.id.channelDetailed_details);
        }

        public ImageView getVideoImg() {
            return videoImg;
        }

        public TextView getVideoDetail() {
            return videoDetail;
        }

        public TextView getVideoName() {
            return videoName;
        }
    }

    public Adapter_ChannelDetailed(Context context, ArrayList<ChannelDetailed> channel){
        mContext = context;
        channelDetailedArrayList = channel;
    }

    @Override
    public Adapter_ChannelDetailed.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_detailedchannel, parent, false);

        Adapter_ChannelDetailed.ViewHolder viewHolder = new Adapter_ChannelDetailed.ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter_ChannelDetailed.ViewHolder holder, int position) {
        ImageView imgvideo = holder.getVideoImg();
        Picasso.with(mContext).load(channelDetailedArrayList.get(position).getVideoImg()).into(imgvideo);

        TextView namevideo = holder.getVideoName();
        namevideo.setText(channelDetailedArrayList.get(position).getVideoName());

        TextView detailedVideo = holder.getVideoDetail();
        if (channelDetailedArrayList.get(position).getVideoDetail() == " "){
            detailedVideo.setText("No detail");
        }
        detailedVideo.setText(channelDetailedArrayList.get(position).getVideoDetail());
    }

    @Override
    public int getItemCount() {
        return channelDetailedArrayList.size();
    }
}
