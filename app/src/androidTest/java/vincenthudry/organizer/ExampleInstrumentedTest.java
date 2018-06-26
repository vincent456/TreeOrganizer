package vincenthudry.organizer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import vincenthudry.organizer.model.Database;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("vincenthudry.organizer", appContext.getPackageName());
    }
    @Test
    public void testNodes(){
        Context context=InstrumentationRegistry.getTargetContext();
        Database db=new Database(context,Settings.databaseName);
        long root=db.addNode("root");
        long c1=db.addNode("c1");
        long c2=db.addNode("c2");
        db.setNodeParent(root,c1);
        db.setNodeParent(root,c2);
        List<Long> children=db.getNodeChildren(root);
        List<String> getTitles=new LinkedList<>();
        for(Long c:children)
            getTitles.add(db.getNodeTitle(c));
        assertTrue(getTitles.contains("c1"));
        assertTrue(getTitles.contains("c2"));
        db.nukeDB();
    }
}
