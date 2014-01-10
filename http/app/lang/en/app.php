<?php

return [
  'title' => 'Dungeon Sync',
  'email' => 'Email',
  'password' => 'Password',
  'password_confirmation' => 'Confirm Password',
  'rememberme' => 'Remember Me',
  'register' => 'Register',
  'login' => 'Log in',
  'orregister' => 'or ' . HTML::linkRoute('home.register', 'register'),
  'orlogin' => 'or ' . HTML::linkRoute('home.login', 'login')
];