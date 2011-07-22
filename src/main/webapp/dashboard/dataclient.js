   
    var info; // data from server
    
    function getData(locationID, datatype) {// get json data from server
    
    	var url;
    	var request;
    	    
    	if (datatype == "temperature") {
    		url = 'http://localhost:8080/findmyapp/locations/' + locationID + '/temperature/latest';
//    		url = 'http://findmyapp.net/findmyapp/locations/' + locationID + '/temperature/latest';
    		request = new ajaxObject(url, processTemperatureData);
    		request.update();  // Server is contacted here.
    	} else if (datatype == "humidity") {
    		//url = 'http://localhost:8080/findmyapp/locations/' + locationID + '/humidity';
    		url = 'http://findmyapp.net/findmyapp/locations/' + locationID + '/humidity/latest';
    		request = new ajaxObject(url, processHumidityData);
    		request.update('limit=20');  // Server is contacted here.
    	} else if (datatype == "noise") {
    		//url = 'http://localhost:8080/findmyapp/locations/' + locationID + '/noise';
    		url = 'http://findmyapp.net/findmyapp/locations/' + locationID + '/noise/latest';
    		request = new ajaxObject(url, processNoiseData);
    		request.update('limit=20');  // Server is contacted here.
    	}  else if (datatype == "users") {
    		//url = 'http://localhost:8080/findmyapp/locations/' + locationID + '/usercount';
    		url = 'http://findmyapp.net/findmyapp/locations/usercount';
    		request = new ajaxObject(url, processUserData);
    		request.update();  // Server is contacted here.
    	} else if (datatype == "beersale") {
    		//url = 'http://localhost:8080/findmyapp/locations/' + locationID + '/beertap/31';
    		url = 'http://findmyapp.net/findmyapp/locations/' + locationID + '/beertap/31/latest';
    		request = new ajaxObject(url, processBeerData);
    		request.update('limit=20');  // Server is contacted here.
    		// show sum as well
    	} else {
    		//alert('No match');
    		//myRequest.update('id=1234&color=blue');  // Server is contacted here.
    	}    	
    }
    	
