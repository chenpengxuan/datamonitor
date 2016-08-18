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

  angular.module('BlurAdmin.pages.app', ['bw.paging'])
    .config(routeConfig);


  /** @ngInject */
  function routeConfig($stateProvider, $urlRouterProvider) {
    $stateProvider
        .state('app', {
          url: '/app',
          template : '<ui-view></ui-view>',
          abstract: true,
          title: '应用管理',
          sidebarMeta: {
            icon: 'ion-grid',
            order: 300,
          },
        }).state('app.func-list', {
          url: '/func-list',
          templateUrl: 'func-list.html',
          controller: 'funcListCtrl',
          title: '功能列表',
          sidebarMeta: {
            order: 100,
          },
        }).state('app.func-add', {
          url: '/func-add/:id',
          templateUrl: 'func-add.html',
          controller: 'funcAddCtrl',
          title: '创建/修改功能',
          // hide:true
        });
    $urlRouterProvider.when('/app','/app/func-list');
  }

})();
