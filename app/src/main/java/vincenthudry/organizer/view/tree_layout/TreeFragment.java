package vincenthudry.organizer.view.tree_layout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import vincenthudry.organizer.R;
import vincenthudry.organizer.Settings;
import vincenthudry.organizer.model.Database;
import vincenthudry.organizer.utils.Box;

public class TreeFragment extends Fragment {
    public TreeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TreeFragment newInstance() {
        TreeFragment fragment = new TreeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private WebView wv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tree, container, false);

        //region bind buttons
        Button triggerWebview=v.findViewById(R.id.trigger_webview);
        wv=v.findViewById(R.id.tree_layout);
        wv.getSettings().setJavaScriptEnabled(true);
        WebView.setWebContentsDebuggingEnabled(true);
        Database db=new Database(getContext(),Settings.databaseName);
        final Box<Long> currentNodeIDBox=new Box<Long>();
        currentNodeIDBox.object=-1L;
        wv.addJavascriptInterface(new WebAppInterface(db,currentNodeIDBox),"Android");
        wv.loadUrl("file:android_asset/tree_view/index.html?1");
        final FloatingActionButton addTreeChild=v.findViewById(R.id.add_tree_child);
        triggerWebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wv.loadUrl("javascript:main()");
            }
        });
        addTreeChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.enter_node_title);
                final View dialView=getLayoutInflater().inflate(R.layout.dialog_text_field,null);
                builder.setView(dialView);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        TextView tv= dialView.findViewById(R.id.single_text_field);
                        String nodeTilte=tv.getText().toString();

                        wv.loadUrl("javascript:getgname()");

                        //currentNodeIDBox.object;


                        Database db=new Database(getContext(),Settings.databaseName);
                        if(currentNodeIDBox.object==null){
                            db.addNode(nodeTilte);
                        }
                        else{
                            db.addChild(currentNodeIDBox.object,nodeTilte);
                        }
                    }
                });
                builder.show();
            }
        });
        //endregion

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
