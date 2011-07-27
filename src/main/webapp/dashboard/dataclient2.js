var humidity;
var temperature;
var comments;
var userCount;
var todaysEvents;
var tomorrowsEvents;
var server = 'http://localhost:8080/';
//var server = 'http://findmyapp.net/';


function getData(locationID, locationName) {// get json data from server
	requestUsers(locationID);
	requestLocationReports(locationID);
	requestTemperature(locationID);
	requestHumidity(locationID);
	requestTodaysEvents(locationName);
	requestTomorrowsEvents(locationName);
}

function requestTodaysEvents(locationName){
	var url = server + 'findmyapp/program/uka11/places/'+ locationName + '/today';
	var request = new ajaxObject(url, processTodaysEvents);
	request.update();  // Server is contacted here
}

function requestTomorrowsEvents(locationName){
	var url = server + 'findmyapp/program/uka11/places/'+ locationName + '/tomorrow';
	var request = new ajaxObject(url, processTomorrowsEvents);
	request.update();  // Server is contacted here
}

function processTodaysEvents(responseText, responseStatus){
	if (responseStatus==200) {
		console.log(responseText);
		todaysEvents = eval(responseText);
		updateEventList('today');  	
	} else {
		console.log(responseStatus + ' -- Error Processing Request for todays events');
	}
}

function processTomorrowsEvents(responseText, responseStatus){
	if (responseStatus==200) {
		console.log(responseText);
		tomorrowsEvents = eval(responseText);
		updateEventList('tomorrow');  	
	} else {
		console.log(responseStatus + ' -- Error Processing Request request for tomorrows events');
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
	if (list.length == 0) {
		row = table.insertRow (-1);
        cell = row.insertCell (0);
        cell.innerHTML = 'Ingen arrangement';
	} else {
		for(i = 0; i < list.length; i++) {
			if (i > 4){
				break;
			}
			row = table.insertRow (-1);
			var arg = "showEvent(" + i +",'" + day + "');";
	        row.setAttribute("onClick",arg);
	        
	        cell = row.insertCell (0);
	        var date = new Date(list[i].showingTime); 
	        cell.innerHTML = date.format("HH:MM"); 
	        
	        cell = row.insertCell (1);
	       	cell.innerHTML = list[i].title;
	       	//console.log(date);	
		}
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

function requestUsers(locationID) {
	var url = server + 'findmyapp/locations/' + locationID + '/users/count';
	var request = new ajaxObject(url, processUserData, locationID);
	request.update();  // Server is contacted here.
}

function processUserData(responseText, responseStatus, locationID) {
	  if (responseStatus==200) {
		  console.log(responseText);
		  var hax = "[" +responseText+"]";
		  userCount = eval(hax);
		  drawUserDataChart();
	  } else {
		console.log(responseStatus + ' -- Error Processing Request for user count');
	  }
}

function drawUserDataChart() {
	document.getElementById('user_count').innerHTML = "[" + userCount[0].usercount + "]";
	console.log("usercount is...: " + userCount[0].usercount);
}

// populate temperature chart
function requestTemperature(locationID) {
	var url = server + 'findmyapp/locations/' + locationID
			+ '/temperature/latest';
	var request = new ajaxObject(url, processTemperatureData);
	request.update(); // Server is contacted here.
}

function processTemperatureData(responseText, responseStatus) {
	if (responseStatus == 200) {
		console.log(responseText);
		temperature = eval(responseText);
		drawTemperatureChart();
	} else {
		console.log(responseStatus + ' -- Error Processing Temperature Request');
	}
}

function drawTemperatureChart() {
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Label');
	data.addColumn('number', 'Value');
	data.addRows(1);
	data.setValue(0, 0, 'Temperatur');
	data.setValue(0, 1, temperature[0].value);

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
	var url = server + 'findmyapp/locations/' + locationID
	+ '/userreports';
	var request = new ajaxObject(url, processLocationReports);
	request.update('parname=comment');
}

function processLocationReports(responseText, responseStatus) {
	if (responseStatus == 200) {
		comments = eval(responseText);
		console.log("responseText: "+responseText);
		drawLocationReports();
	} else {
		console.log(responseStatus + ' -- Error Processing Request for comments (location reports)');
	}
}

function drawLocationReports() {
	var reportString = "";
	var i = 0;
	for (i = 0; i < comments.length; i++) {
		if (i == 0) {
			reportString += comments[i].parameterTextValue;
		} else {
			reportString += "                                                          " + comments[i].parameterTextValue;
			var blankCharacter = "&" + "nbsp";
			reportString = reportString.replace(/ /gi, blankCharacter);
		}
	}
	document.getElementById('live_feed').innerHTML = reportString;
}

// populate humidity chart
function requestHumidity(locationID) {
	var url = server + 'findmyapp/locations/' + locationID
			+ '/humidity/latest';
	var request = new ajaxObject(url, processHumidityData);
	request.update();
}

function processHumidityData(responseText, responseStatus) {
	if (responseStatus == 200) {
		//console.log(responseText);
		humidity = eval(responseText);
		// console.log("humidity length: " + humidity.length);
		drawHumidityChart();
	} else {
		console.log(responseStatus + ' -- Error Processing Request for humidity data');
	}
}

function drawHumidityChart() {
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'Label');
	data.addColumn('number', 'Luftfuktighet i prosent');
	data.addRows(1);
	data.setValue(0, 0, 'Luftfuktighet');
	data.setValue(0, 1, humidity[0].value);

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
