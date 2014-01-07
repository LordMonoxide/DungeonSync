<?php

use Illuminate\Database\Migrations\Migration;

class CreateTableUsers extends Migration {
  public function up() {
    Schema::create('users', function($table) {
      $table->increments('id');
      $table->string('email', 254)->unique();
      $table->string('password', 60);
      $table->timestamps();
    });
  }
  
  public function down() {
    Schema::drop('users');
  }
}