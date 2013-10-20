
<%@ page import="org.itech.klinikav2.domain.Payment" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="${message(code: 'payment.label', default: 'Payment')}" />
	<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
	
<section id="list-payment" class="first">

	<table class="table table-bordered">
		<thead>
			<tr>
				
				
				<th><g:message code="payment.patient.label" default="Patient" /></th>
				<g:sortableColumn property="amountPaid" title="${message(code: 'payment.amountPaid.label', default: 'Amount Paid')}" />
			
			
				
			
			</tr>
		</thead>
		<tbody>
		<g:each in="${paymentInstanceList}" status="i" var="paymentInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
							
				<td>${fieldValue(bean: paymentInstance, field: "patient")}</td>
				<td><g:link action="show" id="${paymentInstance.id}">${fieldValue(bean: paymentInstance, field: "amountPaid")}</g:link></td>
			
			

			
			</tr>
		</g:each>
		</tbody>
	</table>
	
	<div class="pagination">
		<bs:paginate total="${paymentInstanceTotal}" />
	</div>
</section>

</body>

</html>
