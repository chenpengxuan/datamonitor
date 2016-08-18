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

  angular.module('BlurAdmin.pages.appMenu')
      .controller('funcSubmitCtrl', funcSubmitCtrl);

  /** @ngInject */
  function funcSubmitCtrl($scope,$stateParams,$state,$http,$filter) {

    $scope.functionId = $state.current.param;
    $scope.functionVo = {
      httpMethod: 'POST',
      contentType: 'application/json',
      functionParams: []
    };

    if($scope.functionId != -1){ // -1 独立页面没有参数
      $http({
        url: "/function/get",
        method: 'GET',
        params: {id: $scope.functionId}
      }).success(function (data) {
        if (data.success) {
          $scope.functionVo = data.content;
        }
        $scope.functionVo.requestStr = "";
        $scope.functionVo.responseStr = "";
      });
    }


    $scope.submit = function(){

      /*
      $scope.functionVo.functionParam = $scope.functionVo.jsonBody;
      var valid = false;
      if($scope.functionVo.functionParams.length > 0 ){
        $.each($scope.functionVo.functionParams,function(i,item){
          if(item.value != ''){
            valid = true;
          }
        });
      }
      if(!valid && $scope.functionVo.functionParam != ''){//不能过
        valid = true;
      }
      if(!valid){
        layer.alert("",{closeBtn: 0},function (index) {
          layer.close(index)
        });
        return
      }
      */
      $.ajax({
        type: "POST",
        url: "/function/submit",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify($scope.functionVo),
        success: function (data) {
          try {
            $scope.functionVo.requestStr = JSON.parse(data.request);
            $scope.functionVo.responseStr = JSON.parse(data.response);
          } catch (e) {
            if(console){
              console.log(e);
            }
            $scope.functionVo.requestStr = data.request;
            $scope.functionVo.responseStr = data.response;
          }
          format();
          if ($("#response").attr("class").lastIndexOf("panel-open") == -1) {
            $("#response").find("a").click();
          }
          if ($("#request").attr("class").lastIndexOf("panel-open") == -1) {
            $("#request").find("a").click();
          }
          $scope.$apply();//需要手动刷新
        }
      });
    };


    $scope.prettify = function(){
      try {
        $scope.functionVo.jsonBody = JSON.stringify(JSON.parse($scope.functionVo.jsonBody), "", 2);

      } catch (e) {
        layer.alert("json 格式不正确",{closeBtn: 0},function (index) {
          layer.close(index)
        });
      }
      format();
    };

    var format = function () {
      var options = {
        dom: '#requestCanvas',
        isCollapsible:true,
        quoteKeys: true,
        tabSize: 2
      };
      window.jfReq = new JsonFormater(options);
      options.dom = "#responseCanvas";
      window.jfResp = new JsonFormater(options);
      jfReq.doFormat($scope.functionVo.requestStr);
      jfResp.doFormat($scope.functionVo.responseStr);
    };


    $scope.addParam = function() {
      $scope.inserted = {
        paramName:'',
        value:'',
        type: "string",
        array: false
      };
      $scope.functionVo.functionParams.push($scope.inserted);
    };

    $scope.removeParam = function(index) {
      layer.confirm('确定删除吗？', {
            btn: ['确认', '取消'] //按钮
          }, function (i) {
            layer.close(i);
            $scope.functionVo.functionParams.splice(index, 1);
            $scope.$apply();//需要手动刷新
          }, function () {
          }
      );
    };

    $scope.paramTypes = [
      {value: "number", text: '数字'},
      {value: "string", text: '字符串'},
      {value: "time", text: '日期'}
    ];

    $scope.arrays = [
      {value: false, text: '否'},
      {value: true, text: '是'}
    ];


    $scope.showParamTypes = function(funcParam) {
      var selected = [];
      if(funcParam.type) {
        selected = $filter('filter')($scope.paramTypes, {value: funcParam.type});
      }
      return selected.length ? selected[0].text : 'Not set';
    };

    $scope.showIsArray = function(funcParam) {
      var selected = $filter('filter')($scope.arrays, {value: funcParam.array});

      return selected.length ? selected[0].text : 'Not set';
    };


  }

})();
