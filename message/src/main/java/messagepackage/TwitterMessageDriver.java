package messagepackage;


import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TwitterMessageDriver {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: TwitterMessageDriver <input path> <output path>");
            System.exit(-1);
        }
        Job job = new Job();
        job.setJarByClass(TwitterMessageDriver.class);
        job.setJobName("Finding and counting all message from each user");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(TwitterMessageMapper.class);
        job.setReducerClass(TwitterMessageReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        System.out.println("Done!");
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}