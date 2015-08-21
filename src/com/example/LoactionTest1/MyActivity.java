package com.example.LoactionTest1;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import java.util.List;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     * ��λ����
     */
    private Button bt;
    private TextView tv;
    private LocationManager locationManager;
    private String proivder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        bt = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.text_view);

        //�����ǻ�ȡ���� LocationManager ��ʵ��
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //��ȡ���п��õ�λ���ṩ��
        List<String> providerList = locationManager.getProviders(true);
        //�ж��ṩ��
        if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            proivder = locationManager.NETWORK_PROVIDER;
        } else if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            proivder = locationManager.GPS_PROVIDER;
        } else {
            //��û�п��õ�λ���ṩ��ʱ������Toast��ʾ�û�
            Toast.makeText(this, "NO loaction provider to use", Toast.LENGTH_SHORT).show();
            return;
        }
        //���� getLastKnownLocation()�������Ի�ȡ����¼��ǰλ����Ϣ��Location����
        Location location = locationManager.getLastKnownLocation(proivder);
        if (location != null) {
            showLocation(location);
        }

        //�ڲ�������д��
        //���� requestLocationUpdates()���������һ��λ�ü�����
//        locationManager.requestLocationUpdates(proivder, 1000, 0, new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                // ���µ�ǰ�豸��λ����Ϣ
//                showLocation(location);
//            }
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        }
//        );
        locationManager.requestLocationUpdates(proivder, 1000, 0, locationListener);

    }

    //���� requestLocationUpdates()���������һ��λ�ü�����
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // ���µ�ǰ�豸��λ����Ϣ
            showLocation(location);
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        @Override
        public void onProviderEnabled(String provider) {

        }
        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
    private void showLocation(Location location) {
        String sb = "Latitude is: " + location.getLatitude() + "\n" + "Longitude is: " + location.getLongitude();
        tv.setText(sb);

//StringBuilderд��
//        StringBuilder sb = new StringBuilder();
//        sb.append("(");
//        sb.append(location.getLatitude());
//        sb.append(",");
//        sb.append(location.getLongitude());
//        sb.append(")");
//        sb.setText(sb);
    }
}
