#!/bin/bash

docker rm -f todo
rm -rf  /mys3bucket/todoList-0.0.1-SNAPSHOT.jar
rm -rf  /mys3bucket/code-deploy-todo-list-back-end
unzip -o /mys3bucket/todo.zip -d /mys3bucket
cp /mys3bucket/code-deploy-todo-list-back-end/todoList-0.0.1-SNAPSHOT.jar /home/ubuntu/todo/
docker run --restart always -v /home/ubuntu/todo/todoList-0.0.1-SNAPSHOT.jar:/root/todoList-0.0.1-SNAPSHOT.jar -d --name todo -p 80:8080 openjdk:8 java -jar /root/todoList-0.0.1-SNAPSHOT.jar
