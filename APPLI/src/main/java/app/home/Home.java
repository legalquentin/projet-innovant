package app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Home {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)

	private Integer	id;
	private Integer	bed;
	private Integer	owner_id;
	private Integer	etage;
	private String 	type;
	private String	ville;
	private String	addresse;
	private String	pays;
	private String	photo;
	private String	descriptif;
	
	public Home(){}

	public Integer getId(){
		return id;
	}

	public void setBed(Integer bed) {
	    this.bed = bed;
    }
    public Integer setBed(){
	    return bed;
    }

    public void setOwner_id(Integer owner_id){
    	this.owner_id = owner_id;
    }
    public Integer getOwner_id(){
    	return owner_id;
    }

    public void setEttage(Integer etage){
    	this.etage = etage;
    }
    public Integer getEtage(){
    	return etage;
    }

    public void setType(String type){
    	this.type = type;
    }
    public String getType(){
    	return type;
    }

    public void setVille(String ville){
    	this.ville = ville;
    }
    public String getVille(){
    	return ville;
    }

    public void setAddresse(String addresse){
    	this.addresse = addresse;
    }
    public String getAddresse(){
    	return addresse;
    }

    public void setPays(String pays){
	this.pays = pays;
}
    public String getPays(){
    	return pays;
    }

    public void setPhoto(String photo){
    	this.photo = photo;
    }
    public String getPhoto(){
    	return photo;
    }

    public void setDescriptif(String descriptif) {
    	this.descriptif = descriptif;
    }
    public String getDescriptif(){
    	return descriptif;
    }

}