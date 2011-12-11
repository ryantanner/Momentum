

				jQuery(function(){
				// Execute this code when the page is ready to work
				// Create a Script Tag
	 			var script=document.createElement('script');
	 			script.type='text/javascript';
	 			script.src= "http://search.twitter.com/search.json?&q=%23Weather&callback=processTheseTweets&_="+ new Date().getTime();
				// Add the Script to the Body element, which will in turn load the script and run it.
	 			$("body").append(script);
				});

				function processTheseTweets(jsonData){
				var shtml = '';
	 			var results = jsonData.results;
	 			if(results){
				// if there are results (it should be an array), loop through it with a jQuery function
	 			$.each(results, function(index,value){
	 			shtml += "<p class='title'><span class='author'>" + value.from_user + "</span>: " +
	 			value.text + "<span class='date'>" + value.created_at + "</span></p>";
	 			});

				// Load the HTML in the #tweet_stream div
	 			$("#tweet_stream").html( shtml );
	 			}
			}
				
				(function() {
				    var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
				    po.src = 'https://apis.google.com/js/plusone.js';
				    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
				  })();
				
				(function(d, s, id) {
					  var js, fjs = d.getElementsByTagName(s)[0];
					  if (d.getElementById(id)) return;
					  js = d.createElement(s); js.id = id;
					  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
					  fjs.parentNode.insertBefore(js, fjs);
					}(document, 'script', 'facebook-jssdk'));