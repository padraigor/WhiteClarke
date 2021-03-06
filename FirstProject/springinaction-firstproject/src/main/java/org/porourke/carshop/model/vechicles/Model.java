package org.porourke.carshop.model.vechicles;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.porourke.carshop.model.MakeInterface;
import org.porourke.carshop.model.ModelInterface;
import org.porourke.carshop.model.VehicleInterface;
import org.porourke.carshop.model.hibernate.Make;

public class Model implements ModelInterface {
	private long id;		//Unique Identifer
	private Collection<Vehicle> vehicles; // List of Vehicles with this model
	private Make make; // Car make for this model
	private String name;
	
	public Model() {
		this(-1, new HashSet<VehicleInterface>(), null, "");
	}
	public Model(String name, MakeInterface make){
		this(-1, new HashSet<VehicleInterface>(), make, name);
	}
	
	public Model(int id, Collection<VehicleInterface> vehicles, MakeInterface make2, String name) {
		super();
		this.id = id;
			if(vehicles != null) 	this.vehicles = vehicles;
			else					this.vehicles = new HashSet<Vehicle>(); 
		this.make = make2;
		this.name = name;
			if(make2!=null)			this.make.getModels().add(this);
		
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.Modelnterface#getId()
	 */
	public long getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.Modelnterface#setId(int)
	 */
	public void setId(long id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.Modelnterface#getVehicles()
	 */
	public Collection<VehicleInterface> getVehicles() {
		return vehicles;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.Modelnterface#setVehicles(java.util.Set)
	 */
	public void setVehicles(Set<VehicleInterface> vehicles) {
		this.vehicles = vehicles;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.Modelnterface#getMake()
	 */
	public MakeInterface getMake() {
		return make;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.Modelnterface#setMake(org.porourke.carshop.model.vechicles.Make)
	 */
	public void setMake(Make make) {
		this.make = make;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.Modelnterface#getName()
	 */
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.Modelnterface#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.Modelnterface#hashCode()
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this,  Arrays.asList("make"));
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.Modelnterface#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, Arrays.asList("make"));
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.Modelnterface#toString()
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public void setVehicles(Collection<Vehicle> vehicles) {
		// TODO Auto-generated method stub
		
	}
	public void addVehicle(Vehicle vehicle) {
		// TODO Auto-generated method stub
		
	}
	public void setMake(Make make) {
		// TODO Auto-generated method stub
		
	}
	
	
		
}
