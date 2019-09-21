$('.navbar-nav a[href^="#"]').on('click',
	function(e){
		e.preventDefault();
		var id = $(this).attr('href'),
		targetOffset = $(id).offset().top;

		$('html, body').animate({
			scrollTop: targetOffset - 100
		}, 500);
	});

jQuery(function(){
	jQuery(window).scroll(function(){
		if (jQuery(this).scrollTop() > 400) {
			$("#menu").addClass("cor-menu");
		}else{
			$("#menu").removeClass("cor-menu");
		}
	});
});