package vincenthudry.organizer.view.notes_module;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vincenthudry.organizer.R;

public class NoteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("note fragment", "try to inflate");
        return inflater.inflate(R.layout.fragment_note,container,false);
    }
}