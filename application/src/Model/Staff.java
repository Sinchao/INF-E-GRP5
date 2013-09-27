/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

public class Staff extends User implements java.io.Serializable {

    private PersonalType type;
    private String name;
    private Airport primaryAirport;
    
    public void setPrimaryAirport(Airport primaryAirport) {
        this.primaryAirport = primaryAirport;
    }

    public Airport getPrimaryAirport() {
        return primaryAirport;
    }

    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public PersonalType getType() {

        return type;
    }

    /**
     * Set the value of type
     *
     * @param type new value of type
     */
    public void setType(PersonalType type) {
        this.type = type;
    }

    /**
     * Get the value of number
     *
     * @return the value of number
     */

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
  
    @Override
    public String toString() {
        return super.getId() + " " + this.name + " : " + this.type;
    }
}