//    	//Used for testing on localhost
//    	if (datatype == "temperature") {
//    		//url = 'http://localhost:8080/findmyapp/locations/' + locationID + '/temperature';
//    		url = 'http://localhost:8080/findmyapp/locations/' + locationID + '/temperature/latest';
//    		request = new ajaxObject(url, processTemperatureData);
//    		request.update('limit=20');  // Server is contacted here.
//    	} else if (datatype == "humidity") {
//    		//url = 'http://localhost:8080/findmyapp/locations/' + locationID + '/humidity';
//    		url = 'http://localhost:8080/findmyapp/locations/' + locationID + '/humidity/latest';
//    		request = new ajaxObject(url, processHumidityData);
//    		request.update('limit=20');  // Server is contacted here.
//    	} else if (datatype == "noise") {
//    		//url = 'http://localhost:8080/findmyapp/locations/' + locationID + '/noise';
//    		url = 'http://localhost:8080/findmyapp/locations/' + locationID + '/noise/latest';
//    		request = new ajaxObject(url, processNoiseData);
//    		request.update('limit=20');  // Server is contacted here.
//    	}  else if (datatype == "users") {
//    		//url = 'http://localhost:8080/findmyapp/locations/' + locationID + '/usercount';
//    		url = 'http://localhost:8080/findmyapp/locations/usercount';
//    		request = new ajaxObject(url, processUserData);
//    		request.update();  // Server is contacted here.
//    	} else if (datatype == "beersale") {
//    		//url = 'http://localhost:8080/findmyapp/locations/' + locationID + '/beertap/31';
//    		url = 'http://localhost:8080/findmyapp/locations/' + locationID + '/beertap/31/latest';
//    		request = new ajaxObject(url, processBeerData);
//    		request.update('limit=20');  // Server is contacted here.
//    		// show sum as well
//    	} else {
//    		//alert('No match');
//    		//myRequest.update('id=1234&color=blue');  // Server is contacted here.
//    	}    	
//    }
    
    function processBeerData(responseText, responseStatus) {
    	  if (responseStatus==200) {
    		  console.log(responseText);
    		  info = eval(responseText);
    		  drawBeerChart();  		
    	  } else {
    		alert(responseStatus + ' -- Error Processing Request');
    	  }
    }
    
    function processNoiseData(responseText, responseStatus) {
  	  if (responseStatus==200) {
  		  console.log(responseText);
  		  info = eval(responseText);
  		  drawNoiseChart();  		
  	  } else {
  		alert(responseStatus + ' -- Error Processing Request');
  	  }
  	}
    
    function processHumidityData(responseText, responseStatus) {
  	  if (responseStatus==200) {
  		  console.log(responseText);
  		  info = eval(responseText);
  		  drawHumidityChart();  		
  	  } else {
  		alert(responseStatus + ' -- Error Processing Request');
  	  }
  }
    
    function processTemperatureData(responseText, responseStatus) {
    	  if (responseStatus==200) {
    		  console.log(responseText);
    		  info = eval(responseText);
    		  drawTemperatureChart();  		
    	  } else {
    		alert(responseStatus + ' -- Error Processing Request');
    	  }
    }
    
    function processUserData(responseText, responseStatus) {
    	  if (responseStatus==200) {
    		  console.log(responseText);
    		  info = eval(responseText);
    		  drawUserDataChart();
    	  } else {
    		alert(responseStatus + ' -- Error Processing Request');
    	  }
    }
    
    function drawBeerChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Klokkeslett');
        data.addColumn('number', 'Antall enheter');
        
        data.addRows(info.length);
		var i=0;
		for(i=0;i<info.length; i++) {
			data.setValue(i, 0, info[i].time);
			data.setValue(i, 1, info[i].value);
		}
        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
        chart.draw(data, {width: 700, height: 440, title: 'Salg'});
        
    }
    
      function drawTemperatureChart() {
    	  var data = new google.visualization.DataTable();
          data.addColumn('string', 'Label');
          data.addColumn('number', 'Value');
          data.addRows(1);
          data.setValue(0, 0, 'Temperatur');
          data.setValue(0, 1, info[0].value);

          var chart = new google.visualization.Gauge(document.getElementById('chart_div'));
          var options = {width: 200, height: 320, redFrom: 90, redTo: 100,
              yellowFrom:75, yellowTo: 90, minorTicks: 5};
          chart.draw(data, options);
      }
      
      function drawNoiseChart() {
          var data = new google.visualization.DataTable();
          data.addColumn('string', 'Klokkeslett');
          data.addColumn('number', 'Makstemperatur');
          
          data.addRows(info.length);
  		var i=0;
  		for(i=0;i<info.length; i++) {
  			data.setValue(i, 0, info[i].time);
  			data.setValue(i, 1, info[i].max);
  		}
          
          var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
          chart.draw(data, {width: 700, height: 440, title: 'Lydintensitet'});
          
      }
      
      function drawHumidityChart() {
          var data = new google.visualization.DataTable();
          data.addColumn('string', 'Klokkeslett');
          data.addColumn('number', 'Luftfuktighet i prosent');
          
          data.addRows(info.length);
  		var i=0;
  		for(i=0;i<info.length; i++) {
  			data.setValue(i, 0, info[i].time);
  			data.setValue(i, 1, info[i].value);
  		}
          
          var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
          chart.draw(data, {width: 700, height: 440, title: 'Fuktighet'});   
      }
      
      function drawUserDataChart() {
          var data = new google.visualization.DataTable();
          data.addColumn('string', 'Romnavn');
          data.addColumn('number', 'Antall folk');
          
          data.addRows(info.length);
  		var i=0;
  		for(i=0;i<info.length; i++) {
  			data.setValue(i, 0, info[i].locationName);
  			data.setValue(i, 1, info[i].userCount);
  		}
          
          var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
          chart.draw(data, {width: 700, height: 440, title: 'Antall folk på UKA'});   
      }