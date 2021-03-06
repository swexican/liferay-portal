<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/ui/categorization_filter/init.jsp" %>

<%
String assetType = GetterUtil.getString((String)request.getAttribute("liferay-ui:categorization-filter:assetType"), "content");
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-ui:categorization-filter:portletURL");

if (Validator.isNull(portletURL)) {
	portletURL = renderResponse.createRenderURL();
}

long assetCategoryId = ParamUtil.getLong(request, "categoryId");
String assetTagName = ParamUtil.getString(request, "tag");

String assetCategoryTitle = null;
String assetVocabularyTitle = null;

if (assetCategoryId != 0) {
	AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getAssetCategory(assetCategoryId);

	assetCategory = assetCategory.toEscapedModel();

	assetCategoryTitle = assetCategory.getTitle(locale);

	AssetVocabulary assetVocabulary = AssetVocabularyLocalServiceUtil.getAssetVocabulary(assetCategory.getVocabularyId());

	assetVocabulary = assetVocabulary.toEscapedModel();

	assetVocabularyTitle = assetVocabulary.getTitle(locale);
}
%>

<liferay-util:buffer var="removeCategory">
	<c:if test="<%= assetCategoryId != 0 %>">
		<span class="asset-entry">
			<%= assetCategoryTitle %>

			<portlet:renderURL var="viewURLWithoutCategory">
				<portlet:param name="categoryId" value="0" />
			</portlet:renderURL>

			<a href="<%= viewURLWithoutCategory %>" title="<liferay-ui:message key="remove" />">
				<span class="aui-icon aui-icon-close aui-textboxlistentry-close"></span>
			</a>
		</span>
	</c:if>
</liferay-util:buffer>

<liferay-util:buffer var="removeTag">
	<c:if test="<%= Validator.isNotNull(assetTagName) %>">
		<span class="asset-entry">
			<%= assetTagName %>

			<portlet:renderURL var="viewURLWithoutTag">
				<portlet:param name="tag" value="" />
			</portlet:renderURL>

			<a href="<%= viewURLWithoutTag %>" title="<liferay-ui:message key="remove" />">
				<span class="aui-icon aui-icon-close aui-textboxlistentry-close"></span>
			</a>
		</span>
	</c:if>
</liferay-util:buffer>

<c:choose>
	<c:when test="<%= (assetCategoryId != 0) && Validator.isNotNull(assetTagName) %>">

		<%
		AssetUtil.addPortletBreadcrumbEntries(assetCategoryId, request, portletURL);

		AssetUtil.addPortletBreadcrumbEntry(request, assetTagName, currentURL);

		PortalUtil.addPageKeywords(assetCategoryTitle, request);
		PortalUtil.addPageKeywords(assetTagName, request);
		%>

		<h1 class="taglib-categorization-filter entry-title">
			<liferay-ui:message arguments="<%= new String[] {assetVocabularyTitle, removeCategory, removeTag} %>" key='<%= assetType.concat("-with-x-x-and-tag-x") %>' />
		</h1>
	</c:when>
	<c:when test="<%= assetCategoryId != 0 %>">

		<%
		AssetUtil.addPortletBreadcrumbEntries(assetCategoryId, request, portletURL);

		PortalUtil.addPageKeywords(assetCategoryTitle, request);
		%>

		<h1 class="taglib-categorization-filter entry-title">
			<liferay-ui:message arguments="<%= new String[] {assetVocabularyTitle, removeCategory} %>" key='<%= assetType.concat("-with-x-x") %>' />
		</h1>
	</c:when>
	<c:when test="<%= Validator.isNotNull(assetTagName) %>">

		<%
		AssetUtil.addPortletBreadcrumbEntry(request, assetTagName, currentURL);

		PortalUtil.addPageKeywords(assetTagName, request);
		%>

		<h1 class="taglib-categorization-filter entry-title">
			<liferay-ui:message arguments="<%= removeTag %>" key='<%= assetType.concat("-with-tag-x") %>' />
		</h1>
	</c:when>
</c:choose>