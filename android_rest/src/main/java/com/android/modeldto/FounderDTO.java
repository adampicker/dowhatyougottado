package com.android.modeldto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.modelmapper.PropertyMap;
import com.android.model.Founder;


public class FounderDTO {
	

	public Long id;	
	public String accountName;
	public String listKey;
	public double longtitude;
	public double latitude;
	
	public FounderDTO(String name) {
		this.accountName = name;
	}
	
	public FounderDTO() {}

	public Long getId() {
		return this.id;
	}
	public String getAccountName() {
		return this.accountName;
	}
	public String getListKey() {
		return this.listKey;
	}
	
	public double getLongtitude() {
		return this.longtitude;
	}
	
	public double getLatitude() {
		return this.latitude;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public void setKey(String listKey) {
		this.listKey = listKey;
	}
	
	public void setLongtitude(double longtitude) {
		this.longtitude  = longtitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public static PropertyMap<Founder, FounderDTO> founderMap = new PropertyMap<Founder, FounderDTO>() {
		  protected void configure() {
		    map(source.getId(), destination.id);
		    map(source.getAccountName(), destination.accountName);
		    map(source.getListKey(), destination.listKey);
		    map(source.getLatitude(), destination.latitude);
		    map(source.getLongtitude(), destination.longtitude);
		  }
		};

}
