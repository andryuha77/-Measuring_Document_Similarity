<%@ include file="includes/header.jsp"%>

<div class="animated bounceInDown"
	style="font-size: 32pt; font-family: arial; color: #990000; font-weight: bold">Document
	Comparison Service</div>
</p>
&nbsp;
</p>
&nbsp;
</p>

<table width="600" cellspacing="0" cellpadding="7" border="0">

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/lib/jquery-3.2.1.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/index.js"></script>


	<form method="POST" enctype="multipart/form-data" action="upload">
		<fieldset>
			<b>Document Title :</b><br> <input id="title" name="txtTitle"
				type="text" placeholder="Please Choose File first" size="50" />
			<p />
			<p id="error">
				<font color="RED">${message}</font>
			</p>
			<input id="file" type="file" name="txtDocument" />
			<center>
				<button id="submitBt" onClick="return Validate()"
					disabled="disabled">Compare Document</button>
			</center>
		</fieldset>
	</form>

	<%@ include file="includes/footer.jsp"%>