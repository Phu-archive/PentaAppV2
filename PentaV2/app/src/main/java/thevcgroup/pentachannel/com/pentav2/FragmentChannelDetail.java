package thevcgroup.pentachannel.com.pentav2;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FragmentChannelDetail extends Fragment {

    String videoImg;
    String videoName;
    String videoType;
    String detail;
    String videoID;

    String youtubePlayer = "youtube";

    String channelID;

    ArrayList<ChannelDetailed> channelDetaileds = new ArrayList<>();
    RecyclerView recyclerView;
    Adapter_ChannelDetailed adapter_channelDetailed;
    LinearLayoutManager linearLayoutManager;


    public FragmentChannelDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_channel_detail, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.ChannelDetail_recycler_view);
        adapter_channelDetailed = new Adapter_ChannelDetailed(getContext(),channelDetaileds);
        recyclerView.setAdapter(adapter_channelDetailed);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(channelDetaileds.get(position).getVideoType().equals("youtube")) {
                    Intent intent = new Intent(getActivity(), ActivityVideoPage.class);
                    intent.putExtra("type", channelDetaileds.get(position).getVideoType());
                    intent.putExtra("name", channelDetaileds.get(position).getVideoName());
                    intent.putExtra("id", channelDetaileds.get(position).getVideoID());
                    startActivity(intent);
                }else{
                    Snackbar.make(view, channelDetaileds.get(position).getVideoType() + " player type have not implemeted", Snackbar.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        channelID = getActivity().getIntent().getExtras().getString("ChannelID");
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://pentachannel.com/apis/channel/channel_" + channelID +"/");



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
                JSONArray arr = data.getJSONArray("queue");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject playlist = arr.getJSONObject(i);
                    videoName = playlist.getString("name");
                    videoImg = playlist.getString("thumbnail");
                    videoType = playlist.getString("type");
                    detail = playlist.getString("detail");
                    videoID = playlist.getString("video_id");

                    Log.i("test",videoType);
                    Log.i("test",Boolean.valueOf(videoType.equals("youtube")).toString());

                    channelDetaileds.add(new ChannelDetailed(videoImg,videoName,videoType,detail,videoID));
                    adapter_channelDetailed.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
