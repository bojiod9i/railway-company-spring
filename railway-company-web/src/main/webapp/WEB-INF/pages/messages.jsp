<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${!empty errorMsg}">
    <div class="dialog" id="message-dialog">
        <img width="40px" height="40px" src="<c:url value='/resources/images/error.png'/>"
             align="left"> <p style="margin: 0.5em; padding-left: 40px;">${errorMsg}</p>
    </div>
</c:if>

<c:if test="${!empty infoMsg}">
    <div class="dialog" id="message-dialog">
        <img width="40px" height="40px" src="<c:url value='/resources/images/info.png'/>"
                align="left"> <p style="margin: 0.5em; padding-left: 40px;"> ${infoMsg} </p>
    </div>
</c:if>
