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
				<li><a href="formtest.php?session=<?php  echo $_GET["location"]."&code=".$_GET["code"]; ?>" class="ui-btn-active" data-icon="arrow-l">Tilbake til s&oslash;k</a></li>
				<li><a href="jquerytest.html" class="ui-btn-active" data-icon="arrow-r">Back</a></li>
			</ul>
		</div>
	</div><!-- /header -->
	<div data-role="content">
		<?php
		$request = 'http://findmyapp.net/findmyapp/spotify/'.$_GET["location"].'/songs/'.$_GET["spotifyId"];
		$opts = array(
		  'http'=>array(
		    'method'=>"GET",
		    'header'=>"Accept:application/json"
		  )
		);
		$context = stream_context_create($opts);
		$response = file_get_contents($request, false, $context);
		$response = json_decode($response, true);
		echo "<form action=songRequestResponse.php method=GET data-transition=fade>";
		echo "<div id=search data-role=fieldcontain>";
		echo "<label>Artist: </label><label>".$response[artist]."</label></br>";
		echo "<label>Tittel: </label><label>".$response[title]."</label></br>";
		echo "<label>Stemmer: </label><label>".$response[activeRequests]."</label></br>";
		$length = $response[length]/1000;
		$seconds = $length % 60;
		$minutes = ($length -$seconds)/60;
		echo "<label>Lengde: </label><label>".$minutes.":".$seconds."</label>";
		echo "</div>";
		echo "<input type=hidden id=location name=location value='".$_GET["location"]."'/>";
		echo "<input type=hidden id=code name=code value='".$_GET["code"]."'/>";
		echo "<input type=hidden id=spotifyId name=spotifyId value='".$_GET["spotifyId"]."'/>";
		echo "<input type=submit value=Stem p\aring; sang/>";
		echo "</form>";
		?>
	</div>
	<div data-role="footer" ui-state-persist="true">
		<h4>Spotify App</h4>
	</div><!-- /footer -->
</div><!-- /page -->

</body>
</html></em>