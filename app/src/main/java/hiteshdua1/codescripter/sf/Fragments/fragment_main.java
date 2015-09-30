package hiteshdua1.codescripter.sf.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hiteshdua1.codescripter.sf.R;

/**
 * Created by Hitesh Dua on 10/1/2015.
 */
public class fragment_main extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        return inflater.inflate(
                R.layout.fragment_main, container, false);
    }

}
