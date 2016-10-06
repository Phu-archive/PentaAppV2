package thevcgroup.pentachannel.com.pentav2;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
    GridLayoutManager gridLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;

    Boolean has_more_page;
    String currentTag = "83";
    Integer currentPage;
    Integer totalPage;


    public Fragment_Channel() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment__channel, container, false);

        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://www.pentachannel.com/api/v2/channel/tag/"+ currentTag +"/?page=1&per_page=20");

        recyclerView = (RecyclerView) view.findViewById(R.id.channel_recycler_view);
        adapter = new Adapter_Channel(getContext(),channelDatas);
        recyclerView.setAdapter(adapter);
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(),ActivityChannelPage.class);
                intent.putExtra("ChannelName",channelDatas.get(position).getCh_name());
                intent.putExtra("ChannelID",channelDatas.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = gridLayoutManager.getChildCount();
                    totalItemCount = gridLayoutManager.getItemCount();
                    pastVisiblesItems = gridLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount && has_more_page && currentPage < totalPage)
                        {
                            loading = false;
                            currentPage = currentPage + 1;
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    DownloadData downloadData = new DownloadData();
                                    downloadData.execute("http://www.pentachannel.com/api/v2/channel/tag/"+ currentTag +"/?page=" + currentPage + "&per_page=20");
                                }
                            }, 1000);
                        }
                    }
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });


        return view;
    }

    public void NavClick(TagQuery tagQuery){
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://www.pentachannel.com/api/v2/channel/tag/"+ tagQuery.getId() +"/?page=1&per_page=20");

        currentTag = tagQuery.getId();

        channelDatas.clear();
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

                has_more_page = Boolean.valueOf(data.getString("has_other_pages"));
                currentPage = data.getInt("page");
                totalPage = data.getInt("num_page");

                Log.i("test",data.getString("num_page"));

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject channel = arr.getJSONObject(i);
                    String ChannelName = channel.getString("name");
                    String ChannelFollower = channel.getString("follower");
                    String ChannelImg = channel.getString("real_icon");
                    String ChannelID = channel.getString("id");

                    channelDatas.add(new ChannelData(ChannelImg,ChannelName,ChannelFollower,ChannelID));
                    adapter.notifyDataSetChanged();

                    loading = true;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
