package ominext.com.echo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ominext.com.echo.fragment.FriendListFragment;
import ominext.com.echo.fragment.TimeLineFragment;
import ominext.com.echo.fragment.MoreFragment;
import ominext.com.echo.fragment.WhiteboardFragment;

/**
 * Created by LuongHH on 12/6/2016.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FriendListFragment.getInstance();
            case 1:
                return WhiteboardFragment.getInstance();
            case 2:
                return TimeLineFragment.getInstance();
            case 3:
                return MoreFragment.getInstance();
            default:
                break;
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
