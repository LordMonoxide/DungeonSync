<?php namespace api

use Controller;

class AuthController extends Controller {
  public function __construct() {
    $this->beforeFilter('csrf', ['on' => ['post', 'put', 'delete']]);
  }
  
  public function register() {
    $validator = Validator::make(Input::all(), [
      'email'    => ['required', 'email', 'unique:users,email'],
      'password' => ['required', 'min:8', 'max:256', 'confirmed']
    ]);
    
    if($validator->passes()) {
      $user = new User;
      $user->email    = Input::get('email');
      $user->password = Hash::make(Input::get('password'));
      $user->save();
      
      Auth::login($user);
      
      return Response::json('{}', 201);
    } else {
      return Response::json($validator->messages()->toJson(), 409);
    }
  }
  
  public function login() {
    $validator = Validator::make(Input::all(), [
      'email'    => ['required', 'email', 'exists:users,email'],
      'password' => ['required', 'min:8', 'max:256']
    ]);
    
    if($validator->passes()) {
      if(!Auth::attempt(['email' => Input::get('email'), 'password' => Input::get('password')])) {
        return Response::json('{"password": ["Invalid password"]}', 409);
      }
      
      return Response::json('{}', 200);
    } else {
      return Response::json($validator->messages()->toJson(), 409);
    }
  }
  
  public function logout() {
    Auth::logout();
    return Response::json('{}', 200);
  }
}