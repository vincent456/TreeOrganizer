package vincenthudry.organizer.view.tree_layout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import vincenthudry.organizer.Modules;
import vincenthudry.organizer.R;
import vincenthudry.organizer.Settings;
import vincenthudry.organizer.utils.Tuple2;

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

        wv=v.findViewById(R.id.tree_layout);
        wv.setWebViewClient(new WebViewClient());
        wv.getSettings().setJavaScriptEnabled(true);
        WebView.setWebContentsDebuggingEnabled(true);
        wv.addJavascriptInterface(new WebAppInterface(getActivity()),"Android");

        try {
           Settings.getTreeCheckpoint(getContext());
        }catch (Exception e){
            Settings.setTreeCheckpoint(getContext(),0);
        }

        long lastTreeID=Settings.getTreeCheckpoint(getContext());

        wv.loadUrl("file:android_asset/tree_view/index.html?"+String.valueOf(lastTreeID));
        final Button addTreeChild=v.findViewById(R.id.add_tree);
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

                        wv.loadUrl("javascript:followJava1(\""+nodeTilte+"\")");
                    }
                });
                builder.show();
            }
        });

        final EditText nodeSelector = v.findViewById(R.id.node_selector);
        nodeSelector.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE){
                    wv.loadUrl("file:android_asset/tree_view/index.html?"+nodeSelector.getText().toString());
                    return false;
                }
                return false;
            }
        });

        Button addItem=v.findViewById(R.id.add_item);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
             builder.setTitle(getString(R.string.get_items));

             Modules.createInstance(getContext());
             final ArrayList<Tuple2<String,Intent>> modules = Modules.data;

             String[] titles = new String[modules.size()];
             for(int i=0;i<modules.size();i++){
                 titles[i]=modules.get(i).t1;
                }

             builder.setItems(titles, new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialogInterface, int i) {
                     wv.loadUrl("javascript:followJavaModulesIntegration("+i+")");
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
        void onFragmentInteraction(Uri uri);
    }
}
