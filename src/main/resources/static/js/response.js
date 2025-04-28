$(function(){
	console.log("data : ",data);
	const form = $("#form");
	form.attr("action",data.target);
	form.attr("method", data.method);
	form.submit();
})