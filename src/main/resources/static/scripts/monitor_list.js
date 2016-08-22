/*
 *
 *  (C) Copyright 2016 Ymatou (http://www.ymatou.com/).
 *  All rights reserved.
 *
 */

/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
  'use strict';

  angular.module('BlurAdmin.pages.monitor')
      .controller('listCtrl', listCtrl);

  /** @ngInject */
  function listCtrl($scope, $state,$http,$filter) {


    $scope.page = 1;
    $scope.pageSize = 10;
    $scope.total = 0;
    $scope.obj = {};

    //获取数据源
    $http({
      url: "/dbSource/getAll",
      method: 'GET'
    }).success(function (data) {
      if (data.success) {
        $scope.dbSources = data.content;
      }
    });

    $scope.runStatuss = [
      {value: "RUNNING", text: '运行中'},
      {value: "STOPPED", text: '暂停中'}
    ];
    $scope.showRunStatus = function(val) {
      var selected = [];
      if(val) {
        selected = $filter('filter')($scope.runStatuss, {value: val});
      }
      return selected.length ? selected[0].text : '';
    };


    $scope.pagingAction = function(page,pageSize) {
      var param = {};
      param.page = page;
      param.size = pageSize;

      param.name = $scope.obj.name;
      param.dbSource = $scope.obj.dbSource;
      param.runStatus = $scope.obj.runStatus;
      param.createUser = $scope.obj.createUser;
      param.sort = "m.update_time,desc";

      doPaging($http,"/monitor/list",param,function (data) {
          $scope.total = data.content.totalElements;
          $scope.list = data.content.content;
      });
    };
    $scope.pagingAction($scope.page,$scope.pageSize,$scope.total);

    $scope.search = function(){
      $scope.page = 1;
      $scope.pagingAction($scope.page,$scope.pageSize);
    };

    $scope.create = function(){
      $state.go("monitor.monitor-add");
    };
    

    $scope.delete = function(id){

      layer.confirm('确定删除吗？', {
            btn: ['确认', '取消'] //按钮
          }, function (i) {
            layer.close(i);

            $http({
              url: "/monitor/remove",
              method: 'GET',
              params: {id: id}
            }).success(function (data) {
              if (data.success) {
                layer.alert(data.message,{closeBtn: 0},function (index) {
                  $scope.search();//删除先再次查询
                  layer.close(index)
                });
              }
            });
          }, function () {
          }
      );
    };


    $scope.pause = function(id){

      layer.confirm('确定暂停吗？', {
            btn: ['确认', '取消'] //按钮
          }, function (i) {
            layer.close(i);

            $http({
              url: "/monitor/pause",
              method: 'GET',
              params: {id: id}
            }).success(function (data) {
              if (data.success) {
                layer.alert(data.message,{closeBtn: 0},function (index) {
                  $scope.search();//删除先再次查询
                  layer.close(index)
                });
              }
            });
          }, function () {
          }
      );
    };

    $scope.resume = function(id){

      layer.confirm('确定启动吗？', {
            btn: ['确认', '取消'] //按钮
          }, function (i) {
            layer.close(i);

            $http({
              url: "/monitor/resume",
              method: 'GET',
              params: {id: id}
            }).success(function (data) {
              if (data.success) {
                layer.alert(data.message,{closeBtn: 0},function (index) {
                  $scope.search();//删除先再次查询
                  layer.close(index)
                });
              }
            });
          }, function () {
          }
      );
    };

    $scope.runNow = function(id){

      layer.confirm('确定立即运行吗？', {
            btn: ['确认', '取消'] //按钮
          }, function (i) {
            layer.close(i);

            $http({
              url: "/monitor/runNow",
              method: 'GET',
              params: {id: id}
            }).success(function (data) {
              if (data.success) {
                layer.alert(data.message,{closeBtn: 0},function (index) {
                  layer.close(index)
                });
              }
            });
          }, function () {
          }
      );
    };

  }

})();
