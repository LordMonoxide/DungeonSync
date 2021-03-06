<?php

use Illuminate\Auth\UserInterface;
use Illuminate\Auth\Reminders\RemindableInterface;

class User extends Eloquent implements UserInterface, RemindableInterface {
  protected $hidden = array('password');
  
  public function getAuthIdentifier() {
    return $this->getKey();
  }
  
  public function getAuthPassword() {
    return $this->password;
  }
  
  public function getReminderEmail() {
    return $this->email;
  }
  
  public function characters() {
    return $this->hasMany('Character');
  }
}