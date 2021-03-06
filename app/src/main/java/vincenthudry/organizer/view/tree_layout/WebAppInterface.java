package vincenthudry.organizer.view.tree_layout;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import vincenthudry.organizer.Modules;
import vincenthudry.organizer.R;
import vincenthudry.organizer.Settings;
import vincenthudry.organizer.model.NodesDatabase;
import vincenthudry.organizer.model.NotesDatabase;
import vincenthudry.organizer.utils.Tuple2;

public class WebAppInterface {
    private Context context;

    public WebAppInterface(Context context){
        this.context=context;
    }

    @JavascriptInterface
    public String getNode(long id){
        JSONObject ret=new JSONObject();
        NodesDatabase db=new NodesDatabase(context);
        try {
            ret.accumulate("name", db.getNodeTitle(id));
            ret.accumulate("id",id);
            JSONArray childrenArray=new JSONArray();
            ret.accumulate("children",childrenArray);
            List<Long>  childrenData=db.getNodeChildren(id);
            for(Long c:childrenData){
                JSONObject ch=new JSONObject();
                ch.accumulate("name",db.getNodeTitle(c));
                ch.accumulate("id",c);
                childrenArray.put(ch);
            }
        }
        catch (JSONException e){
            throw new RuntimeException(e.getMessage());
        }
        String r=ret.toString();
        return r;
    }
    @JavascriptInterface
    public String getAncestorNode(long id){
        NodesDatabase db=new NodesDatabase(context);
        return getNode(db.getNodeParent(id));
    }

    @JavascriptInterface
    public long followJS1(String title, long id){
        NodesDatabase db=new NodesDatabase(context);
        return db.addChild(id,title);
    }

    @JavascriptInterface
    public long callGetParentNode(long id){
        NodesDatabase db=new NodesDatabase(context);
        return db.getNodeParent(id)==null?-1:db.getNodeParent(id);
    }

    @JavascriptInterface
    public void followJS2(long id){
        //if id has root, else can't delete root
        //set all children of id to root of id
        //delete node id
        NodesDatabase db=new NodesDatabase(context);
        Long root=db.getNodeParent(id);
        List<Long> children = db.getNodeChildren(id);
        if(root!=null||(root==null&&children.isEmpty())){}
        else{
            Toast.makeText(context, R.string.cant_delete_root_node,Toast.LENGTH_SHORT).show();
            return;
        }

        for(Long l:children){
            db.setNodeParent(root,l);
        }

        db.deleteNode(id);
    }

    @JavascriptInterface
    public void followJS3(long id){
        Settings.setTreeCheckpoint(context,id);
    }

    @JavascriptInterface
    public void followJS4(long id, long parent){
        NodesDatabase ndb = new NodesDatabase(context);
        ndb.setNodeParent(parent,id);
    }

    @JavascriptInterface
    public void followJSModulesIntegration(long nodeID,int i){
        Modules.data.get(i).t2.putExtra("nodeID",nodeID);
        context.startActivity(Modules.data.get(i).t2);
    }

    @JavascriptInterface
    public void followJSDeleteNodeContent(long nodeID){

        /*
            free the data of your modules when the node with ID "nodeID" gets deleted
         */

        NotesDatabase notesDatabase=new NotesDatabase(context);
        List<Tuple2<Long,String>> notes = notesDatabase.getAllTitles(nodeID);
        for(Tuple2<Long,String> t : notes){
            notesDatabase.deleteNote(t.t1);
        }
    }
}
