package vincenthudry.organizer.controller;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import vincenthudry.organizer.model.Database;

public class NotesModule {
    private Database db;

    public NotesModule(Database db){
        this.db=db;
    }

    public class DoubleEncrypt extends Exception {

    }

    public void encrypt(int id,String password) throws DoubleEncrypt{
        boolean isEncrypted=db.getEncrypted(id);
        if(isEncrypted)
            throw new DoubleEncrypt();
        String text=db.getNoteContent(id);

        try{
            SecretKeySpec secretKeySpec=new SecretKeySpec(password.getBytes(),"Blowfish");
            Cipher cipher=Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
            byte[] encrypted=cipher.doFinal(text.getBytes());
            String encryptedText=new String(encrypted);
            db.setEncrypt(id,true);
            db.updateNoteText(id,encryptedText);
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }

    public class DoubleDecrypt extends Exception{

    }

    public void decrypt(int id, String password) throws DoubleDecrypt{
        boolean isEncrypted=db.getEncrypted(id);
        if(!isEncrypted)
            throw new DoubleDecrypt();
        String text=db.getNoteContent(id);

        try {
            SecretKeySpec secretKeySpec=new SecretKeySpec(password.getBytes(),"Blowfish");
            Cipher cipher=Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
            byte[] decrypted=cipher.doFinal(text.getBytes());
            String decryptedText=new String(decrypted);
            db.setEncrypt(id,false);
            db.updateNoteText(id,decryptedText);
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }

}
