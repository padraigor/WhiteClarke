package org.porourke.carshop.model.hibernate;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity(name = "Make")
public class Make{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; // Unique Identifier

	@JsonIgnore
	@OneToMany 
	//@JoinTable(name="Make_Model", joinColumns=@JoinColumn(name="id"),inverseJoinColumns=@JoinColumn(name="make_id"))
	private Collection<Model> models; // List of Models for this Make
	
	private String name;

	public Make() {
		this(-1, new ArrayList<Model>(), "");
	}

	public Make(Collection<Model> models, String name) {
		this(-1, models, name);
	}

	public Make(int id, Collection<Model> models, String name) {
		super();
		this.id = id;
		this.models = models;
		this.name = name;

		if (models != null) {
			for (Model m : models) {
				m.setMake(this);
			}
		}
	}

	public void addModel(Model model) {
		if (!models.contains(model)) {
			models.add(model);
			model.setMake(this);
		}
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Collection<Model> getModels() {
		return this.models;
	}

	// Note: Does not disconnect old Models from makes. All old-models.make not
	// set to null
	public void setModels(Collection<Model> models) {
		this.models = models;
		for (Model m : models) {
			m.setMake(this);
		}
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Make other = (Make) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Make [id=" + id + " , name=" + name + "]";
	}

}
