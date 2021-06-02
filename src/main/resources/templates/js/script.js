
function changeDisplay() {
	
	var radioT0 = document.getElementById("type0");
	var radioT1 = document.getElementById("type1");
	var zagCat = document.getElementsByClassName("zagCat")[0];
	var inc = document.getElementsByClassName("income")[0];
	var exp = document.getElementsByClassName("expense")[0];
	
	if(radioT0.checked) {
		zagCat.style.display = "";
		inc.style.display = "";
		exp.style.display = "none";
	} else if(radioT1.checked) {
		zagCat.style.display = "";
		inc.style.display = "none";
		exp.style.display = "";
	}
}