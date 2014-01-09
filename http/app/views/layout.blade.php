<!doctype html>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>@lang('app.title')</title>
  {{ HTML::style('assets/css/main.css') }}
</head>

<body>
  <header>
    @yield('header')
  </header>
  
	<section>
    @yield('content')
  </section>
</body>
</html>