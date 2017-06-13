define(['jquery'], function($){
	
	$(function(){
		var $social = $(".social-share");
		if ($social.length > 0) {
			$social.find("a.sns").each(function(i, el){
				var $btn = $(el),
					type;
				
				if ($btn.hasClass("sns-facebook")) {
					type = "facebook";
				} else if ($btn.hasClass("sns-pinterest")) {
					type = "pinterest";
				}
				
				$btn.on("click", function(event){
					event.preventDefault();
					var params = {'type': type, 'url': location.href},
						title = $(this).data('title'),
						image = $(this).data('image'),
						description = $(this).data('description');
					
					if (title === undefined) {
						if ($('meta[name=title]').length > 0) {
							title = $('meta[name=title]').prop('content');
						} else {
							title = $('title').text();
						}
					}
					params.title = title;
					
					if (description === undefined) {
						if ($('meta[name=description]').length > 0) {
							description = $('meta[name=description]').prop('content');
						} else {
							description = title;
						}
					}
					params.description = description;
					
					if (image === undefined) {
						if ($('meta[property="og:image"]').length > 0) {
							image = $('meta[property="og:image"]').prop('content');
						} else {
							$('main img').each(function(i, el){
								var _src = $(el).prop('src');
								if (!/logo\.png$/.test(_src)) {
									image = _src;
									return false;
								}
							});
						}
					}
					params.image = image;
					toSNS.call(this, params);
				});
			});
			
		    function toSNS(params) {		
		    	var url,
		    		popOption = "width=370, height=360, resizable=no, scrollbars=no, status=no;";
		        switch (params.type) {
			        case 'facebook': {
			        	url = 'http://www.facebook.com/share.php?u=' + encodeURIComponent(params.url);
			        }break;
			        case 'pinterest': {
			        	url = "http://www.pinterest.com/pin/create/button/?url=" + encodeURIComponent(params.url) + "&description=" + encodeURIComponent(params.description);
			        	if (!!params.image) {
			        		url += "&media=" + params.image;
			        	}
			        	popOption = "width=700, height=500, resizable=no, scrollbars=no, status=no;";
			        }break;
		        }
		        
		        window.open(url, params.type, popOption);
		    }
		}		
	});
});