package vincenthudry.organizer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import vincenthudry.organizer.controller.NotesModule;
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
    public void testsDatabaseNotes(){
        Context appContext=InstrumentationRegistry.getTargetContext();
        Database db=new Database(appContext,Settings.databaseName);
        db.nukeDB();
        db=new Database(appContext,Settings.databaseName);
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
    @Test
    public void testEncryption(){
        Context context=InstrumentationRegistry.getTargetContext();
        Database db=new Database(context,"test.db");
        db.nukeDB();
        db=new Database(context,"test.db");
        db.addNote("title test","this is a message for encryption");
        assertEquals(1,(int)(db.getIdByTitle("title test").get(0)));
        NotesModule notesModule=new NotesModule(db);
        try {
            notesModule.encrypt(1, "test password");
        }
        catch (NotesModule.DoubleEncrypt e){

        }
        try{
            notesModule.decrypt(1,"wrong password");
        }
        catch (Exception e){
            int i=0;
        }
        try {
            notesModule.decrypt(1,"test password");
        }catch (Exception e){

        }
        assertEquals("this is a message for encryption",db.getNoteContent(1));
    }
}
