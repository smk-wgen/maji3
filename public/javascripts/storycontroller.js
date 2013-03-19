/**
 * Created with IntelliJ IDEA.
 * User: skunnumkal
 * Date: 3/13/13
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
function Ctrl($scope,$http) {
    $scope.title = "Title";
    $scope.description = "Description";
    $scope.facilities = [{"id":1,"name":"Facility_1"},{"id":2,"name":"Facility_2"}];
    console.log("Down");
    $scope.getFacilities = function(){
        console.log("Making a call");
        //$http.get('/facility').success(onSuccess).error(onError);
        jsRouter.controllers.Application.listFacilities().ajax({
            async: false,
            success: function(data) {
                $scope.facilities = data;
                console.log(data);
                console.log("Got the data from the backend");
            },
            error: function() {
                alert("error:tasks");
            }
        });
    };
    $scope.getFacilities();

    $scope.selectedFacilityId=1;
    $scope.submit = function(){
        var postData =  {'title':$scope.title,'description':$scope.description,'facilityId':$scope.selectedFacilityId,
            'id':'','you':$scope.you,'rating':$scope.rating,'problem':$scope.problemType,
        'treatment':$scope.treatment};
        $http.post('/storypost',postData,{ 'Content-Type':'application/json'}).success(onSuccess).error(onError);
//        jsRouter.controllers.Application.newStory().ajax({
//            type:'POST',
//            dataType : 'json',
//            data: postData,
//            success: onSuccess,
//            error: onError
//        });


           // $http.post(url,).success(onSuccess).error(onError);
    };
    var onSuccess = function(data){console.log(data);
        $scope.facilities = data;
        $http.get('/');
        $location = '/';
    };
    var onError = function(data){console.log(data);};
}

//Ctrl.$inject = ['$scope']