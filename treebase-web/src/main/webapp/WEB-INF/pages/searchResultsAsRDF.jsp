<?xml version="1.0" encoding="UTF-8"?>
<%@ include file="/common/taglibs.jsp"%>
<% response.setContentType("application/rdf+xml"); %>
<rdf:RDF
  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
  xmlns:dcterms="http://purl.org/dc/terms/"
  xmlns:dc="http://purl.org/dc/elements/1.1/"
  xmlns="http://purl.org/rss/1.0/">
  <channel rdf:about="${baseURL}/${phyloWSPath}?query=<c:out value="${normalizedCQLQuery}" escapeXml="true"/>&amp;format=${format}&amp;recordSchema=${recordSchema}">
    <title>${phyloWSPath}</title>
    <link>${baseURL}</link>
    <description>${searchResultsThawed.description}</description>
    <image rdf:resource="${domainAddress}<fmt:message key="icons.treebase.16px"/>"/>
    <items>
      <rdf:Seq>      
		<c:forEach var="result" items="${searchResultsThawed.results}" varStatus="status">
			<rdf:li rdf:resource="${baseURL}/<c:out value="${result.phyloWSPath}"/>"/>
		</c:forEach>      
      </rdf:Seq>
    </items>
  </channel>
  <image rdf:about="${domainAddress}<fmt:message key="icons.treebase.16px"/>">
    <title>${phyloWSPath}</title>
    <link>${baseURL}</link>
    <url>${domainAddress}<fmt:message key="icons.treebase.16px"/></url>
  </image>
  <c:forEach var="result" items="${searchResultsThawed.results}" varStatus="status">
  <item rdf:about="${baseURL}/<c:out value="${result.phyloWSPath}"/>">
    <title><c:out value="${result.namespacedGUID}"/></title>
    <link>${baseURL}/<c:out value="${result.phyloWSPath}"/></link>
    <description><c:out value="${result.namespacedGUID}"/></description>
  </item>  
  </c:forEach>
</rdf:RDF>