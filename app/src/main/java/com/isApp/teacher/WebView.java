package com.isApp.teacher;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.isApp.teacher.common.ColorOfStatusAndNavBar;
import com.isApp.teacher.databinding.ActivityWebViewBinding;



    public class WebView extends AppCompatActivity {

        private ActivityWebViewBinding binding;
        ProgressDialog progressDialog;
        String url;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            init();
            setListeners();

            binding.webview.getSettings().setJavaScriptEnabled(true);
            Bundle extra = getIntent().getExtras();
            if (extra != null) {
                url = extra.getString("url");
            }
            binding.webview.loadUrl(url);

            binding.webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(android.webkit.WebView view, String url) {
                    super.onPageFinished(view, url);
                    progressDialog.dismiss();
                    binding.webview.setVisibility(View.VISIBLE);
                }
            });


        }

        public void setListeners() {
            binding.backButton.setOnClickListener(v -> {
                onBackPressed();
                finish();
            });
        }


        public void init() {
            binding = ActivityWebViewBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            ColorOfStatusAndNavBar colorOfStatusAndNavBar = new ColorOfStatusAndNavBar();
            colorOfStatusAndNavBar.colorOfStatusBar(this);
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

    }

