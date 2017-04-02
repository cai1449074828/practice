package com.blq.zzc.practice.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.blq.zzc.practice.R;
import com.blq.zzc.practice.adapter.XinWenAdapter.XinWenRecyclerViewAdapter;
import com.blq.zzc.practice.base.LazyFragment;
import com.blq.zzc.practice.model.XinWenBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/3.
 */
public class YiYuanXinWenFragment extends LazyFragment{
    public void request(String requestString) {
        OkHttpClient mOkHttpClient=new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(requestString).addHeader("apikey","592e46b62cfe201c68bf7d9f18db11ee");
        //可以省略，默认是GET请求
        requestBuilder.method("GET",null);
        Request request = requestBuilder.build();
        Call mcall= mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("失败");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                    Log.i("wangshu", "cache---" + str);
                } else {
                    String string=response.body().string().toString();
                    System.out.println(string);
                    try {
                        JSONObject jsonObject=new JSONObject(string);
                        JSONArray jsonArray=jsonObject.getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("contentlist");
                        for (int i=0;i<=9;i++){
                            JSONObject jsonObjectBean=jsonArray.getJSONObject(i);
                            XinWenBean xinWenBean=new XinWenBean();
                            JSONArray jsonArrayImage=jsonObjectBean.getJSONArray("imageurls");
                            List<String> listImage=new ArrayList<String>();
                            for (int j=0;j<jsonArrayImage.length();j++){
                                listImage.add(jsonArrayImage.getJSONObject(j).getString("url"));
                            }
                            xinWenBean.setTitle(jsonObjectBean.getString("title"));
                            xinWenBean.setDetailHtml(jsonObjectBean.getString("html"));
                            xinWenBean.setImagesUrl(listImage);
                            xinWenBeanList.add(xinWenBean);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String str = response.networkResponse().toString();
                    Log.i("wangshu", "network---" + str);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerView.setAdapter(new XinWenRecyclerViewAdapter(getActivity(),xinWenBeanList));
                    }
                });
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jian_kang_zi_xun;
    }
    @Bind(R.id.xinwen_recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.xinwen_SwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private List<XinWenBean> xinWenBeanList=new ArrayList<>();
    @Override
    public void initViews() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        String httpUrl = "http://japi.juhe.cn/joke/content/text.from";
//                        String keyApi = "key=e78cbb893189bd5c3b31c96068bacc59";
//                        String httpArg = "page=1&pagesize=10";
                        String httpUrl = "http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
                        String httpArg = "channelId=5572a109b3cdc86cf39001e3&page=1&needHtml=1";
                        String requestString=new String();
                        requestString=httpUrl+"?"+httpArg;
//                        requestString=httpUrl+"?"+keyApi+"&"+httpArg;
                        request(requestString);
                    }
                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new XinWenRecyclerViewAdapter(getActivity(),xinWenBeanList));
    }
}
