package vincenthudry.organizer;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Settings {
    public static String databaseName="database.db";
    public static void setTreeCheckpoint(Context context,long id){
        File filesDir=context.getFilesDir();
        File settings=new File(filesDir,"settings");
        try (OutputStream outputStream=new FileOutputStream(settings)){

            Properties properties=new Properties();
            properties.setProperty("last tree",String.valueOf(id));
            properties.store(outputStream,null);

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public static long getTreeCheckpoint(Context context){
        File filesDir=context.getFilesDir();
        File settings=new File(filesDir,"settings");
        try (InputStream inputStream=new FileInputStream(settings)){
            Properties properties=new Properties();
            properties.load(inputStream);
            return Long.valueOf(properties.getProperty("last tree"));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}