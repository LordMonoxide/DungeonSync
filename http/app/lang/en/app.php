<?php

return [
  'title' => 'Dungeon Sync',
  'email' => 'Email',
  'password' => 'Password',
  'password_confirmation' => 'Confirm',
  'rememberme' => 'Remember Me',
  'register' => 'Register',
  'login' => 'Log In',
  'logout' => 'Log Out',
  'newaccount' => 'New Account',
  'orregister' => 'or ' . HTML::linkRoute('home.register', 'register'),
  'orlogin' => 'or ' . HTML::linkRoute('home.login', 'login')
];