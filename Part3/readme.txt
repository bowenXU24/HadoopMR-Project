
NGram class:

TokenizerMapper extends Mapper class

For every word, start with its first n character as the first gram. A while loop deletes first character in gram and add the next character in the word to form a now gram. Map outputs every gram and list of its occurance.

Command lines: 

cd ~/hadoop

mkdir ngram

cd ngram

nano NGrams.java

mkdir ngram_input
cd ngram_input

#edit an sample file like helloworld
nano a.txt

cd -

#complie 

javac NGrams.java -cp $(/home/student/hadoop/bin/hadoop classpath)

#output a jar file from class

jar cf NGrams.jar NGrams*.class

#export PATH=$PATH:~/hadoop/bin
#put input file into hdfs

hdfs dfs -mkdir /ngram_input
hdfs dfs -mkdir /ngram_output
hdfs dfs -put ngram_input/ ngram_input

# run jar file with 3 arguments input output n-value
~/hadoop/bin/hadoop jar NGrams.jar NGrams ngram_input/ ngram_output/ 2