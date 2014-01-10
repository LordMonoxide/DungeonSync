<?php namespace api;

use Controller;
use Response;

class LangController extends Controller {
  public function app() {
    return Response::json(include app_path() . '/lang/en/app.php', 200);
  }
  
  public function characters() {
    return Response::json(include app_path() . '/lang/en/characters.php', 200);
  }
}