### environment
- Spring Boot
- MybatisPlus
- JSoup
- MySQL
- Redis


### run
simply type the following url in a browser

```
localhost:8080/image/download
```

There are two parameters named `page` and `perPage` in the request url above，whose default values are `1` and `30`，you can also spercify the values by altering the url above like this

```
localhost:8080/image/download?apge=3&perPage=30
```

It should be noted that the value of perPage only ranges from zero to thirty，I haven't come up with a way to fetch a JSon body with tons s photos by requesting once.
