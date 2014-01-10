@extends('characters.layout')

@section('content')
  {{ Form::open(['action' => 'characters.store', 'files' => true, 'class' => 'pure-form']) }}
  
  <table class="pure-table">
    <thead>
      <tr>
        <th>@lang('characters.filename')</th>
        <th></th>
      </tr>
    </thead>
    
    <tbody>
      <tr>
        <td>{{ Form::file('character') }}</td>
        <td>{{ Form::submit(Lang::get('characters.upload')) }}</td>
      </tr>
    </tbody>
  </table>
  
  {{ Form::close() }}
@stop