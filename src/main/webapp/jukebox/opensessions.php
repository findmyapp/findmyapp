
<em><!DOCTYPE html> 
<html> 
	<head> 
	<title>Page Title</title> 
	
	<meta name="viewport" content="width=device-width, initial-scale=1"> 

	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.0b3/jquery.mobile-1.0b3.min.css" />
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.6.3.min.js"></script>
	<script type="text/javascript" src="http://code.jquery.com/mobile/1.0b3/jquery.mobile-1.0b3.min.js"></script>
</head> 
<body> 

<div data-role="page">
	<div data-role="header">
		<div data-role="navbar" data-theme="c">
			<h1>Spotify App</h1>
			<ul>
				<li><a href="jquerytest.html" class="ui-btn-active" data-icon="arrow-r">Back</a></li>
				<li><a href="jquerytest.html" class="ui-btn-active" data-icon="arrow-r">Back</a></li>
			</ul>
		</div>
	</div><!-- /header -->
	<div data-role="content">
	<?php
	$request = 'http://findmyapp.net/findmyapp/spotify';
	$opts = array(
	  'http'=>array(
	    'method'=>"GET",
	    'header'=>"Accept:application/json"
	  )
	);

	$context = stream_context_create($opts);
	$response = file_get_contents($request, false, $context);
	$response = json_decode($response, true);
	echo "<ul data-role='listview' data-theme='a'>";
	$code = "placeholder";
	if (isset($_GET["code"])){
		$code = urlencode($_GET["code"]);
	}
	foreach ($response as $item){
		echo "<li><a href='spotifysok.php?location=".$item[locationId]."&code=".$code."'>Stem i ".$item[locationName]."</a></li>";
	}
	echo "</ul>";
	?>
	</div>
	<div data-role="footer" ui-state-persist="true">
		<h4>Spotify App</h4>
	</div><!-- /footer -->
</div><!-- /page -->

</body>
</html></em>