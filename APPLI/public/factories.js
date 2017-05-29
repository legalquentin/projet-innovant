'use strict';

var app = angular.module('myApp');

app.factory('DB_Service_get', function($http, $q) {
  var dataInfo = "";
  function fetchData(param) {
    var deffered = $q.defer();
    if (_isDataLoaded()) {
      deffered.resolve( _getData() );
    } else {
      $http.get('http://localhost:8080/'+param)
      .success(function(response) {
        deffered.resolve(response);
        var data = deffered.dataInfo;
      });
      return deffered.promise;
    }
  }
  function _isDataLoaded() {
    return (angular.isUndefined(dataInfo))? true : false;
  }
  function _getData() {
    return dataInfo;
  }
  function setData(newData) {
    dataInfo = newData;
  }
  return {fetchData : fetchData, setData : setData};
});

app.factory('DB_Service_post', function($http, $q) {
  var dataInfo = "";
  function sendData(param) {
    var deffered = $q.defer();
    if (_isDataLoaded()) {
      deffered.resolve( _getData() );
    } else {
      $http.post('http://localhost:8080/'+param.path, param.payload)
      .success(function(response) {
        deffered.resolve(response);
        var data = deffered.dataInfo;
      });
      return deffered.promise;
    }
  }
  function _isDataLoaded() {
    return (angular.isUndefined(dataInfo))? true : false;
  }
  function _getData() {
    return dataInfo;
  }
  function setData(newData) {
    dataInfo = newData;
  }
  return {sendData : sendData, setData : setData};
});
