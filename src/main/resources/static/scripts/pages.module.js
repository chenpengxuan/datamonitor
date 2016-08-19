/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
  'use strict';

  angular.module('BlurAdmin.pages', [
    'ui.router',

    'BlurAdmin.pages.dashboard',
    'BlurAdmin.pages.monitor',
    'BlurAdmin.pages.logger'
  ])
      .config(routeConfig).run(function($rootScope, $state, $stateParams) {
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
        $rootScope.$on("$stateChangeSuccess",  function(event, toState, toParams, fromState, fromParams) {
          // to be used for back button //won't work when page is reloaded.
          $rootScope.previousState_name = fromState.name;
          $rootScope.previousState_params = fromParams;
        });
        //back button function called from back button's ng-click="back()"
        $rootScope.back = function() {
          $state.go($rootScope.previousState_name,$rootScope.previousState_params);
        };
      });

  /** @ngInject */
  function routeConfig($urlRouterProvider, baSidebarServiceProvider) {
    $urlRouterProvider.otherwise('/dashboard');

    /*baSidebarServiceProvider.addStaticItem({
      title: 'Pages',
      icon: 'ion-document',
      subMenu: [{
        title: 'Sign In',
        fixedHref: 'auth.html',
        blank: true
      }, {
        title: 'Sign Up',
        fixedHref: 'reg.html',
        blank: true
      }, {
        title: 'User Profile',
        stateRef: 'profile'
      }, {
        title: '404 Page',
        fixedHref: '404.html',
        blank: true
      }]
    });
    baSidebarServiceProvider.addStaticItem({
      title: 'Menu Level 1',
      icon: 'ion-ios-more',
      subMenu: [{
        title: 'Menu Level 1.1',
        disabled: true
      }, {
        title: 'Menu Level 1.2',
        subMenu: [{
          title: 'fdsafd',
          //disabled: true,
          url: '/func_cbdd1',
          controller: 'TablesPageCtrl',
          templateUrl: 'user-list.html',
          fixedHref: 'user-list.html',
        }]
      }]
    });*/
  }

})();
