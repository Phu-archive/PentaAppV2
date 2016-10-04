package thevcgroup.pentachannel.com.pentav2;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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


public class Fragment_video extends Fragment{

    RecyclerView recyclerView;
    ArrayList<DetailedVideo> detailedVideos = new ArrayList<>();
    Adapter_DetailedVideo adapter;
    LinearLayoutManager layoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;

    Boolean hasNextPage = true;
    String currentTag = "83";
    Integer currentPage;
    Integer totalPage;

    LoadMoreItems loadMoreItems;


    public Fragment_video() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment_video, container, false);

        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://www.pentachannel.com/api/v2/link/tag/" + currentTag +"/?page=1&per_page=20");

        recyclerView = (RecyclerView) view.findViewById(R.id.video_recycler_view);
        adapter = new Adapter_DetailedVideo(getContext(),detailedVideos);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();

//        http://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();


                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount && hasNextPage  && currentPage < totalPage)
                        {
                            loading = false;
                            currentPage = currentPage + 1;
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    DownloadData downloadData = new DownloadData();
                                    downloadData.execute("http://www.pentachannel.com/api/v2/link/tag/" + currentTag +"/?page=" + currentPage +"&per_page=20");
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
        currentTag = tagQuery.getId();
        downloadData.execute("http://www.pentachannel.com/api/v2/link/tag/" + currentTag +"/?page=1&per_page=20");

        detailedVideos.clear();
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
                hasNextPage = Boolean.valueOf(data.getString("has_other_pages"));
                currentPage = data.getInt("page");
                totalPage = data.getInt("num_page");

                Log.i("test",currentPage.toString());

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

                    loading = true;

                    adapter.notifyDataSetChanged();

//                    Log.i("test", String.valueOf(adapter != null));
//                    Log.i("test",detailedVideos.get(i).getName_video());

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public void onAttach(Activity activity) {
//        loadMoreItems = (LoadMoreItems) activity;
//        super.onAttach(activity);
//    }
}

