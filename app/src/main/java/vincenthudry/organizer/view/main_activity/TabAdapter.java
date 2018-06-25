package vincenthudry.organizer.view.main_activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    public void init(Fragment[] in){
        fragments=in;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return (CharSequence) new String[]{"notes","reminders","tree"}[position];
    }

    public Fragment[] getFragments(){
        return fragments;
    }

}
