package com.wcna.calms.jpos.services.admin;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.CharEncoding;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.wcg.product.services.session.ISessionManagmentService;
import com.wcg.service.util.ApplicationUtil;
import com.wcg.calms.common.util.Logger;


public class JPOSCSVUploadServlet extends HttpServlet {

	private String redirectPath;
	private static final long serialVersionUID = 8599270382478224408L;

	public void init(ServletConfig in)
		throws ServletException {
		
		super.init(in);
		this.redirectPath = in.getInitParameter("redirectPath");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		ISessionManagmentService ms = ApplicationUtil.getInstance().getBean(ISessionManagmentService.class);
		
		response.setCharacterEncoding(CharEncoding.UTF_8);
		
        HttpSession session = request.getSession(false);
        String responseString = "?sessionToken=" + request.getSession().getAttribute(ms.getSessionTokenName()) + "&";
        
        DiskFileUpload upload = new DiskFileUpload();
        try {
        	List itemList = upload.parseRequest(request);
        	if (itemList != null && !itemList.isEmpty()) {
        		int size = itemList.size();
        		for (int i = 0; i < size; i++) {
        			FileItem fe = (FileItem) itemList.get(i);
        			if (fe != null) {
        				if (fe.isFormField()) {
        					responseString += (fe.getFieldName() + "=" + fe.getString() + "&");
        				} else {
        					byte[] bytes = fe.get();
        					String name = fe.getName();
        					session.setAttribute("productSheetFileName", name);
        					session.setAttribute("productSheetFileBytes", bytes);
        				}
        			}
        		}
        	}
        } catch (Exception e) {
        	Logger.error(e.getMessage(),e);
        }
       
        int len = responseString.length();
        if (len > 0 && "&".equals(responseString.substring(len - 1))) {
        	responseString = responseString.substring(0, (len - 1));
        }
        
		response.sendRedirect(response.encodeRedirectURL(redirectPath) + responseString);
	}
}
