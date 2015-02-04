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


public class Make implements MakeInterface {
	private long id;			   // Unique Identifier 
	private Collection<ModelInterface> models; // List of Models for this Make
	private String name;
	
	public Make(){	
		this(-1, new HashSet<ModelInterface>(), null);
	}
	
	public Make(String name)
	{	this(-1, new HashSet<ModelInterface>(), name);
	}
	
	public Make(int id, String name)
	{	this(id,new HashSet<ModelInterface>(),name);
	}

	public Make(int id, Collection<ModelInterface> models, String name) {
		super();
		this.id = id;
			if(models == null)	this.models = new HashSet<ModelInterface>();
			else				this.models = models;	
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.MakeInterface#addModel(org.porourke.carshop.model.vechicles.Model)
	 */
	public void addModel(ModelInterface model){	
		this.models.add(model);
	}
	
 	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.MakeInterface#getId()
	 */
 	public long getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.MakeInterface#setId(int)
	 */
	public void setId(long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.MakeInterface#getModels()
	 */
	public Collection<ModelInterface> getModels() {
		return models;
	}

	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.MakeInterface#setModels(java.util.Set)
	 */
	public void setModels(Collection<ModelInterface> models) {
		this.models = models;
	}

	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.MakeInterface#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.MakeInterface#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.MakeInterface#hashCode()
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.MakeInterface#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this,obj);
	}
	/* (non-Javadoc)
	 * @see org.porourke.carshop.model.vechicles.MakeInterface#toString()
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
