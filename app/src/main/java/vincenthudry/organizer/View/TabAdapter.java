package vincenthudry.organizer.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;
    public TabAdapter(FragmentManager fm) {
        super(fm);
        //fragments=new Fragment[2];
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
        return 2;
    }

   // @Override
   // public CharSequence getPageTitle(int position){
   //     return (CharSequence) new String[]{"notes","reminder"}[position];
   // }
}
