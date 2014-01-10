<?php

class HomeController extends BaseController {
  public function __construct() {
    $this->beforeFilter('guest');
  }
  
  public function home() {
    return Redirect::route('home.login');
  }
  
  public function register() {
    return View::make('register');
  }
  
  public function login() {
    return View::make('login');
  }
}