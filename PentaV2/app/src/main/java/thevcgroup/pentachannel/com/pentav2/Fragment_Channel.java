package thevcgroup.pentachannel.com.pentav2;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.channels.Channel;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fragment_Channel extends Fragment {

    ArrayList<ChannelData> channelDatas = new ArrayList<>();
    RecyclerView recyclerView;
    Adapter_Channel adapter;


    public Fragment_Channel() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment__channel, container, false);

        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://www.pentachannel.com/api/v2/channel/tag/83/?page=1&per_page=20");

        recyclerView = (RecyclerView) view.findViewById(R.id.channel_recycler_view);
        adapter = new Adapter_Channel(getContext(),channelDatas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));


        return view;
    }

    class DownloadData extends AsyncTask<String, Void, String> {

        String data;


        public DownloadData() {
        }

        public String doInBackground(String... url) {
            data = "";

            try{
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url[0]).build();
                Response response = client.newCall(request).execute();
                data =  response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        public void onPostExecute(String result) {
            try {
                JSONObject data = new JSONObject(result);
                JSONArray arr = data.getJSONArray("channels");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject channel = arr.getJSONObject(i);
                    String ChannelName = channel.getString("name");
                    String ChannelFollower = channel.getString("follower");
                    String ChannelImg = channel.getString("real_icon");
                    String ChannelID = channel.getString("id");

                    channelDatas.add(new ChannelData(ChannelImg,ChannelName,ChannelFollower,ChannelID));
                    adapter.notifyDataSetChanged();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
