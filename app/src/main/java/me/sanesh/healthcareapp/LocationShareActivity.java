package me.sanesh.healthcareapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

public class LocationShareActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PERMISSIONS_FINE_LOCATION = 1234;
    //Google API for location services
    FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLoc;
    private Location tempLoc;
    TextView currentLocationData;
    EditText smsNumber;
    MaterialButton sendLocation, callAmbulance;

    String locationData = "";

    static int PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_share);
        currentLocationData = findViewById(R.id.txtCurrentLocation);
        smsNumber = findViewById(R.id.txtSendSmsNumber);
        sendLocation = findViewById(R.id.btnSendLocation);
        callAmbulance = findViewById(R.id.btnCallAmbulance);
        retrieveCurrentLocation();


        //Request Permission
        if (ContextCompat.checkSelfPermission(getApplicationContext().getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            ;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CODE);

        sendLocation.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (smsNumber != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                            sendSms(locationData);
                        } else {
                            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                        }
                    }
                } else {
                    Toast.makeText(LocationShareActivity.this, "Please enter number to send location!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        callAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + 1919));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);

    }

    //Check whether permission was granted or not
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Compressed
        switch (requestCode) {
            case PERMISSIONS_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    retrieveCurrentLocation();
                } else {
                    Toast.makeText(getApplicationContext(), "SGMedical Services Application required Location permissions to function properly", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }

    }

    //Update GPS data
    public void retrieveCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        //Check whether permission was already granted or not
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(LocationShareActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        tempLoc = location;
                        locationData = (tempLoc.getAltitude() + " " + tempLoc.getLatitude() + " " + tempLoc.getLongitude());
                        currentLocationData.setText(tempLoc.getAltitude() + " " + tempLoc.getLatitude() + " " + tempLoc.getLongitude());
                    } else {
                        Toast.makeText(getApplicationContext(), "Location Data is empty", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }
    }

    private void sendSms(String message) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(String.valueOf(smsNumber.getText()), null, String.valueOf(message), null, null);
            Toast.makeText(this, "Location Shared!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

    }
}