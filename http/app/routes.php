<?php

Route::group(['prefix' => 'api'], function() {
  Route::group(['prefix' => 'auth'], function() {
    Route::put('/register', ['as' => 'api.auth.register', 'uses' => 'api\AuthController@register']);
    Route::put('/login',    ['as' => 'api.auth.login',    'uses' => 'api\AuthController@login']);
    Route::put('/logout',   ['as' => 'api.auth.logout',   'uses' => 'api\AuthController@logout']);
  });
  
  Route::group(['prefix' => 'characters'], function() {
    Route::model('character', 'Character');
    
    Route::get('/',                     ['as' => 'api.characters.all',      'uses' => 'api\CharacterController@all']);
    Route::get('/{character}/download', ['as' => 'api.characters.download', 'uses' => 'api\CharacterController@download']);
  });
});

Route::get('/',         ['as' => 'home.home',     'uses' => 'HomeController@home']);
Route::get('/register', ['as' => 'home.register', 'uses' => 'HomeController@register']);
Route::put('/register', ['as' => 'auth.register', 'uses' => 'AuthController@register']);
Route::get('/login',    ['as' => 'home.login',    'uses' => 'HomeController@login']);
Route::put('/login',    ['as' => 'auth.login',    'uses' => 'AuthController@login']);
Route::get('/logout',   ['as' => 'auth.logout',   'uses' => 'AuthController@logout']);

Route::resource('characters', 'CharacterController');