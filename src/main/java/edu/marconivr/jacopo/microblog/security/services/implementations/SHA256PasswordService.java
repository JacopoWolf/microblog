package edu.marconivr.jacopo.microblog.security.services.implementations;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.google.common.base.Charsets;

import org.springframework.stereotype.Service;

import edu.marconivr.jacopo.microblog.entities.User;
import edu.marconivr.jacopo.microblog.security.services.IPasswordService;

@Service
public class SHA256PasswordService implements IPasswordService 
{
    public static final int SALT_LENGTH = 16;
    public static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    @Override
    public void setCredentialsOf(User user, String password) 
    {
        password = new String(password.getBytes(), Charsets.ISO_8859_1  );
        try 
        {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            String salt = playCounterStrike();
            String candidate = password.concat(salt);


            String hashedPassword = this.toHexadecimal( digest.digest(candidate.getBytes()) );


            User.setPasswordOf(user, hashedPassword, salt);

        } 
        catch (NoSuchAlgorithmException e) 
        { 
            // pointless must-handle exception.
        }
    }

    @Override
    public boolean verify(User user, String password) 
    {
        password = new String(password.getBytes(), Charsets.ISO_8859_1 );
        try
        {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            String candidate = password.concat( User.getSaltOf(user) );


            String hashAttempt = this.toHexadecimal( digest.digest( candidate.getBytes() ) );


            return (User.getHashedPasswordOf(user).equalsIgnoreCase(hashAttempt));

        }
        catch(NoSuchAlgorithmException e)
        {
            return false;
        }
    }


    /**
     * generates SALT. Obviously a gamer joke.
     * @return
     */
    private String playCounterStrike()
    {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(SALT_LENGTH);

        for (int i = 0; i < SALT_LENGTH; i++)
            sb.append( AB.charAt( rnd.nextInt( AB.length() ) ) );

        return sb.toString();
    }

    private String toHexadecimal ( byte[] bytes )
    {
        StringBuilder sb = new StringBuilder();

        for ( int i = 0; i < bytes.length; i++ )
            sb.append( Integer.toString((bytes[i]&0xff) + 0x100,16).substring(1) );

        return sb.toString();

    }


}