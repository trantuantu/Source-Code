package com.example.vvn.slidingpanelayout;

import android.graphics.Color;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    SlidingPaneLayout mSlidingPanel;
    ListView mMenuList;
    ImageView appImage;
    TextView TitleText;

    String [] MenuTitles = new String[]{"Menu Item 1","Menu Item 2","Menu Item 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSlidingPanel = (SlidingPaneLayout) findViewById(R.id.SlidingPanel);

        mMenuList = (ListView) findViewById(R.id.MenuList);
        appImage = (ImageView)findViewById(android.R.id.home);

        TitleText = (TextView)findViewById(android.R.id.title);


        mMenuList.setAdapter(new ArrayAdapter(this, R.layout.menu_layout, R.id.textView, MenuTitles));

        mSlidingPanel.setPanelSlideListener(panelListener);
        //mSlidingPanel.setParallaxDistance(200);

        mMenuList.setOnItemClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    SlidingPaneLayout.PanelSlideListener panelListener = new SlidingPaneLayout.PanelSlideListener(){

        @Override
        public void onPanelClosed(View arg0) {
            // TODO Auto-genxxerated method stub        getActionBar().setTitle(getString(R.string.app_name));
        }

        @Override
        public void onPanelOpened(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPanelSlide(View arg0, float arg1) {
            // TODO Auto-generated method stub

        }

    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.show_sliding:
                if(mSlidingPanel.isOpen()){
                    mSlidingPanel.closePane();
                }
                else{
                    mSlidingPanel.openPane();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Select item

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if(position == 0){
            LeftMenuItem_1 fragment = new LeftMenuItem_1();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if(position == 1){
            LeftMenuItem_2 fragment = new LeftMenuItem_2();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }else if(position == 2){
            LeftMenuItem_3 fragment = new LeftMenuItem_3();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }

        mSlidingPanel.closePane();
    }
}
