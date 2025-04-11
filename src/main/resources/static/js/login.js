$(function(){
	console.log("data2 : ",data);
	const form = $("#form");
	form.attr("action",addr+"/login");
	$("<input type='hidden' name='loginurl' value='"+data.loginurl+"'/>").appendTo(form);
	$("<input type='hidden' name='logouturl' value='"+data.logouturl+"'/>").appendTo(form);
	$("<input type='hidden' name='samlRequest' value='"+data.samlRequest+"'/>").appendTo(form);
	$("<input type='hidden' name='relayState' value='"+data.relayState+"'/>").appendTo(form);
	$("<input type='hidden' name='signature' value='"+data.signature+"'/>").appendTo(form);
})