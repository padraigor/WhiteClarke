package com.wcna.calms.jpos.services.audit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.data.ModuleEntityData;
import com.wcna.calms.data.ScreenEntityData;
import com.wcna.calms.util.DataAccessSession;

public class ProposalScreenStaticService extends CalmsAjaxService {
	public Map listFields(Map arguments) {

		Session hs = DataAccessSession.getInstance().getSession();

		String appType = (String) arguments.get("keyId");


		Criteria criteria = hs.createCriteria(ModuleEntityData.class);
		{
			Criteria screenCriteria = criteria.createCriteria("module", "module");
			//screenCriteria.add(Restrictions.or(
				//	Restrictions.eq("moduleTypeCode", appType), Restrictions.isNull("applicationTypeCode")));
		}

		Map<String, Map<String, Object>> results = new HashMap<String, Map<String, Object>>();
		for (ModuleEntityData screenData : (List <ModuleEntityData>) criteria.list()) {

			String key = screenData.getModule().getName();

			Map<String, Object> screenMap = (Map<String, Object>) results.get(key);
			if (screenMap == null) {
				screenMap = new HashMap<String, Object>();
				results.put(key, screenMap);

				Long screenId = screenData.getModule().getId();

				screenMap.put("screenId", screenId);
				screenMap.put("screenName", screenData.getModule().getName());
			}

			List<Map<String,Object>>entities = (List<Map<String,Object>>) screenMap.get("entities");
			if (entities == null)
			{
				entities = new ArrayList<Map<String,Object>>();
				screenMap.put("entities", entities);
			}

			Map<String,Object> entity = new HashMap<String,Object>();
			entity.put("entityId", screenData.getEntity().getId());
			entity.put("entityName", screenData.getEntity().getFieldDesc());

			entities.add(entity);
		}
		return results;
	}

	public Object invoke(Object _arguments) {
		Map arguments = (Map) _arguments;
		return listFields(arguments);
	}
}