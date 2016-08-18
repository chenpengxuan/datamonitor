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

  angular.module('BlurAdmin.pages.app')
      .controller('funcListCtrl', funcListCtrl);

  /** @ngInject */
  function funcListCtrl($scope, $state,$http) {


    $scope.page = 1;
    $scope.pageSize = 10;
    $scope.total = 0;
    $scope.func = {};

    //获取应用
    $http({
      url: "/application/getAll",
      method: 'GET'
    }).success(function (data) {
      if (data.success) {
        $scope.applications = data.content;
      }
    });

    $scope.pagingAction = function(page,pageSize) {
      var param = {};
      param.page = page;
      param.size = pageSize;
      //param.name = $scope.user.name;
      //param.roleId = $scope.user.roleId;
      param.name = $scope.func.name;
      param.url = $scope.func.url;
      param.author = $scope.func.author;
      param.applicationId = $scope.func.applicationId;
      param.sort = "f.update_time,desc";

      doPaging($http,"/function/list",param,function (data) {
          $scope.total = data.content.totalElements;
          $scope.funcList = data.content.content;
      });
    };
    $scope.pagingAction($scope.page,$scope.pageSize,$scope.total);

    $scope.search = function(){
      $scope.page = 1;
      $scope.pagingAction($scope.page,$scope.pageSize);
    };

    $scope.createFunc = function(){
      $state.go("app.func-add");
    };

    $scope.delete = function(id){

      layer.confirm('确定删除吗？', {
            btn: ['确认', '取消'] //按钮
          }, function (i) {
            layer.close(i);

            $http({
              url: "/function/delete",
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

  }

})();
