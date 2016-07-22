<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<h1>${message}1</h1>
	<h1>${userName}</h1>


<form action="/msapp/v1/uploadTenderDatumPic" method="post" enctype="multipart/form-data">
	<input type="file" name="datums">
	<input type="input" name="tenderId" value="1"><p/>
	<input type="input" name="uuid" value="018F53B09AC9458689008E6991C20ABA"><p/>
	<input type="input" name="userId" value="802"><p/>
	<input type="input" name="ip" value="192.168.1.1"><p/>
	<input type="input" name="driverName" value="MI-NOTE"><p/>
	<input type="input" name="systemVersion" value="android-6.0"><p/>
	<input type="input" name="position" value="客户端连接zookeeper集群开源中国"><p/>
	<input type="input" name="longitude" value="116.318549"><p/>
	<input type="input" name="latitude" value="40.041318"><p/>
	<input type="input" name="uploadWay" value="1"><p/>
	<input type="input" name="metrialsType" value="1"><p/>
	<input type="input" name="datumId" value="1"><p/>
	<input type="input" name="uploadFrom" value="1"><p/>
	<input type="input" name="datumType" value="11"><%--用于标记第一次上传还是补充上传<p/>--%>
	<input type="submit">
</form>

</body>
</html>