#!/bin/bash

rm -rf  /mys3bucket/code-deploy-todo-list-back-end
unzip -o /mys3bucket/todo.zip -d /mys3bucket
cp /mys3bucket/code-deploy-todo-list-back-end/todoList-0.0.1-SNAPSHOT.jar /home/ubuntu/todo/
docker service update --force 7hollys_todo