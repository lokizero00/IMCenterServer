<%@ tag pageEncoding="utf-8" %>
<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty errors }">
<h3>错误信息</h3>
    <c:forEach items="${errors }" var="error" varStatus="L">
    		<p><font size="2" color="red">${L.count}. ${error }</font></p>
    </c:forEach> 
</c:if>