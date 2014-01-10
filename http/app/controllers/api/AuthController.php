<?php namespace api;

use Auth;
use Controller;
use Hash;
use Input;
use Response;
use Validator;

use User;

class AuthController extends Controller {
  public function __construct() {
    //$this->beforeFilter('csrf', ['on' => ['post', 'put', 'delete']]);
  }
  
  public function register() {
    $validator = Validator::make(Input::all(), [
      'email'    => ['required', 'email', 'unique:users,email'],
      'password' => ['required', 'min:8', 'max:256', 'confirmed'],
      'remember' => ['in:yes,no,on,off,1,0']
    ]);
    
    if($validator->passes()) {
      $user = new User;
      $user->email    = Input::get('email');
      $user->password = Hash::make(Input::get('password'));
      $user->save();
      
      $remember = Input::get('remember', false);
      if($remember === 'yes' || $remember === 'on')  { $remember = true; }
      if($remember === 'no'  || $remember === 'off') { $remember = false; }
      
      Auth::login($user, $remember);
      
      return Response::json('{}', 201);
    } else {
      return Response::json($validator->messages(), 409);
    }
  }
  
  public function login() {
    $validator = Validator::make(Input::all(), [
      'email'    => ['required', 'email', 'exists:users,email'],
      'password' => ['required', 'min:8', 'max:256'],
      'remember' => ['in:yes,no,on,off,1,0']
    ]);
    
    if($validator->passes()) {
      $remember = Input::get('remember', false);
      if($remember === 'yes' || $remember === 'on')  { $remember = true; }
      if($remember === 'no'  || $remember === 'off') { $remember = false; }
      
      if(!Auth::attempt(['email' => Input::get('email'), 'password' => Input::get('password')], $remember)) {
        return Response::json('{"password": ["Invalid password"]}', 409);
      }
      
      return Response::json('{}', 200);
    } else {
      return Response::json($validator->messages(), 409);
    }
  }
  
  public function logout() {
    Auth::logout();
    return Response::json('{}', 200);
  }
}