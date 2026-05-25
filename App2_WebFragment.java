package edu.uic.cs478.s2026.project3app2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WebFragment extends Fragment {

    // ---------------------------------------------------------------
    // Keys
    // ---------------------------------------------------------------
    private static final String ARG_URL   = "arg_url";
    private static final String STATE_URL = "state_url";

    // ---------------------------------------------------------------
    // Fields
    // ---------------------------------------------------------------
    private WebView     webView;
    private ProgressBar progressBar;
    private String      currentUrl;

    // ---------------------------------------------------------------
    // Factory
    // ---------------------------------------------------------------
    public static WebFragment newInstance(String url) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------
    // Lifecycle
    // ---------------------------------------------------------------

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (savedInstanceState != null) {
            currentUrl = savedInstanceState.getString(STATE_URL);
        } else if (getArguments() != null) {
            currentUrl = getArguments().getString(ARG_URL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        webView     = view.findViewById(R.id.webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,
                                                    WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url,
                                      android.graphics.Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                currentUrl = url;
            }
        });

        if (currentUrl != null && !currentUrl.isEmpty()) {
            webView.loadUrl(currentUrl);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (webView != null) {
            outState.putString(STATE_URL, webView.getUrl());
        }
    }

    // ---------------------------------------------------------------
    // Public API
    // ---------------------------------------------------------------

    public void loadUrl(String url) {
        currentUrl = url;
        if (webView != null) {
            webView.loadUrl(url);
        }
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    /**
     * Called by the host Activity when the back button is pressed.
     * Returns true if the WebView consumed the back press (went back a page).
     * Returns false if the WebView has no more history (Activity should handle it).
     */
    public boolean handleBackPress() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
            return true;  // WebView handled it — stay on screen
        }
        return false;     // WebView has no history — Activity should collapse panel
    }
}
