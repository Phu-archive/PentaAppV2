package thevcgroup.pentachannel.com.pentav2;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Adapter_Channel extends RecyclerView.Adapter<Adapter_Channel.ViewHolder> {

    Context mContext;
    ArrayList<ChannelData> channelDatas = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView channelName;
        public TextView Follower;
        public ImageView channelImg;

        public ViewHolder(View itemView) {
            super(itemView);

            channelImg = (ImageView) itemView.findViewById(R.id.channel_img);
            Follower = (TextView) itemView.findViewById(R.id.channel_follower);
            channelName = (TextView) itemView.findViewById(R.id.channel_channelName);
        }

        public ImageView getChannelImg() {
            return channelImg;
        }

        public TextView getChannelName() {
            return channelName;
        }

        public TextView getFollower() {
            return Follower;
        }
    }

    public Adapter_Channel(Context context, ArrayList<ChannelData> channel){
        mContext = context;
        channelDatas = channel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.adapter_channel, parent, false);

        Adapter_Channel.ViewHolder viewHolder = new Adapter_Channel.ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ImageView channelImg = holder.getChannelImg();
        Picasso.with(mContext).load(channelDatas.get(position).getCh_img()).placeholder(R.drawable.test_img).into(channelImg);

        TextView channelName = holder.getChannelName();
        channelName.setText(channelDatas.get(position).getCh_name());

        TextView follower = holder.getFollower();
        follower.setText(channelDatas.get(position).getFollower());

    }

    @Override
    public int getItemCount() {
        return channelDatas.size();
    }


}
