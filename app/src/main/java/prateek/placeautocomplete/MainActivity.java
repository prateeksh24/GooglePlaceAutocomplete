package prateek.placeautocomplete;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    TextView tvSearch,tvPlace,tvLat,tvLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvSearch=(TextView)findViewById(R.id.tvSearch);
        tvPlace=(TextView)findViewById(R.id.tvPlace);
        tvLat=(TextView)findViewById(R.id.tvLat);
        tvLon=(TextView)findViewById(R.id.tvLon);
        tvSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();
        if(id==R.id.tvSearch)
        {
            openPlaceAutocomplete();
        }
        else
        {
            Toast.makeText(this, "Check Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void openPlaceAutocomplete() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }

    }

    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK)
            {
                Place place = PlaceAutocomplete.getPlace(this, data);
                tvPlace.setText(place.getName());
                tvLat.setText(String.valueOf( place.getLatLng().latitude));
                tvLon.setText(String.valueOf( place.getLatLng().longitude));
            }
            else if (resultCode == PlaceAutocomplete.RESULT_ERROR)
            {
                Status status = PlaceAutocomplete.getStatus(this, data);
                tvPlace.setText("ERROR");
            }
            else if (resultCode == RESULT_CANCELED) {
                tvPlace.setText("CANCELED");
            }
        }
        else
        {
            Toast.makeText(this, "Nothing happened", Toast.LENGTH_SHORT).show();
        }
    }

}