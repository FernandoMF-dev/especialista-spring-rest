function findRestaurants() {
	$.ajax({
		url: "http://localhost:8080/api/restaurants",
		type: "GET",
		success: function (res) {
			$("#req-content").text(JSON.stringify(res));
		}
	})
}

function closeRestaurant() {
	$.ajax({
		url: "http://localhost:8080/api/restaurants/1/open?value=false",
		type: "PATCH",
		success: function (res) {
			alert("Restaurant closed");
		}
	})
}

$("#req-button").click(closeRestaurant);
