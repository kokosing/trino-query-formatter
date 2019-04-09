# presto-query-formatter [![Build Status](https://travis-ci.org/prestodb-rocks/presto-query-formatter.svg?branch=master)](https://travis-ci.org/prestodb-rocks/presto-query-formatter)
SQL query formatter that supports Presto syntax

# Build

```
./mvnw clean install
```

# Usage 

Check this [page](http://prestodb.rocks/projects/presto-query-formatter/) to see more documentation about how to use this tool.

```
echo 'SELECT 1;' | java -jar target/presto-root-0.1-SNAPSHOT-executable.jar 
```
