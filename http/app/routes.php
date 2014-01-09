<?php

Route::group(['prefix' => 'api'], function() {
  Route::put('/register', ['as' => 'api.auth.register', 'uses' => 'api\AuthController@register']);
  Route::put('/login',    ['as' => 'api.auth.login',    'uses' => 'api\AuthController@login']);
  Route::put('/logout',   ['as' => 'api.auth.logout',   'uses' => 'api\AuthController@logout']);
});

Route::get('/',         ['as' => 'home.home',     'uses' => 'HomeController@home']);
Route::get('/register', ['as' => 'home.register', 'uses' => 'HomeController@register']);
Route::put('/register', ['as' => 'auth.register', 'uses' => 'AuthController@register']);
Route::get('/login',    ['as' => 'home.login',    'uses' => 'HomeController@login']);
Route::put('/login',    ['as' => 'auth.login',    'uses' => 'AuthController@login']);

Route::resource('characters', 'CharacterController');