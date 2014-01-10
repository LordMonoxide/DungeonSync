@extends('layout')

@section('content')
  {{ Form::open(['route' => 'auth.login', 'method' => 'PUT', 'class' => 'pure-form pure-form-stacked']) }}
  {{ Form::label('email', Lang::get('app.email'), ['class' => 'required']) }}
  {{ Form::email('email', null, ['required']) }}
  {{ Form::label('password', Lang::get('app.password'), ['class' => 'required']) }}
  {{ Form::password('password', ['required']) }}
  <label for="remember" class="pure-checkbox">
      <input id="remember" type="checkbox">@lang('app.rememberme')
  </label>
  {{ Form::submit(Lang::get('app.login')) }}
  <span>@lang('app.orregister')</span>
  {{ Form::close() }}
@stop