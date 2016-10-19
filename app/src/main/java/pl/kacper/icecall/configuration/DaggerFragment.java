package pl.kacper.icecall.configuration;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import pl.kacper.icecall.RemoteMedApplication;

/**
 * Created by kacper on 02.01.16.
 * Fragment that can be injected with dagger
 */
public class DaggerFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((RemoteMedApplication) getActivity().getApplication()).inject(this);
    }

}