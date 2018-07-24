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

There are threeparameters named `page` , `perPage`  and `level` in the request url above，whose default values are `1` , `30` and `4`，you can also spercify the values by altering the url above like this

```
localhost:8080/image/download?apge=3&perPage=30&level=1
```

It should be noted that the value of perPage only ranges from zero to thirty，I haven't come up with a way to fetch a JSon body with tons of photos by requesting once.