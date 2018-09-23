import datetime

def application(environ, start_response):
    start_response('200 OK', [('Content-Type', 'text/html')])

    time = datetime.datetime.now().isoformat()
    html = "<p>The current datetime is " + time + "</p>"
    return [html.encode('utf8')]
