
    $(function() {

		$('#map_canvas').gmap({'center': getLatLng(), 'callback': function () {
				$('#map_canvas').gmap('addMarker', {'position': getLatLng(), 'title': 'Hello world!'});
			}
		});

		function getLatLng() {
			if ( google.loader.ClientLocation != null ) {
				return new google.maps.LatLng(google.loader.ClientLocation.latitude, google.loader.ClientLocation.longitude);	
			}
			return new google.maps.LatLng(29.464700,-98.481260);
		}
		
		$('#ex1-terrain').toggle(function () {             
			$('#map_canvas').gmap('option', 'mapTypeId', google.maps.MapTypeId.TERRAIN);
		}, 
		function () { 
			$('#map_canvas').gmap('option', 'mapTypeId', google.maps.MapTypeId.ROADMAP); 
		} 
		);
		
    });




















