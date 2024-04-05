package com.example.loginsignup;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class BranchMapLocation extends AppCompatActivity {

    private EditText editTextCurrentLocation;
    private Spinner spinnerDestination;
    private Button buttonShowMap;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_map_location);

        editTextCurrentLocation = findViewById(R.id.editTextCurrentLocation);
        spinnerDestination = findViewById(R.id.spinnerDestination);
        buttonShowMap = findViewById(R.id.buttonShowMap);
        webView = findViewById(R.id.webView);

        // Populate spinner with destination options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.destinations_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDestination.setAdapter(adapter);

        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        buttonShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentLocation = editTextCurrentLocation.getText().toString();
                String destination = spinnerDestination.getSelectedItem().toString();

                // Load Google Maps website with user's input
                webView.loadUrl("https://www.google.com/maps/dir/" + currentLocation + "/" + destination);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
