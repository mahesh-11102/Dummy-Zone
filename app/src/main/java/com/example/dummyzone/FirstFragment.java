package com.example.dummyzone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {


    private RequestQueue mRequestQueue;
    private ArrayList<DummyZoneFeedModal> instaModalArrayList;
    private ArrayList<DummyZoneFeedModal> dummyZoneFeedModalArrayList;
    private ProgressBar progressBar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        // initializing our views.
        progressBar = view.findViewById(R.id.idLoadingPB);

        // calling method to load
        // data in recycler view.
        getDummyZoneFeeds(view);
        // Inflate the layout for this fragment
        return view;
    }
    private void getDummyZoneFeeds(View view) {
        dummyZoneFeedModalArrayList = new ArrayList<>();

        // below line is use to initialize the variable for our request queue.
        mRequestQueue = Volley.newRequestQueue(getActivity());


        mRequestQueue.getCache().clear();

        // below is the url stored in
        // string for our sample data
        String url = "https://jsonkeeper.com/b/DSFO";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                try {
                    // in below line we are extracting the data from json object.
                    String authorName = response.getString("authorName");
                    String authorImage = response.getString("authorImage");

                    // below line is to get json array from our json object.
                    JSONArray feedsArray = response.getJSONArray("feeds");

                    // running a for loop to add data to our array list
                    for (int i = 0; i < feedsArray.length(); i++) {
                        // getting json object of our json array.
                        JSONObject feedsObj = feedsArray.getJSONObject(i);

                        // extracting data from our json object.
                        String postDate = feedsObj.getString("postDate");
                        String postDescription = feedsObj.getString("postDescription");
                        String postIV = feedsObj.getString("postIV");
                        String postLikes = feedsObj.getString("postLikes");
                        String postComments = feedsObj.getString("postComments");

                        // adding data to our modal class.
                        DummyZoneFeedModal feedModal = new DummyZoneFeedModal(authorImage, authorName, postDate, postDescription, postIV, postLikes, postComments);
                        dummyZoneFeedModalArrayList.add(feedModal);

                        // below line we are creating an adapter class and adding our array list in it.

                        DummyZoneFeedRVAdapter adapter = new DummyZoneFeedRVAdapter(dummyZoneFeedModalArrayList, getActivity());
                        RecyclerView instRV = view.findViewById(R.id.idRVInstaFeeds);

                        // below line is for setting linear layout manager to our recycler view.
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

                        // below line is to set layout
                        // manager to our recycler view.
                        instRV.setLayoutManager(linearLayoutManager);

                        // below line is to set adapter
                        // to our recycler view.
                        instRV.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Fail to get data with error " + error, Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }
}