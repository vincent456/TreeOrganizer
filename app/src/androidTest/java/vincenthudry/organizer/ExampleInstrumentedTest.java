package vincenthudry.organizer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import vincenthudry.organizer.Model.Database;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    //@Test
    //public void useAppContext() throws Exception {
    //    // Context of the app under test.
    //    Context appContext = InstrumentationRegistry.getTargetContext();
    //
    //    assertEquals("vincenthudry.organizer", appContext.getPackageName());
    //}
    @Test
    public void testsDatabaseNotes(){
        Context appContext=InstrumentationRegistry.getTargetContext();
        Database db=new Database(appContext,"database.db");
        db.nukeDB();
        db=new Database(appContext,"database.db");
        db.addNote("title1","message1");
        assertEquals("message1",db.getNoteContent(1));
        db.addNote("title1","message2");
        assertEquals("message2",db.getNoteContent(2));
        List<Integer> notesWithTitle1=db.getIdByTitle("title1");
        assertEquals(2,notesWithTitle1.size());
        db.updateNote(2,"title2","edit");
        assertEquals("title2",db.getNoteTitle(2));
        assertEquals("edit",db.getNoteContent(2));
        assertEquals(2,db.getAllTitles().size());
        db.deleteNote(2);
        assertEquals(1,db.getAllTitles().size());
        db.nukeDB();
    }
}
