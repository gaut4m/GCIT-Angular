var libraryModule = angular.module('libraryApp', [ 'ngRoute', 'ngCookies','ngAnimate', 'ui.bootstrap'] );

libraryModule.config([ "$routeProvider", function($routeProvider) {
	return $routeProvider.when("/", {
		redirectTo : "/home"
	}).when("/home", {
		templateUrl : "home.html"
	}).when("/viewauthors", {
		templateUrl : "viewauthors.html"
	}).when("/addauthor", {
		templateUrl : "addauthor.html"
	}).when("/viewpublisher", {
		templateUrl : "viewpublisher.html"
	}).when("/addpublisher", {
		templateUrl : "addpublisher.html"
	}).when("/viewbooks", {
		templateUrl : "viewbooks.html"
	}).when("/addbook", {
		templateUrl : "addbook.html"
	}).when("/test", {
		templateUrl : "test.html"
	})
} ]);


libraryModule.controller('authorCtrl', function($scope,$modal, $http, $cookieStore) {
	// get all authors and display initially
	$http.get('http://localhost:8080/lms/getAuthors')
		.success(
			function(data) {
				$scope.authors = data;
				console.log($scope.authors);
			}
		);
	
	$scope.searchAuthor = function() {
		
		 $http.post('http://localhost:8080/lms/searchAuthors',$scope.searchString).
		  success(function(data) {
			  $scope.authors = data;
		    });
		
	};
	
$scope.update=function(a,b){
		
		$scope.author={authorId:a,authorName:b};
		
		var modalInstance = $modal.open({
		      animation: $scope.animationsEnabled,
		      templateUrl: 'myModal.html',
		      resolve: {
		       author: function () {
		          return $scope.author;
		        }
		      }
		    });
		
	};
	$scope.addAuthor = function() {
		var author={authorName:$scope.author.authorName};
		 $http.post('http://localhost:8080/lms/insertAuthor',author).
		  success(function(data) {
			 alert('Author Added');
		    });
		
	};
	
	
	
});

libraryModule.controller('publisherCtrl', function($scope, $http, $cookieStore) {
	
	$http.get('http://localhost:8080/lms/getPublishers')
	.success(
		function(data) {
			$scope.pubs = data;
			
		}
	);
$scope.searchPublisher = function() {
	
	 $http.post('http://localhost:8080/lms/searchPublishers',$scope.searchString).
	  success(function(data) {
		  $scope.pubs = data;
	    });
	
};

$scope.addPublisher = function() {
	var pub=$scope.pub;
	 $http.post('http://localhost:8080/lms/addPublisherr',pub).
	  success(function(data) {
		 alert('publisher Added');
	    });
	
};
	
});

libraryModule.controller('bookCtrl', function($scope, $http, $cookieStore) {
	// get all authors and display initially
	$http.get('http://localhost:8080/lms/author/get')
		.success(
			function(data) {
				$scope.authors = data;
				console.log($scope.authors);
			}
		);
	
//	$scope.addAuthor = function addAuthor() {
//		if($scope.addAuthorFrm.$valid){
//		 $http.post('addAuthor/' +$scope.authorName).
//		  success(function(data) {
//			 alert('Author Added');
//			 $scope.authors = data;	
//			 showAddAuthor();
//		    });
//		}
//	};
});

libraryModule.controller('testCtrl', function($scope, $http, $cookieStore) {
	$scope.testVar = $cookieStore.get('sample');
});
