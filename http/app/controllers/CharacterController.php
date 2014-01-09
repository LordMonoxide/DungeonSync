<?php

class CharacterController extends BaseController {
  public function __construct() {
    $this->beforeFilter('auth');
  }
  
  public function index() {
    $characters = Auth::user()->characters();
    return View::make('characters.index')->with('characters', $characters);
  }
  
  public function create() {
    return View::make('characters.create');
  }
  
  public function store() {
    $validator = Validator::make(Input::all(), [
      'character' => ['required']
    ]);
    
    if($validator->passes()) {
      $file = Input::file('character');
      echo $file->getRealPath() . '<br>';
      echo $file->getClientOriginalName() . '<br>';
      echo $file->getClientOriginalExtension() . '<br>';
      echo $file->getSize() . '<br>';
      $file->move(storage_path() . '\uploads', str_random(64));
    } else {
      echo $validator->messages();
    }
  }
  
  public function show($id) {
    
  }
  
  public function edit($id) {
    
  }
  
  public function update($id) {
    
  }
  
  public function destroy($id) {
    
  }
}