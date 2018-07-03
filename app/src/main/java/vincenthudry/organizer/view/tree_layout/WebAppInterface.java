package vincenthudry.organizer.view.tree_layout;

import android.webkit.JavascriptInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import vincenthudry.organizer.model.Database;

public class WebAppInterface {
    private Database db;

    public WebAppInterface(Database db){
        this.db=db;
    }

    @JavascriptInterface
    public String getNode(long id){
        JSONObject ret=new JSONObject();
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
        return getNode(db.getNodeParent(id));
    }

    @JavascriptInterface
    public long followJS1(String title, long id){
        return db.addChild(id,title);
    }

    @JavascriptInterface
    public long callGetParentNode(long id){
        return db.getNodeParent(id);
    }

    @JavascriptInterface
    public void followJS2(long id){
        //if id has root, else can't delete root
        //set all children of id to root of id
        //delete node id

        Long root=db.getNodeParent(id);
        List<Long> children=db.getNodeChildren(id);

        db.deleteNode(id);
    }
}
