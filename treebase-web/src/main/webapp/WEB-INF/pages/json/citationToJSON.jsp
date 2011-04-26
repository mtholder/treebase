<%@ include file="/common/taglibs.jsp"%>
<c:if test="${citation == null}">null</c:if>
<c:if test="${citation != null}">
{	
	id:${citation.id},
	citationType:'${citation.citationType}',
	<c:choose>
	<c:when test="${citation.citationType == 'Article'}">
	journal:'${citation.journal}'
	</c:when>
	<c:when test="${citation.citationType == 'Book'}">
	booktitle:'${citation.bookTitle}'
	</c:when>
	<c:when test="${citation.citationType == 'Book Section'}">
	booktitle:'${citation.bookTitle}',
	sectiontitle:'${citation.sectionTitle}',
	</c:when>
	</c:choose>
}
</c:if>