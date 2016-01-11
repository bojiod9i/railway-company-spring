<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

  <!-- Tabs-->
  <ul>
    <li><a href="#tabs-1"> <spring:message code="employees.label"/> </a>
  </ul>

  <!-- Content Tab 1 -->

  <div id="tabs-1">
    <jsp:include page="employeeList.jsp"/>
  </div>

</div>