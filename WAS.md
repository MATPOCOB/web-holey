_'Focus on attacks to know how to protect'_

What is security?
- Confidentiality - no stolen data
- Integrity - no corrupt data
- Availability - no DoS

http://www.owasp.org - security wiki/community
https://www.exploit-db.com/ - attack wiki/community

# Client Side

## GET and POST
Should accept only relevant parameters
- if GET accepts POST params - allows to hide values, send more data, bypass firewalls
- if POST accepts GET params - easier CSRF

## Input Data
Whole HTTP request including headers, imported files, file names etc.

DEMO: if Apache access logs are publicly visible, then it is possible to inject JS via User-Agent

## Input Data Validation
Client-side - usability
Server-side - validation
Invalid input means:
- insufficient client-side check or
- hacker

## Input manipulation tools
- Burp Suite
- Tamper data FF plugin

## Web Content Injection - typically named XSS
1. USER enters some data (e.g. username/password)
2. CLIENT sends HTTP REQUEST to SERVER
3. Server creates HTTP RESPONSE and sends it to CLIENT
4. CLIENT renders data that comes from SERVER
  - renders HTML
  - loads content, e.g. ```<img> <iframe> <script> <style>```
  - executes Javascript
5. USER enters some data and modifies DOM CLIENT-side
  e.g. $('div').innerHtml = <script>alert(1)</script>

### HTML injection
```html
<input value="user-input" />
<input value=user-input /> <!--HTML5-->
```
### DEMO
http://localhost:8080/search

- When query is entered it is reflected back by server
- Searching for: `test ' " < > <tag>`
- check 'View source' not 'Inspect' to find broken HTML
- `" autofocus onfocus="alert(1)"`
- chrome detects XSS attack vector - what about safari, firefox

#### Wrong Content-Type
http://localhost:8080/rest/partner

What if partner can change his name?
http://localhost:8080/rest/partner?name=abc

#### I use well-known templating framework and it will do the work
Spring Boot App + Freemarker

http://freemarker.org/docs/pgui_config_outputformatsautoesc.html

_By default, templates have "undefined" output format associated, which does no escaping, and in general gives the behavior that you would expect from a template engine that doesn't care about output formats and escaping._

```java
configuration.setOutputFormat(HTMLOutputFormat.INSTANCE);
```

### Javascript injection

? $.getJSON

x-content-type-options: nosniff

### URL manipulation

## Cookies
## Sessions
## Request Forgery
## UI redress attacks

## Server Side

## Business logic flaws
## Google hacking
## File handling
## SQL injection

http://localhost:8080/sql/search
' OR 1=1
