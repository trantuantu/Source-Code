package  com.example.tuantu.week3_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout layout = (RelativeLayout)findViewById(R.id.mainlayout);
        MyView myview = new MyView(this);

        layout.addView(myview);
       // myview.Start();
    }
}
