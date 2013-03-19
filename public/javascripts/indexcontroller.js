/**
 * Created with IntelliJ IDEA.
 * User: skunnumkal
 * Date: 3/16/13
 * Time: 10:08 PM
 * To change this template use File | Settings | File Templates.
 */

function IndexCtrl($scope,$http) {
    $scope.getStories = function(){
        console.log("Making call");
        $http.get('/story').success(onSuccess).error(onError);
    };
    var onSuccess = function(data){
        $scope.stories = data;
    }
    var onError = function(data){
        console.log(data);
        alert("Something went wrong");
    }
    $scope.getStories();
}
