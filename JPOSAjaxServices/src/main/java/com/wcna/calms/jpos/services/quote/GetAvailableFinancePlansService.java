package com.wcna.calms.jpos.services.quote;

import com.wcg.product.services.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAvailableFinancePlansService implements Service {

	public Object invoke(Object arg0) {
		Map<String, String> fp = new HashMap<String, String>();
		List<Map<String, String>> rlist = new ArrayList<Map<String, String>>();
		
		rlist.add(fp);
		
		fp.put("label", "Hire Purchase");
		fp.put("value", "1");
		
		fp = new HashMap<String, String>();
		rlist.add(fp);
		
		fp.put("label", "Finance Lease");
		fp.put("value", "2");
		
		return rlist;
	}

}
