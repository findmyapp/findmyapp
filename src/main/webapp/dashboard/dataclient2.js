var info; // data from server
var todaysEvents;
var tomorrowsEvents;

function getData(locationID, locationName, datatype) {// get json data from server
	var url;
	var request;

	testings();
	requestLocationReports(locationID);
	requestTemperature(locationID);
	requestHumidity(locationID);
	requestTodaysEvents(locationName);
	requestTomorrowsEvents(locationName);
}

function requestTodaysEvents(){
	var url = 'http://localhost:8080/findmyapp/program/uka11/places/storsalen';
	var request = new ajaxObject(url, processTodaysEvents);
	request.update();  // Server is contacted here
}

function requestTomorrowsEvents(){
	url = 'http://localhost:8080/findmyapp/program/uka11/places/dodensdal';
	request = new ajaxObject(url, processTomorrowsEvents);
	request.update();  // Server is contacted here
}

function processTodaysEvents(responseText, responseStatus){
	if (responseStatus==200) {
		
		//console.log(responseText);
		todaysEvents = eval(responseText);
		updateEventList('today');  	
	}
}

function processTomorrowsEvents(responseText, responseStatus){
	if (responseStatus==200) {
		
		//console.log(responseText);
		tomorrowsEvents = eval(responseText);
		updateEventList('tomorrow');  	
	}
}

function updateEventList(day){
	var list;
	var table;
	
	if (day == 'today'){
		list = todaysEvents;
		table = document.getElementById ("event-table-today");
	} else {
		list = tomorrowsEvents;
		table = document.getElementById ("event-table-tomorrow");
	}
	
	var i=0;
	var row;
	var cell;
	
	//console.log('no of events: ' + list.length)
	for(i = 0; i < list.length; i++) {
		
		row = table.insertRow (-1);
		
		var arg = "showEvent(" + i +",'" + day + "');";
        row.setAttribute("onClick",arg);
        
        cell = row.insertCell (0);
        var date = new Date(list[i].showingTime); 
        //console.log(date);
        cell.innerHTML = date.format("HH:MM"); 
        
        cell = row.insertCell (1);
       	cell.innerHTML = list[i].title;
       	
	}
}

function showEvent(index, day) {
	
	if (day == 'today'){
		list = todaysEvents;
	} else {
		list = tomorrowsEvents;
	}
	
	//console.log("showing event for: " + day + " with index: " + index);
	var eventDiv = document.getElementById ("event-info-div");
	var roomDiv = document.getElementById ("room-info-div");
	
	eventDiv.innerHTML = "";
	var h1 = document.createElement("h1");
	h1.innerHTML = list[index].title;
	eventDiv.appendChild(h1);
	
	var img = document.createElement('img');
	img.setAttribute("id", "event-close-button");
	img.setAttribute("src","close_button.gif");
	img.onclick = function () {eventDiv.style.visibility="hidden"; roomDiv.style.visibility="visible";};
	eventDiv.appendChild(img);
	
	var p = document.createElement("p");
	p.innerHTML = list[index].lead;
	eventDiv.appendChild(p);
	eventDiv.style.visibility="visible";
	roomDiv.style.visibility="hidden";
}

// populate temperature chart
function requestTemperature(locationID) {
	url = 'http://localhost:8080/findmyapp/locations/' + locationID
			+ '/temperature/latest';
//	 url = 'http://findmyapp.net/findmyapp/locations/' + locationID +
//	 '/temperature/latest';
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
	url = 'http://localhost:8080/findmyapp/locations/' + locationID
	+ '/userreports';
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
	for (i = 0; i < info.length; i++) {
		if (i == 0) {
			reportString += info[i].parameterTextValue;
		} else {
			reportString += "                                                          " + info[i].parameterTextValue;
			var blankCharacter = "&" + "nbsp";
			reportString = reportString.replace(/ /gi, blankCharacter);
		}
	}
	document.getElementById('live_feed').innerHTML = reportString;
}

// populate humidity chart
function requestHumidity(locationID) {
	url = 'http://localhost:8080/findmyapp/locations/' + locationID
			+ '/humidity/latest';
//	 url = 'http://findmyapp.net/findmyapp/locations/' + locationID +
//	 '/humidity/latest';
	request = new ajaxObject(url, processHumidityData);
	request.update();
}

function processHumidityData(responseText, responseStatus) {
	if (responseStatus == 200) {
		//console.log(responseText);
		info = eval(responseText);
		// console.log("humidity info length: "+info.length);
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
