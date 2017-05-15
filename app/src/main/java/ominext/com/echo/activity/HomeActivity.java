package ominext.com.echo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ominext.com.echo.R;
import ominext.com.echo.adapter.ViewPagerAdapter;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.setSelectedTabIndicatorHeight(4);

        //set drawables for each tab
        tabLayout.getTabAt(0).setIcon(R.drawable.friend_list);
        tabLayout.getTabAt(1).setIcon(R.drawable.whiteboard);
        tabLayout.getTabAt(2).setIcon(R.drawable.timeline);
        tabLayout.getTabAt(3).setIcon(R.drawable.set_up);
    }
}
