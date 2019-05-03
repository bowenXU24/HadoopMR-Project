import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class NGramsNew {

  public static class TokenizerMapper
       extends Mapper<Object, Text, Text, IntWritable>{

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    private int n;

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

      //passing length of gram to mapper
      Configuration conf = context.getConfiguration();
      n = Integer.parseInt(conf.get("gramLength"));

      StringTokenizer itr = new StringTokenizer(value.toString());

      while (itr.hasMoreTokens()) {
          String cur=itr.nextToken();
          StringBuilder sb=new StringBuilder();
          int i;
          for(i=0;i<n;i++){
              sb.append(cur.charAt(i));
          }
          word.set(sb.toString());
          context.write(word, one);
          while(i<cur.length()){
              sb.deleteCharAt(0);
              sb.append(cur.charAt(i));
              word.set(sb.toString());
              context.write(word, one);
              i++;
          }
      }
    }
  }

  public static class IntSumReducer
       extends Reducer<Text,IntWritable,Text,IntWritable> {
    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values,
                       Context context
                       ) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
      result.set(sum);
      context.write(key, result);
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    //set input n value
    conf.set("gramLength", args[2]);

    Job job = Job.getInstance(conf, "n-gram");

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    
    job.setJarByClass(NGramsNew.class);
    
    //job.waitForCompletion(true);

    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    
    
    System.exit(job.waitForCompletion(true) ? 0 : 1);
    //Though MapReduce program is parallel processing. Mapper, Combiner and Reducer class has sequence flow. 
    //Have to wait for completing each flow depends on other class so need wait for completion(true)
    //But It must to set input and output path before starting Mapper, 
  }
}