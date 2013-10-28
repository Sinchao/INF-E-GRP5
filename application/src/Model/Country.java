/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Kelly
 */
public class Country {
    private int id;
    private String landcode;
    private String country;
    private Integer region;
    
    public Country() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRegion(Integer region) {
        if(region != null){
            this.region = region.intValue();
        }
    }
    
    public Integer getRegion() {
        if(region != null){
            return region;
        }else{
            return null;
        }
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
     public String getLandcode() {
        return landcode;
    }

    public void setLandcode(String landcode) {
        this.landcode = landcode;
    }
    
     @Override
    public String toString(){
        return landcode + " : " + country + " : " + region;
    }
}
