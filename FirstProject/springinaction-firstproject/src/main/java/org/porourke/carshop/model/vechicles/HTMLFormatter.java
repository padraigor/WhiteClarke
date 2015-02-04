package org.porourke.carshop.model.vechicles;

import java.util.Set;

import org.porourke.carshop.model.MakeInterface;

public class HTMLFormatter {
	
	public String convertSetOfTypeMakeToOptionsListForDropDownList(Set<Make> makes)
	{	String ans = "";
			for(MakeInterface m:makes)
			{	ans = ans + "<Option value = \"" + m.getId() + "\">" + m.getName() + "</Option>";	
			}
		return ans;
	}

}
