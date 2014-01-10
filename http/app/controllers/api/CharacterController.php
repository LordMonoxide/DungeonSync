<?php namespace api;

use Auth;
use Controller;
use Response;

class CharacterController extends Controller {
  public function __construct() {
    $this->beforeFilter('auth.401');
  }
  
  public function all() {
    return Response::json(Auth::user()->characters, 200);
  }
  
  public function download($character) {
    return Response::download(storage_path() . '/uploads/' . $character->filename, $character->original, ['Content-Type' => 'application/octet-stream']);
  }
}