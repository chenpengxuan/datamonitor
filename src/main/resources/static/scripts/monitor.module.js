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

    angular.module('BlurAdmin.pages.monitor', ['bw.paging'])
        .config(routeConfig);


    /** @ngInject */
    function routeConfig($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('monitor', {
                url: '/monitor',
                template : '<ui-view></ui-view>',
                abstract: true,
                title: '监控管理',
                sidebarMeta: {
                    icon: 'ion-grid',
                    order: 300,
                },
            })
            .state('monitor.monitor-list', {
                url: '/monitor-list',
                title: '监控列表',
                templateUrl: 'monitor-list.html',
                controller: 'listCtrl',
                sidebarMeta: {
                    icon: 'ion-grid',
                    order: 300,
                }
            })
            .state('monitor.monitor-add',
                {
                url: '/monitor-add/:id',
                templateUrl: 'monitor-add.html',
                controller:'monitorAddCtrl',
                title: '创建/修改监控'
                // hide: true
            })
        ;

        $urlRouterProvider.when('/monitor','/monitor/monitor-list');
    }

})();
