$(function() {
	transformRequestClick();
})

function transformRequestClick() {
	$("#transformRequest").on("click", function() {
		console.log($("#data").val());
		$.ajax({
			type: 'post',
			url : window.location.origin+'/SP/trans',
			data: $("#data").val(),
			dataType:'text' , 
			processData: false,
			success: function(result) {
				$("#result").val(result);
			},
			error: function() {
				//에러가 났을 경우 실행시킬 코드
			}
		})


	})
}