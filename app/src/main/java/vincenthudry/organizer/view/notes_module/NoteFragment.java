package vincenthudry.organizer.view.notes_module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import vincenthudry.organizer.R;

public class NoteFragment extends Fragment {

    private Context context;

    public static final int NEW_NOTE_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context=getContext();

        View v=inflater.inflate(R.layout.fragment_note,container,false);

        //region add listeners
        View fab=v.findViewById(R.id.add_note_fab);
                fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,NoteTakingActivity.class);
                intent.putExtra("noteID",-1);
                startActivityForResult(intent,NEW_NOTE_REQUEST);
            }
        });
        //endregion

        return v;
    }
}