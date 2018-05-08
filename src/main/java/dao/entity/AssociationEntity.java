package dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@PrimaryKeyJoinColumn(name="id_person")
@Table(name = "association")
public class AssociationEntity extends PersonEntity implements SelServicesEntity{
	
	@Column(name = "website")
	private String website;

	

	/* getter and setter */
	

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
		
}

