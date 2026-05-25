package edu.uic.cs478.s2026.project3app2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

/**
 * Displays Chicago restaurants.
 * Mirrors AttractionsActivity exactly but uses restaurant data.
 */
public class RestaurantsActivity extends AppCompatActivity
        implements ListFragment.OnPlaceSelectedListener {

    // ---------------------------------------------------------------
    // State keys
    // ---------------------------------------------------------------
    private static final String KEY_SELECTED_INDEX = "selected_index";
    private static final String KEY_SELECTED_URL   = "selected_url";
    private static final String KEY_WEB_VISIBLE    = "web_visible";

    // ---------------------------------------------------------------
    // Fragment tags
    // ---------------------------------------------------------------
    private static final String TAG_LIST = "tag_list";
    private static final String TAG_WEB  = "tag_web";

    // ---------------------------------------------------------------
    // Fields
    // ---------------------------------------------------------------
    private FrameLayout        containerList;
    private FrameLayout        containerWeb;
    private List<ChicagoPlace> restaurants;

    private int     selectedIndex = -1;
    private String  selectedUrl   = "";
    private boolean webVisible    = false;

    // ---------------------------------------------------------------
    // Lifecycle
    // ---------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        // Set up ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
            getSupportActionBar().setSubtitle(getString(R.string.subtitle_restaurants));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // ← adds back arrow
        }

        containerList = findViewById(R.id.containerList);
        containerWeb  = findViewById(R.id.containerWeb);

        restaurants = ChicagoData.getRestaurants();

        // Restore state if returning from a config change
        if (savedInstanceState != null) {
            selectedIndex = savedInstanceState.getInt(KEY_SELECTED_INDEX, -1);
            selectedUrl   = savedInstanceState.getString(KEY_SELECTED_URL, "");
            webVisible    = savedInstanceState.getBoolean(KEY_WEB_VISIBLE, false);
        }

        // Only add fragments on fresh start
        if (savedInstanceState == null) {
            addListFragment();
        }

        // Restore layout
        if (webVisible) {
            showSplitLayout();
        } else {
            showFullListLayout();
        }

        // Handle back press
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (webVisible) {
                    // First check if the WebView can go back a page
                    FragmentManager fm = getSupportFragmentManager();
                    WebFragment webFragment =
                            (WebFragment) fm.findFragmentByTag(TAG_WEB);

                    if (webFragment != null && webFragment.handleBackPress()) {
                        // WebView went back a page — do nothing else
                        return;
                    }

                    // WebView has no more history — collapse the panel
                    collapseWebPanel();

                } else {
                    // No web panel open — default back behavior
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SELECTED_INDEX, selectedIndex);
        outState.putString(KEY_SELECTED_URL, selectedUrl);
        outState.putBoolean(KEY_WEB_VISIBLE, webVisible);
    }

    // ---------------------------------------------------------------
    // Options Menu
    // ---------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // Handle the back arrow press in ActionBar
        if (id == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }

        if (id == R.id.menu_attractions) {
            return true;
        } else if (id == R.id.menu_restaurants) {
            Intent intent = new Intent(this, RestaurantsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // ---------------------------------------------------------------
    // ListFragment.OnPlaceSelectedListener
    // ---------------------------------------------------------------

    @Override
    public void onPlaceSelected(ChicagoPlace place, int position) {
        selectedIndex = position;
        selectedUrl   = place.getUrl();
        webVisible    = true;

        showSplitLayout();
        loadOrUpdateWebFragment(selectedUrl);
    }

    // ---------------------------------------------------------------
    // Fragment Management
    // ---------------------------------------------------------------

    private void addListFragment() {
        ListFragment listFragment = ListFragment.newInstance(restaurants);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerList, listFragment, TAG_LIST)
                .commit();
    }

    private void loadOrUpdateWebFragment(String url) {
        FragmentManager fm = getSupportFragmentManager();
        WebFragment webFragment = (WebFragment) fm.findFragmentByTag(TAG_WEB);

        if (webFragment == null) {
            webFragment = WebFragment.newInstance(url);
            fm.beginTransaction()
                    .replace(R.id.containerWeb, webFragment, TAG_WEB)
                    .commit();
        } else {
            webFragment.loadUrl(url);
        }
    }

    // ---------------------------------------------------------------
    // Layout helpers
    // ---------------------------------------------------------------

    private void showFullListLayout() {
        containerWeb.setVisibility(View.GONE);

        android.view.ViewGroup.LayoutParams params = containerList.getLayoutParams();
        params.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
        containerList.setLayoutParams(params);
    }

    private void showSplitLayout() {
        containerWeb.setVisibility(View.VISIBLE);

        android.widget.LinearLayout.LayoutParams listParams =
                (android.widget.LinearLayout.LayoutParams)
                        containerList.getLayoutParams();
        listParams.width  = 0;
        listParams.weight = 1f;
        containerList.setLayoutParams(listParams);

        android.widget.LinearLayout.LayoutParams webParams =
                (android.widget.LinearLayout.LayoutParams)
                        containerWeb.getLayoutParams();
        webParams.width  = 0;
        webParams.weight = 2f;
        containerWeb.setLayoutParams(webParams);
    }

    private void collapseWebPanel() {
        webVisible  = false;
        selectedUrl = "";

        FragmentManager fm = getSupportFragmentManager();
        WebFragment webFragment = (WebFragment) fm.findFragmentByTag(TAG_WEB);
        if (webFragment != null) {
            fm.beginTransaction()
                    .remove(webFragment)
                    .commit();
        }

        showFullListLayout();

        ListFragment listFragment =
                (ListFragment) fm.findFragmentByTag(TAG_LIST);
        if (listFragment != null) {
            listFragment.setSelectedPosition((int) RecyclerView.NO_ID);
        }
    }
}
