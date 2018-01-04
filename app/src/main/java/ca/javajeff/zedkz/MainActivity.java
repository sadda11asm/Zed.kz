package ca.javajeff.zedkz;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ApiAdapter.ApiAdapterOnClickHandler {
    private ApiAdapter mAdapter;
    private TextView mErrorTextView;
    public ArrayList<String> names = new ArrayList<>();
    public ArrayList<String> ids = new ArrayList<>();
    public ArrayList<String> surnames = new ArrayList<>();

    private RecyclerView mRecyclerView;
    ProgressBar mLoadingIndicator;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                refreshContent();
            }
        });

        mErrorTextView = (TextView) findViewById(R.id.error);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ApiAdapter(this);

        mRecyclerView.setAdapter(mAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.indicator);
        loadApiData();
    }

    private void showInfoDataView() {
        mErrorTextView.setVisibility(View.INVISIBLE);

        mRecyclerView.setVisibility(View.VISIBLE);
    }
    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);

        mErrorTextView.setVisibility(View.VISIBLE);
    }
    @Override
    public void onClick(String id) {

    }

    public class FetchInfoTask extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
        @Override
        protected JSONArray doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            URL weatherRequestUrl = NetworkUtils.buildUrl();

            try {
                String jsonApiResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                OpenAPIJsonUtils
                        .getSimpleInfoStringsFromJson(MainActivity.this, jsonApiResponse);
                Log.v("fsdf", OpenAPIJsonUtils.Arr.toString());
                return OpenAPIJsonUtils.Arr;

            } catch (Exception e) {
                e.printStackTrace();
                showErrorMessage();
                Log.v("fsdf", "error");
                return null;
            }
        }
        @Override
        protected void onPostExecute(JSONArray resultJSONString) {

            mLoadingIndicator.setVisibility(View.INVISIBLE);

            //String [] ids = new String[0];
            //String[] surnames = new String[0];
            //Log.i("resultJSON",resultJSONString.toString());
            if (resultJSONString==null) {
                showErrorMessage();
                return;
            }
            for (int i=0;i<resultJSONString.length();i++) {
                try {
                    JSONObject object = resultJSONString.getJSONObject(i);
                    Log.v("objectJSON", object.toString());
                    String name = "first_name";
                    //Log.v("idss", object.getString("id"));
                    names.add("  "+ object.getString("id")+ ".  " + object.getString(name));
                    //surnames[i] = object.getString("last_name").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v("error", "object");
                }
            }
            if (resultJSONString != null) {
                showInfoDataView();
                Log.i("names", names.toString());
                //Log.i("ids", ids.toString());
                //Log.i("surnames", surnames.toString());
                mAdapter.setInfoData(names);
            } else {
                showErrorMessage();
            }
        }
    }
    private void refreshContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter = new ApiAdapter(MainActivity.this);
                mAdapter.setInfoData(null);
                loadApiData();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
    private void loadApiData() {
        URL queryUrl = NetworkUtils.buildUrl();
        new FetchInfoTask().execute(queryUrl.toString());
    }

}
