#!/bin/bash

rm -rf  /home/ubuntu/todo/code-deploy-todo-list-back-end
cp /mys3bucket/todo.zip /home/ubuntu/todo/
unzip -o /home/ubuntu/todo/todo.zip -d /home/ubuntu/todo/
mv /home/ubuntu/todo/code-deploy-todo-list-back-end/todoList-0.0.1-SNAPSHOT.jar /home/ubuntu/todo/
docker service update --force 7hollys_todo