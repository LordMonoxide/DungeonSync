@extends('layout')

@section('header')
  <div class="pure-menu pure-menu-open pure-menu-horizontal">
    <ul>
      <li>{{ HTML::linkRoute('characters.index', Lang::get('characters.list')) }}</li>
      <li>{{ HTML::linkRoute('characters.create', Lang::get('characters.add')) }}</li>
    </ul>
  </div>
@stop