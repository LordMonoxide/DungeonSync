@extends('characters.layout')

@section('content')
  <table class="pure-table pure-table-striped">
    <thead>
      <tr>
        <th>@lang('characters.filename')</th>
        <th>@lang('characters.created')</th>
        <th>@lang('characters.updated')</th>
        <th>
      </tr>
    </thead>
    
    <tbody>
      @foreach($characters as $character)
        <tr>
          <td>{{{ $character->original }}}</td>
          <td>{{ $character->created_at }}</td>
          <td>{{ $character->updated_at }}</td>
          <td>
            {{ Form::open(['action' => ['api.characters.download', $character->id], 'method' => 'GET', 'class' => 'pure-form']) }}
            {{ Form::submit(Lang::get('characters.download')) }}
            {{ Form::close() }}
          </td>
        </tr>
      @endforeach
    </tbody>
  </table>
@stop