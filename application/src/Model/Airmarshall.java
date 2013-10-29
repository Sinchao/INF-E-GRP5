/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Kelly
 */
public class Airmarshall extends Staff{
    
    private String weaponNr;
    private int personnelNr;
    private String nationality;
    

    public String getWeaponNr() {
        return weaponNr;
    }

    public void setWeaponNr(String weaponNr) {
        this.weaponNr = weaponNr;
    }

    public int getPersonnelNr() {
        return personnelNr;
    }

    public void setPersonnelNr(int personnelNr) {
        this.personnelNr = personnelNr;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
