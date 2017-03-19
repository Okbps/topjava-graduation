### Test com.task.voting.web.rest (application deployed in application context `/`).
> For windows use `Git Bash`

#### get All Users
`curl -s http://localhost:8080/rest/admin/users --user Admin:admin`

#### get Users 100001
`curl -s http://localhost:8080/rest/admin/users/100001 --user Admin:admin`

#### get All Cafes
`curl -s http://localhost:8080/rest/cafes`

#### get Cafes 100006
`curl -s http://localhost:8080/rest/cafes/100006`

#### get Reports date
`curl -s http://localhost:8080/rest/votes/reports?date=2017-02-08`

#### get Cafes not found
`curl -s -v http://localhost:8080/rest/cafes/1`

#### delete Votes
`curl -s -X DELETE http://localhost:8080/rest/votes/100000/2017-02-08 --user Admin:admin`

#### create Votes
`curl -s -X POST -d '{"id":{"user":{"id":100000,"name":"Admin","password":"admin","roles":["ROLE_USER","ROLE_ADMIN"]},"dateTime":"2017-03-08T12:00:00"},"cafe":{"id":100006,"name":"Sbarro"}}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/votes/ --user Admin:admin`

#### update Cafemenus
`curl -s -X PUT -d '{"id":100008,"cafe":{"id":100005,"name":"Papa Johns"},"dateTime":"2017-02-08T10:00:00","dish":"Updated dish","price":7.5}' -H 'Content-Type: application/json' http://localhost:8080/rest/cafemenus/100008 --user Admin:admin`