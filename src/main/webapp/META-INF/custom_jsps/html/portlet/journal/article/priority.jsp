<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalArticle journalArticle = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

AssetEntry assetEntry = null;		 
		 
if (journalArticle!=null) {
	assetEntry = AssetEntryLocalServiceUtil.fetchEntry(JournalArticle.class.getName(), journalArticle.getResourcePrimKey());
}

double priority = assetEntry==null?1.0:assetEntry.getPriority();
		 
		 
%>

<h3><liferay-ui:message key="priority" /></h3>


<aui:input name="priority" type="text" value="<%=priority %>" />


