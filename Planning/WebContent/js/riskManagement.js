var app = angular.module('app', []);

app.controller('ctrl1', function($scope){
	
	$scope.risks = [
					{description: "computer goes down", probability: 10, impact: 4},
					{description: "computer get virus", probability: 20, impact: 3},
					{description: "computer run out of RAM", probability: 50, impact: 1},
				   ];
	
	//Background config

	var imgs = ['images/office1.jpg',
	            'images/office2.jpg',
	            'images/office3.jpg',
	            'images/office4.jpg',
	            'images/office5.jpg'
	            ];
	  var i = 1; 
	  document.getElementById("bgimg").style.backgroundImage = "url("+imgs[i]+")";
	  setTimeout(changeImage, 10000);
	
	 function changeImage(){
		i++;
		if(i === 4){
			i = 0;
		}
		$("#bgimg").fadeOut(200, function(){
			document.getElementById("bgimg").style.backgroundImage = "url("+imgs[i]+")";
			$("#bgimg").fadeIn(200);
		});
		setTimeout(changeImage, 10000);
	 }
	 
	 var a;
	 $scope.edit = function(index){
		 a = index;
	 }
	 
	 /*$scope.out = function(){
		 $scope.risks[a].description = $('input', this).val();
     	text = $scope.risks[a].description;
		 $('.editText').html( text );
	 }*/
	 
	 $(function(){
		    $('.editText').dblclick( function(){
		    	console.log("1> "+$scope.risks[a].description);
		        var text = $scope.risks[a].description;
		        $(this).html('<input type="text" value="'+text+'">').find('input').focus();
		    }).keypress( function(e){
		        if(e.keyCode == 13){
		        	$scope.risks[a].description = $('input', this).val();
		        	text = $scope.risks[a].description;
		            $(this).html( text );
		            console.log("2> "+$scope.risks[a].description);
		        }
		    });
	 });
	 
	 
	 $scope.test = function(){
		 console.log($scope.risks[1].description);
	 }
 
});

function test(){
	RiskManagementDB.printFutbolistas();
}