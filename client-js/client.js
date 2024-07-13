function findRestaurants() {
	$.ajax({
		url: "http://localhost:8080/api/restaurants",
		type: "GET",
		success: function (res) {
			$("#req-content").text(JSON.stringify(res));
		}
	})
}

$("#req-button").click(findRestaurants);
