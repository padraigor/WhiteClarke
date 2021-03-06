package org.porourke.carshop.model.vechicles;

import java.util.Arrays;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.porourke.carshop.model.ModelInterface;
import org.porourke.carshop.model.VechicleInterface;
import org.porourke.carshop.model.VehicleInterface;
import org.porourke.carshop.model.hibernate.Model;


public class Vehicle implements VehicleInterface {
	private long id; 		//unique identifier
	private String reg;		//Registration Plate number add Vin later if required
	private Model model;	//Car model, links onto car Make/Manufacture
	private String colour;	
	private int year;
	private int engSizeCC;
	//Add image if time allows
	public Vehicle(){	
		reg = null; model = null; colour = null; year = -1; engSizeCC = -1;
	}
	public Vehicle(String reg, Model model, String colour, int year, int engSizeCC){	
		this(-1,reg, model, colour, year, engSizeCC);

	}

	public Vehicle(long id, String reg, Model model, String colour,int year, int engSizeCC) {
		super();
		this.id = id;
		this.reg = reg;
		this.model = model;
		this.colour = colour;
		this.year = year;
		this.engSizeCC = engSizeCC;
		
		//if(model != null) model.getVehicles().add(this);
		//System.out.println("Debug 1: " + model.getVehicles().size());
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.VechicleInterface#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.VechicleInterface#getReg()
	 */
	public String getReg() {
		return reg;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.VechicleInterface#setReg(java.lang.String)
	 */
	public void setReg(String reg) {
		this.reg = reg;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.VechicleInterface#getModel()
	 */
	public Model getModel() {
		return model;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.VechicleInterface#getId()
	 */
	public long getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.VechicleInterface#setId(int)
	 */
	public void setId(int id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.VechicleInterface#setModel(org.porourke.carshop.model.vechicles.Model)
	 */
	public void setModel(Model model2) {
		this.model = model2;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.VechicleInterface#getColour()
	 */
	public String getColour() {
		return colour;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.VechicleInterface#setColour(java.lang.String)
	 */
	public void setColour(String colour) {
		this.colour = colour;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.VechicleInterface#getYear()
	 */
	public int getYear() {
		return year;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.VechicleInterface#setYear(int)
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.VechicleInterface#getEngSizeCC()
	 */
	public int getEngSizeCC() {
		return engSizeCC;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.VechicleInterface#setEngSizeCC(int)
	 */
	public void setEngSizeCC(int engSizeCC) {
		this.engSizeCC = engSizeCC;
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.VechicleInterface#hashCode()
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this,  Arrays.asList("model"));
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.VechicleInterface#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, Arrays.asList("model"));
	}
	public void setModel(ModelInterface model) {
		// TODO Auto-generated method stub
		
	}
}
