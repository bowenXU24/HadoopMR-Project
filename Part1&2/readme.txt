The Dockerfile:

from system image ubuntu:18.04

RUN :
install openjdk-8-jdk
install hadoop 3.2.0
create ssh keys

ADD:
copy configuration file from local directory /config

CMD: to start services


Command Line: 

docker build -t my-hadoop . 

#Running from the directory containing your Dockerfile will create the docker my-hadoop image.

docker run -p 8088:8088 --name my-hadoop-container -d my-hadoop

#The command can be used to create a Docker container from this image. 
#The CMD instruction used in the Dockerfile will run start-hadoop.sh by default when the container is created.


#Interactive shell in the Docker container, and running a sample MapReduce job:
# start interactive shell in running container

docker exec -it my-hadoop-container bash

# once shell has started run hadoop "pi" example job

hadoop jar $HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-examples-3.2.0.jar pi 10 100