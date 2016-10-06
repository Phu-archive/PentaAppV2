package thevcgroup.pentachannel.com.pentav2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityChannelPage extends AppCompatActivity {

    String videoImg;
    String videoName;
    String videoType;
    String detail;
    String videoID;

    String channelID;

    ArrayList<ChannelDetailed> channelDetaileds = new ArrayList<>();
    RecyclerView recyclerView;
    Adapter_ChannelDetailed adapter_channelDetailed;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("ChannelName"));

        recyclerView = (RecyclerView) findViewById(R.id.ChannelDetail_recycler_view);
        adapter_channelDetailed = new Adapter_ChannelDetailed(this,channelDetaileds);
        recyclerView.setAdapter(adapter_channelDetailed);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        channelID = getIntent().getExtras().getString("ChannelID");
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://pentachannel.com/apis/channel/channel_" + channelID +"/");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

                    Log.i("test",videoName);

                    channelDetaileds.add(new ChannelDetailed(videoImg,videoName,videoType,detail,videoID));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
