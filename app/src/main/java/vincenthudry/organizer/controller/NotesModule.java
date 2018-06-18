package vincenthudry.organizer.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import vincenthudry.organizer.model.Database;

public class NotesModule {

    Database db;

    public NotesModule(Database db){
        this.db=db;
    }

    public class DoubleEncrypt extends Exception{

    }

    public void encrypt(long id, String text, String password) throws DoubleEncrypt {
        if(db.getEncrypted(id))
            throw new DoubleEncrypt();

        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            //database
            db.setEncrypted(id,true);
            db.updateText(id,new String(encrypted));
            db.setEncryptedData(id,encrypted);
        }
        catch (NoSuchPaddingException| NoSuchAlgorithmException| InvalidKeyException| BadPaddingException| IllegalBlockSizeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public class DoubleDecrypt extends Exception{

    }

    public void decrypt(long id, String password) throws DoubleDecrypt, Exception {
        if(!db.getEncrypted(id))
            throw new DoubleDecrypt();
        byte[] encryptedData=db.getEncryptedData(id);

        try {
            SecretKeySpec secretKeySpec=new SecretKeySpec(password.getBytes(),"Blowfish");
            Cipher cipher=Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
            byte[] decrypted=cipher.doFinal(encryptedData);
            //database
            db.setEncrypted(id,false);
            db.updateText(id,new String(decrypted));
            db.setEncryptedData(id,new byte[]{});
        }
        catch (Exception e){
            throw e;
        }
    }

    public void addNewEncryptedNote(String noteTitle,String noteText,String password) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(),"Blowfish");
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(noteText.getBytes());
            //database
            db.addEncryptedNote(noteTitle,encrypted.toString(),encrypted);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
