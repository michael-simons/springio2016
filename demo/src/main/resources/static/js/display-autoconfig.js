$(function () {
    $.get('/autoconfig').done(
	function (result) {	
	    var addThymeleafBannerConditions = function(mark, values) {
		var autoconfigView = $('.autoconfig tbody');
		for (key in values) {	
		    if(key.startsWith('ThymeleafBanner')) {
			var conditions = $('<ul>').append(values[key].map(function(e) {
			    return $('<li>').append(
				    document.createTextNode(e.condition), 
				    $('<br/>'), 
				    document.createTextNode(e.message)
			    );
			}));
			autoconfigView.append(
			    $('<tr>').append(
				$('<td>').append($('<p>').text(key),					
				$('<td>').append(conditions)),
				$('<td>').text(mark)
			    )						
			);   
		    }
		}    
	    };

	    addThymeleafBannerConditions('✅', result.positiveMatches); // White check mark on green
	    addThymeleafBannerConditions('❌', result.negativeMatches); // Red x
	}
    );
});