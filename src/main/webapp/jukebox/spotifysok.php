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
				<li><a href="votedsongs.php" class="ui-btn-active" data-icon="arrow-l" data-direction="reverse">Back</a></li>
				<li><a href="jquerytest.html" class="ui-btn-active" data-icon="arrow-r">Back</a></li>
			</ul>
		</div>
	</div><!-- /header -->	
	<div data-role="content">
		<div data-role="collapsible" <?php if(isset($_GET["query"])){echo "data-collapsed=false";}?>>
		<h3>S&oslash;k</h3>
			<form action="formtest.php" method="GET" data-transition="fade">
				<div id="search" data-role="fieldcontain">
					<label for="search">Finn spotifysang</label>
					<input type="search" type="text" id="query" name="query" value=""/>
				</div>
				<input type="hidden" id="code" name="code" value="<?php echo urlencode($_GET["code"]); ?>"/>
				<input type="hidden" id="session" name="location" value="<?php echo urlencode($_GET["location"]); ?>"/>
				<input type="submit" value="Submit"/>
			</form>
			<?php
				if (isset($_GET["query"])){
					$request = 'http://findmyapp.net/findmyapp/spotify/'.$_GET["session"].'/songs/search?query='.urlencode($_GET["query"]);
					$opts = array(
				  			'http'=>array(
				    		'method'=>"GET",
				    		'header'=>"Accept:application/json")
				  			);	
					$context = stream_context_create($opts);
					$response = file_get_contents($request, false, $context);
					$response = json_decode($response, true);
					echo "<div data-role=collapsible data-collapsed=false>";
					echo "<h3>S&oslash;keresultater</h3>";
					echo "<ul data-role='listview' data-theme='a' data-filter=true>";
					foreach ($response as $item){
						echo "<li><a href='song.php?location=".$_GET["location"]."&code=".urlencode($_GET["code"])."&spotifyId=".$item[spotifyId]."'>".$item[artist]." - ".$item[title]."<span class=ui-li-count>".$item[activeRequests]."</a></li>";
					}
					echo "</ul>";
					echo "</div>";
				}
			?>
		</div>
		<div data-role="collapsible" <?php if(!isset($_GET["query"])){echo "data-collapsed=false";}?>>
		<h3>Sanger med flest stemmer p&aring; <?php
			$request = 'http://findmyapp.net/findmyapp/spotify/'.$_GET["session"];
			$opts = array(
			  'http'=>array(
			    'method'=>"GET",
			    'header'=>"Accept:application/json"
			  )
			);
			$context = stream_context_create($opts);
			$response = file_get_contents($request, false, $context);
			$response = json_decode($response, true);
			echo $response[locationName];
		?></h3>
		<?php
			$request = 'http://findmyapp.net/findmyapp/spotify/'.$_GET["location"].'/songs';
			$opts = array(
			  'http'=>array(
			    'method'=>"GET",
			    'header'=>"Accept:application/json"
			  )
			);
			$context = stream_context_create($opts);
			$response = file_get_contents($request, false, $context);
			$response = json_decode($response, true);
			echo "<ul data-role='listview' data-theme='a' data-filter=true>";
			foreach ($response as $item){
				echo "<li><a href='song.php?location=".$_GET["location"]."&code=".urlencode($_GET["code"])."&spotifyId=".$item[spotifyId]."'>".$item[artist]." - ".$item[title]."<span class=ui-li-count>".$item[activeRequests]."</span></a></li>";
			}
			echo "</ul>";
		?>
		</div>
	</div>
	<div data-role="footer" ui-state-persist="true">
		<h4>Spotify App</h4>
	</div><!-- /footer -->
</div><!-- /page -->

</body>
</html></em>