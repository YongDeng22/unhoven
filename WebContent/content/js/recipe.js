/**
 * 
 */

function populateTableHead(tableJson) {
		var head = "";
		if (tableJson) {
			head = "<table class='table table-bordered table-hover table-responsive table-stripped' style='width:auto'><thead><tr>";
			var keys = Object.keys(tableJson);
			for (var i in keys) {
				head += "<th style='border: solid 1px black; padding: 2px; text-align:center'>" + keys[i] + "</th>";
			}
			head += "</tr></thead><tbody>";
		}
		return head;
	}
	
	
	function populateIngredientTabe(ingredients) {
		var ingredientTable = $("#ingredientTable");
		var htmlContent = "";
		if (ingredients && ingredients.length > 0) {
			htmlContent += populateTableHead(ingredients[0]);
			for (var i in ingredients) {
				htmlContent += "<tr>";
				var keys = Object.keys(ingredients[i]);
				var row = ingredients[i];
				for (var j in keys) {
					htmlContent += "<td>" + row[keys[j]] + "</td>";
				}
				htmlContent += "</tr>";
			}
		}
		return htmlContent;
	}
	
	function populatePosterTable(poster) {
		var posterTable = $("#posterTable");
		var htmlContent = "<p>Poster table</p>";
		htmlContent += populateTableHead(poster);
		if (poster) {
			htmlContent += "<tr>";
			var keys = Object.keys(poster);
			for (var i in keys) {
				htmlContent += "<td>" + poster[keys[i]] + "</td>";
			}
			htmlContent += "</tr></tbody></table>";
		}
		posterTable.append(htmlContent);
	}
	
	function getIngredientIDs(ingredients) {
		var ids = "";
		if (ingredients && ingredients.length > 0) {
			for (var i = 0; i < ingredients.length - 1; i++) {
				ids += ingredients[i]["ingredientId"] + ", ";
			}
			ids += ingredients[ingredients.length - 1]["ingredientId"];				
		}
		return ids;
	}