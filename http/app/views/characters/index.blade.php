@extends('characters.layout')

@section('content')
  <table class="pure-table pure-table-striped">
    <thead>
      <tr>
        <th>@lang('characters.filename')</th>
        <th>@lang('characters.created')</th>
        <th>@lang('characters.updated')</th>
      </tr>
    </thead>
    
    <tbody>
      @foreach($characters as $character)
        <tr>
          <td>{{{ $character->original }}}</td>
          <td>{{ $character->createdAt }}</td>
          <td>{{ $character->updatedAt }}</td>
        </tr>
      @endforeach
    </tbody>
  </table>
@stop