<?php

use Illuminate\Database\Migrations\Migration;

class CreateTableCharacters extends Migration {
  public function up() {
    Schema::create('characters', function($table) {
      $table->increments('id');
      $table->integer('user_id')->unsigned();
      $table->string('filename', 256);
      $table->string('original', 256);
      $table->timestamps();
    });
  }
  
  public function down() {
    Schema::drop('characters');
  }
}