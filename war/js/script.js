
    $(function() {
		
		$('#map_container').hide();

		$('#map_canvas').gmap({'center': getLatLng(), 'callback': function () {
				$('#map_canvas').gmap('addMarker', {'position': getLatLng(), 'title': 'Hello world!'});
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

	function getLatLng(callback) {
		
		if ( navigator.geolocation ) {
			 navigator.geolocation.getCurrentPosition ( 
            	function(position) {
                	callback(new google.maps.LatLng(position.coords.latitude,position.coords.longitude));
 				}, 
            	function(error) {
					callback(new google.maps.LatLng(29.464700,-98.481260));	
                }
            )();      
        } else {
					callback(new google.maps.LatLng(29.464700,-98.481260));	
        }
		
	}

	function getDirections(start,end)	{
		// builds DirectionRequest object for google maps api

        $('#map_canvas').gmap('displayDirections', 
				{'origin': start, 
				'destination': end,
				'travelMode': google.maps.DirectionsTravelMode.DRIVING },
				{ 'panel': document.getElementById('directionsResults') },
					function(result, status) {
                		// success callback
        			}
				);

	}
	
	function geolocate()	{
		// geocoder
		
		
		
		
	}




















