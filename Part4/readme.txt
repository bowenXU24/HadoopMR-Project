1. How many hits were made to the website item “/assets/img/home-logo.png”?

CountWebsite.java

split the Text value with " " into a String[] a;
a[6] is the dir address part after GET/POST.
interate through everyline checking whether dir address matches the target “/assets/img/home-logo.png”
output the key, value pair for every match

Command Line:

cd ~/hadoop
mkdir CountWebsite_classes
cd CountWebsite_classes
nano CountWebsite.java
mkdir countwebsite_input
cd countwebsite_input

#uplaod access_log file to VM and HDFS

scp -i key_student access_log student@138.197.97.219:~/hadoop/CountWebsite_classes/countwebsite_input/
cd -
bin/hdfs dfs -mkdir /countwebsite_input
% hdfs dfs -mkdir /countwebsite_output
bin/hdfs dfs -put ~/hadoop/CountWebsite_classes/countwebsite_input/ countwebsite_input

#complie, output jar file and run 

javac CountWebsite.java -cp $(/home/student/hadoop/bin/hadoop classpath)
jar cf CountWebsite.jar CountWebsite*.class
~/hadoop/bin/hadoop jar ~/hadoop/CountWebsite_classes/CountWebsite.jar CountWebsite countwebsite_input/ countwebsite_output

# explore the output

bin/hdfs dfs -cat countwebsite_output/*



2. How many hits were made from the IP: 10.153.239.5?

a[0] is the IP address of every single log
interate through everyline checking whether dir address matches the target “10.153.239.5”
output the key, value pair for every match

Command Line:
cd ~/hadoop
cd CountWebsite_classes
nano CountIP.java

javac CountIP.java -cp $(/home/student/hadoop/bin/hadoop classpath)
jar cf CountIP.jar CountIP*.class

~/hadoop/bin/hadoop jar ~/hadoop/CountWebsite_classes/CountIP.jar CountIP countwebsite_input/ countIP_output/

~/hadoop/bin/hdfs dfs -cat countIP_output/*

#Delete duplicate output
#bin/hdfs dfs -rm -r hdfs://CC-AM-29:9000/user/student/countIP_output



3. Most hit path

MostHitPath.java:
split the Text value with " " into a String[] a;
a[6] is the dir address part after GET/POST.
interate through everyline output key, list of value pair

Reduce method output;
        private Map<Text, IntWritable> countDic: an intermediate map with key value pair.

Override the cleanup(Context) method to count the Collection of <key, value>
output the <key, value> pair with max value

Command Line:

cd ~/hadoop
cd CountWebsite_classes
nano MostHitPath.java

javac MostHitPath.java -cp $(/home/student/hadoop/bin/hadoop classpath)
jar cf MostHitPath.jar MostHitPath*.class

~/hadoop/bin/hadoop jar ~/hadoop/CountWebsite_classes/MostHitPath.jar MostHitPath countwebsite_input/ mosthitpath_output/

~/hadoop/bin/hdfs dfs -cat mosthitpath_output/*


4. Most hit IP

MostHitIP.java

interate through everyline output key, list of value pair

Reduce method output;
        private Map<Text, IntWritable> countDic: an intermediate map with key value pair.

Override the cleanup(Context) method to count the Collection of <key, value>
output the <key, value> pair with max value


Command Line:

cd ~/hadoop
cd CountWebsite_classes
nano MostHitIP.java

javac MostHitIP.java -cp $(/home/student/hadoop/bin/hadoop classpath)
jar cf MostHitIP.jar MostHitIP*.class
~/hadoop/bin/hadoop jar ~/hadoop/CountWebsite_classes/MostHitIP.jar MostHitIP countwebsite_input/ mosthitip_output/
~/hadoop/bin/hdfs dfs -cat mosthitip_output/*

