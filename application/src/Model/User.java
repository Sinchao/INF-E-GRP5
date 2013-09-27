/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class User implements Serializable {

    private int id;
    private String username;
    private String password;
    private Rank rank;

    public User() {
        rank = Rank.USER;
    }
    
    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password, password is MD5 coded
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Set the value of password, password is MD5 coded
     *
     * @param password new value of password
     */
    public void setPassword(String password, boolean setHash) {
        if(setHash){
            this.password = this.hash(password);
        }else{
            this.password = password;
        }
    }
    
    /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the value of username
     *
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    private String hash(String password) {
        String result;

        MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(password.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            result = hashtext;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
   
        return result;
    }

    @Override
    public String toString() {
        return username;
    }

}
