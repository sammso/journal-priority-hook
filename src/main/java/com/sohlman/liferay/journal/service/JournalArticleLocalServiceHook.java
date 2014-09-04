package com.sohlman.liferay.journal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalService;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceWrapper;

import java.io.Serializable;
import java.util.Map;

public class JournalArticleLocalServiceHook extends JournalArticleLocalServiceWrapper {
	/* (non-Java-doc)
	 * @see com.liferay.portlet.journal.service.JournalArticleLocalServiceWrapper#JournalArticleLocalServiceWrapper(JournalArticleLocalService journalArticleLocalService)
	 */
	public JournalArticleLocalServiceHook(JournalArticleLocalService journalArticleLocalService) {
		super(journalArticleLocalService);
		assetEntryLocalService = AssetEntryLocalServiceUtil.getService();
	}
	
	public JournalArticle updateStatus(
			long userId, JournalArticle article, int status, String articleURL,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException, SystemException {
		
		JournalArticle journalArticle = getWrappedService().updateStatus(userId, article, status, articleURL, workflowContext, serviceContext);
		
		AssetEntry assetEntry = assetEntryLocalService.fetchEntry(JournalArticle.class.getName(), journalArticle.getResourcePrimKey());
		
		String priorityString = (String)serviceContext.getAttribute("priority");
		
		Double priority = Double.valueOf(priorityString);
		
		assetEntry.setPriority(priority);
		
		assetEntryLocalService.updateAssetEntry(assetEntry);
		
		final Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				JournalArticle.class);
		
		indexer.reindex(journalArticle);
		
		return journalArticle;
	}

	AssetEntryLocalService assetEntryLocalService;
}