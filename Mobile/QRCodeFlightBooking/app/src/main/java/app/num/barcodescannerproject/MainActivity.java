package app.num.barcodescannerproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity {
    private TextView txtView;
    private ScrollView scrollView;
    int PICK_CONTACT_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
    }
    public void openScanner(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            startActivityForResult(new Intent(this, QRScanner.class),PICK_CONTACT_REQUEST);
        }
        else
            Toast.makeText(getApplicationContext(), "Kiểm tra kết nối mạng...", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try
                {
                    //txtView.setText(data.getData().toString());
                    scrollView.removeAllViews();
                    JSONArray resJson = new JSONArray(data.getData().toString());
                    int totalCount;
                    if (resJson.length() == 6) totalCount = 2;
                    else totalCount = 1;
                    int x;
                    if (resJson.length() == 6) x = 0;
                    else x = 1;

                    LinearLayout parent = new LinearLayout(getApplicationContext());
                    parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    parent.setOrientation(LinearLayout.VERTICAL);
                    scrollView.addView(parent);

                    for (int c = 0; c < totalCount; c++) {

                        String[] listVal = {"Hành Khách", "Tổng Tiền", "Mã Đặt Chỗ", "Hạng", "Mã Chuyến Bay", "Khởi Hành", "Nơi Đến", "Số Ghế", "Ngày Khởi Hành", "Giờ Khởi Hành", "Thời Gian Bay", "Số Gói Hành Lý", "Giá Vé", "Giá Hành Lý"};
                        for (int i = 0; i < listVal.length; i++) {
                            TextView txtName = new TextView(getApplicationContext());
                            TextView txtValue = new TextView(getApplicationContext());
                            LinearLayout li = new LinearLayout(getApplicationContext());
                            if (i % 2 != 0) {
                                txtName.setTextColor(Color.parseColor("#006666"));
                                txtValue.setTextColor(Color.parseColor("#006666"));

                            } else {
                                txtName.setTextColor(Color.parseColor("#ffffff"));
                                txtValue.setTextColor(Color.parseColor("#ffffff"));
                                li.setBackgroundColor(Color.parseColor("#006666"));
                            }

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.weight = 5;
                            txtName.setLayoutParams(params);

                            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params1.weight = 5;
                            txtValue.setLayoutParams(params1);
                            txtName.setText(listVal[i]);

                            if (i == 0) {
                                if (c == 0) {
                                    String passenger = "";
                                    for (int k = 0; k < resJson.getJSONArray(2 - x).length(); k++) {
                                        if (k < resJson.getJSONArray(2 - x).length() - 1)
                                            passenger += resJson.getJSONArray(2 - x).getJSONObject(k).getString("DanhXung") + " " + resJson.getJSONArray(2 - x).getJSONObject(k).getString("Ho") + " " + resJson.getJSONArray(2 - x).getJSONObject(k).getString("Ten") + ", ";
                                        else
                                            passenger += resJson.getJSONArray(2 - x).getJSONObject(k).getString("DanhXung") + " " + resJson.getJSONArray(2 - x).getJSONObject(k).getString("Ho") + " " + resJson.getJSONArray(2 - x).getJSONObject(k).getString("Ten");
                                    }
                                    txtValue.setText(passenger);
                                }
                            } else if (i == 1) {
                                if (c == 0) {
                                    txtValue.setText(resJson.getJSONObject(c).getString("TongTien") + " VND");
                                    txtName.setTextColor(Color.parseColor("#ff0000"));
                                    txtValue.setTextColor(Color.parseColor("#ff0000"));
                                }
                            } else if (i == 2) {
                                txtValue.setText(resJson.getJSONObject(c).getString("MaDatCho"));
                            } else if (i == 3) {
                                txtValue.setText(resJson.getJSONObject(c).getString("Hang"));
                            } else if (i == 4) {
                                txtValue.setText(resJson.getJSONObject(c).getString("MaChuyenBay"));
                            } else if (i == 5) {
                                if (c == 0)
                                    txtValue.setText(resJson.getJSONArray(4 - x).getJSONObject(0).getString("TenDiaDanh"));
                                else
                                    txtValue.setText(resJson.getJSONArray(5 - x).getJSONObject(0).getString("TenDiaDanh"));
                            } else if (i == 6) {
                                if (c == 0)
                                    txtValue.setText(resJson.getJSONArray(5 - x).getJSONObject(0).getString("TenDiaDanh"));
                                else
                                    txtValue.setText(resJson.getJSONArray(4 - x).getJSONObject(0).getString("TenDiaDanh"));
                            } else if (i == 7) {
                                String seats = "";
                                for (int k = 0; k < resJson.getJSONArray(3 - x).length(); k++) {
                                    String[] dateStr = resJson.getJSONObject(c).getString("Ngay").split("-");
                                    String resStr = dateStr[0].replace("0", "") + "-" + dateStr[1].replace("0", "") + "-" + dateStr[2];
                                    if (resJson.getJSONArray(3 - x).getJSONObject(k).getString("MaGhe").equals(resJson.getJSONObject(c).getString("MaChuyenBay") + resStr))
                                        seats += resJson.getJSONArray(3 - x).getJSONObject(k).getString("SoGhe") + " ";
                                }
                                txtValue.setText(seats);
                            } else if (i == 8) {
                                txtValue.setText(resJson.getJSONObject(c).getString("Ngay"));
                            } else if (i == 9) {
                                txtValue.setText(resJson.getJSONObject(c).getString("Gio"));
                            } else if (i == 10) {
                                txtValue.setText(resJson.getJSONObject(c).getString("ThoiGianBay"));
                            } else if (i == 11) {
                                if (resJson.getJSONObject(c).getString("SoLuong") != null && !resJson.getJSONObject(c).getString("SoLuong").equals("null"))
                                    txtValue.setText(resJson.getJSONObject(c).getString("SoLuong"));
                                else txtValue.setText("0");
                            } else if (i == 12) {
                                txtValue.setText(resJson.getJSONObject(c).getString("GiaBan") + " VND");
                            } else if (i == 13) {
                                if (resJson.getJSONObject(c).getString("DonGia") != null && !resJson.getJSONObject(c).getString("DonGia").equals("null"))
                                    txtValue.setText(resJson.getJSONObject(c).getString("DonGia") + " VND");
                                else txtValue.setText("0 VND");
                            }
                            if ((i == 0 && c == 1) || (i == 2 && c == 0))
                            {
                                TextView txtFlight = new TextView(getApplicationContext());
                                txtFlight.setBackgroundColor(Color.parseColor("#dba511"));
                                txtFlight.setTextColor(Color.parseColor("#ffffff"));
                                LinearLayout.LayoutParams txtFlightParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                txtFlight.setLayoutParams(txtFlightParams);
                                txtFlight.setText("Chuyến bay " + (c + 1));

                                LinearLayout liFlight = new LinearLayout(getApplicationContext());
                                liFlight.setOrientation(LinearLayout.HORIZONTAL);
                                liFlight.setWeightSum(10);
                                liFlight.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                liFlight.addView(txtFlight);
                                parent.addView(liFlight);
                            }

                            li.setOrientation(LinearLayout.HORIZONTAL);
                            li.setWeightSum(10);
                            li.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            li.addView(txtName);
                            li.addView(txtValue);
                            if (!(i == 0 && c == 1) && !(i == 1 && c == 1)) parent.addView(li);
                        }
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}