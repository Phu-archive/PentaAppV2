package thevcgroup.pentachannel.com.pentav2;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class NavigationDrawerFragment extends Fragment {

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<TagQuery> QueryData = new ArrayList<TagQuery>();
    RecyclerView recyclerView;


    public NavigationDrawerFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://pentachannel.com/api/v2/tag/query/");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);

        Navigation_Adapter adapter = new Navigation_Adapter(getContext(),QueryData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Thank for code http://stackoverflow.com/questions/24471109/recyclerview-onclick
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Log.i("test",QueryData.get(position).getName());

                Log.i("test",QueryData.get(position).getId());

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));


        return view;
    }
    public void setUp(DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        drawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(drawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
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
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    QueryData.add(new TagQuery(object.getString("id"),object.getString("name")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}



