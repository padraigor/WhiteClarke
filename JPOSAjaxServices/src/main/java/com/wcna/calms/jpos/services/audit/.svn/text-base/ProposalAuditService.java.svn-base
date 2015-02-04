package com.wcna.calms.jpos.services.audit;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.RootEntityResultTransformer;
import org.hibernate.transform.Transformers;

import com.wcg.calms.ajax.common.CalmsAjaxService;
import com.wcna.calms.data.ScreenEntityData;
import com.wcna.calms.data.audit.AuditLogData;
import com.wcna.calms.log.Logger;
import com.wcna.calms.log.LoggerInstance;
import com.wcna.calms.service.format.IFormatService;
import com.wcna.calms.util.DataAccessSession;

public class ProposalAuditService extends CalmsAjaxService {
	private static final LoggerInstance logger = Logger.getLogger(ProposalAuditService.class);
	private IFormatService formatService;

	public  void setFormatService(IFormatService formatService) {
		this.formatService = formatService;
	}

	public List<ProposalAuditVO> searchAuditData(Map arguments) {

		Session hs = DataAccessSession.getInstance().getSession();

		Map args = (Map) arguments.get("keyId");

		Criteria criteria = hs.createCriteria(AuditLogData.class);
		criteria.createAlias("screen", "screen");
		criteria.createAlias("entity", "entity");
		criteria.createAlias("user", "user");

		String proposalNumber = (String) args.get("proposalNumber");
		String fromDate = (String) args.get("dateFrom");
		String toDate = (String) args.get("dateTo");

		// Select Clause
		ProjectionList projList = Projections.projectionList(); {
			projList.add(Projections.property("refId"), "refId");
			projList.add(Projections.property("oldValue"), "valueFrom");
			projList.add(Projections.property("newValue"), "valueTo");
			projList.add(Projections.property("changedDate"),"changedDate");
			projList.add(Projections.property("user.fullName"), "changedBy");
			projList.add(Projections.property("transactionRef"), "change");
			projList.add(Projections.property("appId"));
			projList.add(Projections.property("screen.screenDescription"), "moduleName");
			projList.add(Projections.property("entity.fieldDesc"), "fieldName");
			projList.add(Projections.property("indexPosition"), "indexPosition");
		}
		// Restrictions
        criteria = criteria.setProjection(Projections.distinct(projList));

        DetachedCriteria selectCriteria = DetachedCriteria.forClass(AuditLogData.class)
        	.setProjection( Property.forName("id"));

		//Joins
        selectCriteria.createAlias("screen", "screen");
        selectCriteria.createAlias("entity", "entity");
        selectCriteria.createAlias("user", "user");

		if (fromDate != null) {
			try {
				Date fromDateValue = (Date) formatService.parseDate(fromDate, this.getUserContainer().getLocale(), true, null);
				selectCriteria.add(Property.forName("changedDate").ge(fromDateValue));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (toDate != null) {
			try {
				Date toDateValue = (Date) formatService.parseDate(toDate, this.getUserContainer().getLocale(), true, null);
				selectCriteria.add(Property.forName("changedDate").le(toDateValue));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		selectCriteria = selectCriteria.add(Restrictions.eq("appId", new Long(proposalNumber)));
		Long minId = getFirstSubmittedRecord(new Long(proposalNumber));
		minId = minId != null ? minId : Long.MAX_VALUE;
		selectCriteria.add(Restrictions.gt("id", minId));
		String fieldId = (String) args.get("fieldId");
		if (fieldId != null) {
			selectCriteria.add(Restrictions.eq("entity.id", fieldId));
		}

		criteria.add(Subqueries.propertyIn("id", selectCriteria));
        criteria = criteria.setResultTransformer(Transformers.aliasToBean(ProposalAuditVO.class));


        criteria.addOrder(Order.desc("changedDate"));
        criteria.addOrder(Order.asc("screen.screenDescription"));
        criteria.addOrder(Order.asc("entity.fieldDesc"));
        // Where Clause


		List<ProposalAuditVO> results = criteria.list();
		for (ProposalAuditVO auditVO : results) {
			if (auditVO.getIndexPosition() != null) {
				auditVO.setFieldName(auditVO.getFieldName() + " [" + (1 + auditVO.getIndexPosition()) + "]");
			}

			if (auditVO.getRefId() != null) {
				auditVO.setModuleName(auditVO.getModuleName() + " (" + auditVO.getRefId() + ")");
			}
		}
		return results;
	}

	private Long getFirstSubmittedRecord(Long appId) {
		Session hs = DataAccessSession.getInstance().getSession();
		Criteria criteria = hs.createCriteria(AuditLogData.class);


		ScreenEntityData screenData = findScreenEntityData("Document", "status", null, hs);
		criteria.add(Restrictions.eq("entity.id", screenData.getEntityData().getId()));
		criteria.add(Restrictions.isNotNull("oldValue"));
		criteria.add(Restrictions.eq("appId", new Long(appId)));
		criteria.setProjection(Projections.min("id"));
		return (Long) criteria.uniqueResult();
	}

	public static ScreenEntityData findScreenEntityData(String screenName, String propertyName, String controlPrefixCode, Session session) {
		Criteria criteria = session.createCriteria(ScreenEntityData.class);

		Criteria screenCriteria = criteria.createCriteria("screenData");
		screenCriteria.add(Restrictions.eq("name", screenName));

		Criteria entityCriteria = criteria.createCriteria("entityData");
		entityCriteria.add(Restrictions.eq("fieldName", propertyName));

		if (controlPrefixCode != null){
			Criteria prefixCriteria = entityCriteria.createCriteria("controlPrefixData");
			prefixCriteria.add(Restrictions.idEq(controlPrefixCode));
		}
		criteria.addOrder(Order.desc("id"));
		criteria.setResultTransformer(new RootEntityResultTransformer());
		ScreenEntityData screenEntity = (ScreenEntityData) criteria.list().get(0);
		return screenEntity;
	}

	public Object invoke(Object _arguments) {
		Map arguments = (Map) _arguments;
		return searchAuditData(arguments);
	}
}