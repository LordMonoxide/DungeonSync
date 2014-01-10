<?php

class AuthController extends BaseController {
  public function register() {
    $request  = Request::create(URL::route('api.auth.register'), 'PUT', Input::all());
    $response = Route::dispatch($request);
    
    if($response->getStatusCode() !== 201) {
      return $response->getContent();
    }
    
    return Redirect::route('home.home');
  }
  
  public function login() {
    $request  = Request::create(URL::route('api.auth.login'), 'PUT', Input::all());
    $response = Route::dispatch($request);
    
    if($response->getStatusCode() !== 200) {
      return $response->getContent();
    }
    
    return Redirect::intended(URL::route('characters.index'));
  }
  
  public function logout() {
    $request  = Request::create(URL::route('api.auth.logout'), 'PUT', Input::all());
    $response = Route::dispatch($request);
    
    if($response->getStatusCode() !== 200) {
      return $response->getContent();
    }
    
    return Redirect::route('home.home');
  }
}