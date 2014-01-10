<?php

class CharacterController extends BaseController {
  public function __construct() {
    $this->beforeFilter('auth');
  }
  
  public function index() {
    $characters = Auth::user()->characters;
    return View::make('characters.index')->with('characters', $characters);
  }
  
  public function create() {
    return View::make('characters.create');
  }
  
  public function store() {
    if(Input::file('character') === NULL) {
      die('No char');
    }
    
    $file = Input::file('character');
    $name = substr($file->getClientOriginalName(), -$file->getClientOriginalExtension());
    $validator = Validator::make([
      'name' => $name,
      'ext' => $file->getClientOriginalExtension()
    ], [
      'name' => ['required', 'unique:characters,original,NULL,id,user_id,' . Auth::user()->id],
      'ext' => ['in:dsparty']
    ]);
    
    if($validator->passes()) {
      echo $file->getRealPath() . '<br>';
      echo $file->getClientOriginalName() . '<br>';
      echo $file->getClientOriginalExtension() . '<br>';
      echo $file->getSize() . '<br>';
      
      $character = new Character;
      $character->user()->associate(Auth::user());
      $character->filename = str_random(64);
      $character->original = $name;
      $character->save();
      
      $file->move(storage_path() . '\uploads', $character->filename);
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