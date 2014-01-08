<?php

Route::group(['prefix' => 'api'], function() {
  Route::put('/register', ['as' => 'api.register', 'uses' => 'api\AuthController@register']);
  Route::put('/login',    ['as' => 'api.login',    'uses' => 'api\AuthController@login']);
  Route::put('/logout',   ['as' => 'api.logout',   'uses' => 'api\AuthController@logout']);
});