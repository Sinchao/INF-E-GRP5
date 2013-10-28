/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author user
 */
public class Airport {
    private int  id;
    private String code;
    private String name;
    private Country country;
    private String city;
    
   
     
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    


     /**
      * Get the value of code
      *
      * @return the value of code
      */
     public String getCode() {
         return code;
     }

     /*
      * Set the value of code
      *
      * @param code new value of code
      */
     public void setCode(String code){
         this.code = code;
     }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of country
     *
     * @return the value of country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Set the value of country
     *
     * @param country new value of country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

     /**
     * Get the value of city
     *
     * @return the value of city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the value of city
     *
     * @param city new value of city
     */
    public void setCity(String city) {
        this.city = city;
    }


    @Override
    public String toString(){
        return code + " : " + name + " : " + country + " : " + city;
    }

    
}
