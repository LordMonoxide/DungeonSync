@extends('layout')

@section('content')
  {{ Form::open(['route' => 'auth.register', 'method' => 'PUT', 'class' => 'pure-form pure-form-stacked']) }}
  {{ Form::label('email', Lang::get('app.email'), ['class' => 'required']) }}
  {{ Form::email('email', null, ['required']) }}
  {{ Form::label('password', Lang::get('app.password'), ['class' => 'required']) }}
  {{ Form::password('password', ['required']) }}
  {{ Form::label('password_confirmation', Lang::get('app.password_confirmation'), ['class' => 'required']) }}
  {{ Form::password('password_confirmation', ['required']) }}
  <label for="remember" class="pure-checkbox">
      <input name="remember" type="checkbox">@lang('app.rememberme')
  </label>
  {{ Form::submit(Lang::get('app.register')) }}
  <span>@lang('app.orlogin')</span>
  {{ Form::close() }}
@stop