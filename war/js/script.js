function getLatLng(callback) {
	if (callback)	{
		if ( navigator.geolocation ) {
			 navigator.geolocation.getCurrentPosition( 
	        	function(position) {
					var pos = new google.maps.LatLng(position.coords.latitude,position.coords.longitude);
	            	callback.call(pos);
				}, 
	        	function(error) {
					var pos = new google.maps.LatLng(29.464700,-98.481260);
					callback(pos);	
	            }
	        );      
	    } else {
			var pos = new google.maps.LatLng(29.464700,-98.481260);
			callback.call(pos);	
	    }
	}
}


    $(document).ready(function() {
		
		//$('#map_container').hide();



    });

	getLatLng(function(position) {
		var center = position;
		$('#map_canvas').gmap({'center': getLatLng(), 'callback': function () {
				$('#map_canvas').gmap('addMarker', {'position': center, 'title': 'Hello world!'});
			}
		});

		$('#ex1-terrain').toggle(function () {             
			$('#map_canvas').gmap('option', 'mapTypeId', google.maps.MapTypeId.TERRAIN);
		}, 
		function () { 
			$('#map_canvas').gmap('option', 'mapTypeId', google.maps.MapTypeId.ROADMAP); 
		} 
		);
	});


	function getDirections(start,end)	{
		// builds DirectionRequest object for google maps api

        $('#map_canvas').gmap('displayDirections', 
				{'origin': '"' + start + '"',
				'destination': '"' + end + '"',
				'travelMode': google.maps.DirectionsTravelMode.DRIVING },
				{ 'panel': document.getElementById('directionsResults') },
					function(result, status) {
						// get latlng points from result
						var divisor = Math.floor(result.routes[0].overview_path.length/5);
						var points = Array();
						for (var i = 0; i < 5; i++)	{
							points.push(result.routes[0].overview_path[divisor*i]);
						}
						getWeatherForPoints(points);
						$('#input_form').slideUp(1000,function () { 
							$('#map_container').removeClass('hidden-map'); 
							$('#directionsResults').removeClass('hidden');
						});
        			}
				);

	}
	
	function getWeatherForPoints(points)	{
		// points is an array of latlng objects
		var pointsQSt = "";
		for (var i = 0; i < 5; i++)	{
			pointsQSt += "location" + i + "=" + points[i].lat() + "," + points[i].lng() + "&";
		}
		//$.getJSON('http://trinitymomentum.appspot.com/momentum?' + pointsQSt, function (data) {
		$.getJSON('http://trinitymomentum.appspot.com/momentum?test=adsfsd', function (data) {
			$.each(data.markers, function (i,m) {
				// make marker
				var markerOpts = {'position' : new google.maps.LatLng(m.latitude,m.longitude),
								  'icon' : m.icon_url,
								  'bounds' : false};
				var infoWindowOpts = {'content' : 	'<h1>' + m.location + '</h1>' + 
													'Conditions: <strong>' + m.weather + '</strong><br />' + 
													'Wind: <strong>' + m.wind_mph + ' mph</strong><br />' +
													'Temp: <strong>' + m.temperature + '</strong><br />'};
				$('#map_canvas').gmap('addMarker', markerOpts).click(function () {
					$('#map_canvas').gmap('openInfoWindow', infoWindowOpts, this);							
				});
			}); 
		});
	}
	
	function geolocate(callback)	{
		// geocoder
		// returns string representing user location
		if (callback) {
			getLatLng(function(position)	{
				var geocoder = new google.maps.Geocoder();
				geocoder.geocode({'latLng': position},function(results,status) {
					if (status == google.maps.GeocoderStatus.OK) {
				        if (results[1]) {
				          callback(results[1].formatted_address);
				        }
			      } else {
			        	$('#geolocation_error').fadeIn(1000,function () {
							setTimeout(function () { $('#geolocation_error').fadeOut(1000); }, 1500);
						});
						
						callback("");
			      }
				});
			});
		}
		else
			alert("error");
	}
		
	$(document).ready(function($) {
		
		$('#geolocater').click(function() {
			geolocate(function(pos) { if (pos != "") $('#from').val(pos); });
			return false;
		});
		
		$('#getDirections').unbind('click').click(function () {
			
			if ($('#from').val() === "")	{
	        	$('#from_error').fadeIn(1000,function () {
					setTimeout(function () { $('#from_error').fadeOut(1000); }, 1500);
				});				
				return false;
			}
			else if ($('#to').val() === "")	{
	        	$('#to_error').fadeIn(1000,function () {
					setTimeout(function () { $('#to_error').fadeOut(1000); }, 1500);
				});				
				return false;
			}
			else	{
				getDirections($('#from').val(),$('#to').val());				
			}
			
			return false;
		});
	});
