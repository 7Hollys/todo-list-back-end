sudo docker rm -f todo
sudo rm -rf  /mys3bucket1/jar/todoList-0.0.1-SNAPSHOT.jar
sudo rm -rf  /mys3bucket1/jar/code-deploy-todo-list-back-end

sudo unzip -o /mys3bucket1/jar/todo.zip -d /mys3bucket1/jar
sudo \cp /mys3bucket1/jar/code-deploy-todo-list-back-end/todoList-0.0.1-SNAPSHOT.jar /home/ec2-user/todo/
sudo docker run --restart always -v /home/ec2-user/todo/todoList-0.0.1-SNAPSHOT.jar:/root/todoList-0.0.1-SNAPSHOT.jar -d --name todo -p 80:8080 openjdk:8 java -jar /root/todoList-0.0.1-SNAPSHOT.jar