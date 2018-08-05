package vincenthudry.organizer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    public void test(){
        Context context=InstrumentationRegistry.getTargetContext();

        JSONObject root = TodoGenerator.fromString("");
        JSONObject oject1=TodoGenerator.createToDoItem("1");
        TodoGenerator.addSubTask(root,oject1);
        JSONObject object11 = TodoGenerator.createToDoItem("11");
        TodoGenerator.addSubTask(oject1,object11);
        JSONObject object2=TodoGenerator.createToDoItem("2");
        TodoGenerator.addSubTask(root,object2);

        TodoGenerator.setNumbersData(root);
        assertEquals(0,TodoGenerator.getID(root));
        assertEquals(1,TodoGenerator.getID(oject1));
        assertEquals(2,TodoGenerator.getID(object11));
        assertEquals(3,TodoGenerator.getID(object2));
        TodoGenerator.setParents(root);
        assertEquals(0,TodoGenerator.getParent(oject1));
        assertEquals(1,TodoGenerator.getParent(object11));
        assertEquals(0,TodoGenerator.getParent(object2));
    }
}
