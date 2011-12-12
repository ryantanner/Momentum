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
		
		$('#map_container').hide();

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

    });


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
	});
