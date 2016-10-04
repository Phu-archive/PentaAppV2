package thevcgroup.pentachannel.com.pentav2;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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


public class Fragment_video extends Fragment{

    RecyclerView recyclerView;
    ArrayList<DetailedVideo> detailedVideos = new ArrayList<>();
    Adapter_DetailedVideo adapter;


    public Fragment_video() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_video, container, false);

        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://www.pentachannel.com/api/v2/link/tag/83/?page=1&per_page=20");

        recyclerView = (RecyclerView) view.findViewById(R.id.video_recycler_view);
        adapter = new Adapter_DetailedVideo(getContext(),detailedVideos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
                JSONArray arr = data.getJSONArray("links");


                for (int i = 0; i < arr.length(); i++) {

                    JSONObject ch = arr.getJSONObject(i);
                    JSONArray description = ch.getJSONArray("description");
                    JSONObject chData = description.getJSONObject(0);

                    String video_img = ch.getString("real_thumbnail");
                    String ch_img = chData.getString("channel_image");
                    String video_name = ch.getString("name");
                    String ch_name = chData.getString("channel_name");
                    String created_date = ch.getString("create_at");

                    detailedVideos.add(new DetailedVideo(video_img,ch_img,video_name,ch_name,created_date));

                    adapter.notifyDataSetChanged();

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

