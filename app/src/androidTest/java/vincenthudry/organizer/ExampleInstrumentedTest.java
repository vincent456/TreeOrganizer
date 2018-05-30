package vincenthudry.organizer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

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

   //TODO: for unknown reasons, it fails to solve packages and classes
   @Test
   public void testEncryptNote(){
       Context appContext=InstrumentationRegistry.getTargetContext();
       Database db=new Database(appContext,"testdb.db");
       db.nukeDB();
       db=new Database(appContext,"testdb.db");
       db.addNote("test","this text is tested for encryption");
       int id=db.getIdByTitle("test").get(0);
       NotesModule notesModule=new NotesModule(db);
       try {
           notesModule.encrypt(id, "password");
           notesModule.decrypt(id,"password");
           }
           catch (NotesModule.DoubleEncrypt | NotesModule.DoubleDecrypt doubleEncrypt) {
           throw new RuntimeException();
           }
           assertEquals("this text is tested for encryption",db.getNoteContent(id));
       }
}
