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
     * 定位服务
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

        //首先是获取到了 LocationManager 的实例
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);
        //判断提供器
        if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            proivder = locationManager.NETWORK_PROVIDER;
        } else if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            proivder = locationManager.GPS_PROVIDER;
        } else {
            //当没有可用的位置提供器时，弹出Toast提示用户
            Toast.makeText(this, "NO loaction provider to use", Toast.LENGTH_SHORT).show();
            return;
        }
        //调用 getLastKnownLocation()方法可以获取到记录当前位置信息的Location对象
        Location location = locationManager.getLastKnownLocation(proivder);
        if (location != null) {
            showLocation(location);
        }

        //内部匿名类写法
        //调用 requestLocationUpdates()方法来添加一个位置监听器
//        locationManager.requestLocationUpdates(proivder, 1000, 0, new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                // 更新当前设备的位置信息
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

    //调用 requestLocationUpdates()方法来添加一个位置监听器
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // 更新当前设备的位置信息
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

//StringBuilder写法
//        StringBuilder sb = new StringBuilder();
//        sb.append("(");
//        sb.append(location.getLatitude());
//        sb.append(",");
//        sb.append(location.getLongitude());
//        sb.append(")");
//        sb.setText(sb);
    }
}
