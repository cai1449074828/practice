package com.blq.zzc.practice.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.blq.zzc.practice.R;
import com.blq.zzc.practice.base.LazySwipeBackActivity;
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;

public class XinWenDetailActivity extends LazySwipeBackActivity {
    @Bind(R.id.XinWenDetail_SimpleDraweeView)
    SimpleDraweeView mXinWenDetailSimpleDraweeView;
    @Bind(R.id.XinWenDetail_CollapsingToolbarLayout)
    CollapsingToolbarLayout mXinWenDetailCollapsingToolbarLayout;
    @Bind(R.id.xinWenDetail_webview)
    WebView mXinWenDetailWebview;
    @Bind(R.id.XinWen_AppBarLayout)
    AppBarLayout mXinWenAppBarLayout;
    @Bind(R.id.XinWen_Toolbar)
    Toolbar mXinWenToolbar;
    @Override
    public int getLayoutId() {
        return R.layout.activity_xin_wen;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getIntentInfo();
        initCollapsingToolbarLayout();
        initWebview();
    }
    private String title;
    private String imageUri;
    private String detailHtml;
    private void getIntentInfo() {
        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        imageUri = intent.getStringExtra("image");
        detailHtml=intent.getStringExtra("detailHtml");
    }

    private void initCollapsingToolbarLayout() {
        imageUri = "http://hiphotos.baidu.com/apistore/pic/item/c2fdfc039245d68883f2f560a1c27d1ed31b2405.jpg?timestamp=1436171652";
//        Glide.with(this).load(imageUri).placeholder(R.drawable.account_avatar).fitCenter().into(mXinWenDetailImageview);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageUri))
                .setResizeOptions(new ResizeOptions(500, 500))
                .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(mXinWenDetailSimpleDraweeView.getController())
                .setAutoPlayAnimations(true)
                .build();
        mXinWenDetailSimpleDraweeView.setController(draweeController);
    }

    private void initWebview() {
        String chuliHtmlQian = "<html><body>" +
                //以下代码用于解决webview中图片过大引起的webview可以向右滑动问题
                "<style>\n" +
                " \n" +
                "img{\n" +
                " max-width:80%;\n" +
                " height:auto;\n" +
                "}\n" +
                " \n" +
                "</style>";
        String chuliHtmlHou = "</body></html>";
        String chuliHtml = chuliHtmlQian + detailHtml + chuliHtmlHou;
        mXinWenDetailWebview.loadData(chuliHtml, "text/html; charset=utf-8", "utf-8");
    }

    @Override
    public void initToolBar() {
        mXinWenToolbar.setTitle(title);
        setSupportActionBar(mXinWenToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mXinWenAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset==0){
                    mXinWenDetailWebview.setEnabled(false);
                }
                else mXinWenDetailWebview.setEnabled(true);
            }
        });
    }
}
