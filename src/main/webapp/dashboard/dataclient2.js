var info; // data from server

function getData(locationID) {// get json data from server

	var url;
	var request;

	testings();
	requestLocationReports(locationID);
	requestTemperature(locationID);
	requestHumidity(locationID);
	
}

// populate temperature chart
function requestTemperature(locationID) {
//	url = 'http://localhost:8080/findmyapp/locations/' + locationID
//			+ '/temperature/latest';
	 url = 'http://findmyapp.net/findmyapp/locations/' + locationID +
	 '/temperature/latest';
	request = new ajaxObject(url, processTemperatureData);
	request.update(); // Server is contacted here.
}

function processTemperatureData(responseText, responseStatus) {
	if (responseStatus == 200) {
		console.log(responseText);
		info = eval(responseText);
		drawTemperatureChart();
	} else {
		alert(responseStatus + ' -- Error Processing Request');
	}
}

function drawTemperatureChart() {
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Label');
	data.addColumn('number', 'Value');
	data.addRows(1);
	data.setValue(0, 0, 'Temperatur');
	data.setValue(0, 1, info[0].value);

	var chart = new google.visualization.Gauge(document
			.getElementById('temp_chart'));
	var options = {
		width : 150,
		height : 240,
		redFrom : 90,
		redTo : 100,
		yellowFrom : 75,
		yellowTo : 90,
		minorTicks : 5
	};
	chart.draw(data, options);
	var d = document.getElementById('temp_chart').firstChild;
	d.style.marginLeft = 'auto';
	d.style.marginRight = 'auto';
}

// populate the livefeed
function requestLocationReports(locationID) {
	console.log("locationID: "+locationID);
	url = 'http://localhost:8080/findmyapp/locations/' + locationID
	+ '/userreports';
	console.log("url: "+url);
	request = new ajaxObject(url, processLocationReports);
	request.update('parname=comment');
}

function processLocationReports(responseText, responseStatus) {
	if (responseStatus == 200) {
		info = eval(responseText);
		console.log("responseText: "+responseText);
		drawLocationReports();
	} else {
		alert(responseStatus + ' -- Error Processing Request');
	}
}

function drawLocationReports() {
	var reportString = "";
	var i = 0;
	console.log("info length: "+info.length);
	for (i = 0; i < info.length; i++) {
		if (i == 0) {
			reportString += info[i].parameterTextValue;
		} else {
			reportString += "      " + info[i].parameterTextValue;
		}
	}
	console.log("reportString: "+reportString);
	document.getElementById('live_feed').innerHTML = reportString;
}

// populate humidity chart
function requestHumidity(locationID) {
//	url = 'http://localhost:8080/findmyapp/locations/' + locationID
//			+ '/humidity/latest';
	 url = 'http://findmyapp.net/findmyapp/locations/' + locationID +
	 '/humidity/latest';
	request = new ajaxObject(url, processHumidityData);
	request.update();
}

function processHumidityData(responseText, responseStatus) {
	if (responseStatus == 200) {
		console.log(responseText);
		info = eval(responseText);
		console.log("humidity info length: "+info.length);
		drawHumidityChart();
	} else {
		alert(responseStatus + ' -- Error Processing Request');
	}
}

function drawHumidityChart() {
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Label');
	data.addColumn('number', 'Luftfuktighet i prosent');
	data.addRows(1);
	data.setValue(0, 0, 'Luftfuktighet');
	data.setValue(0, 1, info[0].value);

	var chart = new google.visualization.Gauge(document
			.getElementById('humidity_chart'));
	var options = {
		width : 150,
		height : 240,
		redFrom : 90,
		redTo : 100,
		yellowFrom : 75,
		yellowTo : 90,
		minorTicks : 5
	};
	chart.draw(data, options);
	var d = document.getElementById('humidity_chart').firstChild;
	d.style.marginLeft = 'auto';
	d.style.marginRight = 'auto';
}

function testings() {

}
