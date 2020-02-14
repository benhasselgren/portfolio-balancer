package com.example.portfoliobalancer;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PortfolioDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        Place place =
                (Place) getIntent().getParcelableExtra("place");

        if (place != null) {
            TextView locationTitle =
                    (TextView) findViewById(R.id.location_info_title);
            ImageView locationImage =
                    (ImageView) findViewById(R.id.location_info_image);
            TextView locationInfo =
                    (TextView) findViewById(R.id.location_info_body);

            locationTitle.setText(place.getNameId());
            locationImage.setImageResource(place.getIconId());
            locationInfo.setText(place.getInfo());
        }
    }
}
