package messagepackage;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.User;

import java.io.IOException;
import java.util.HashMap;


public class TwitterMessageMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        HashMap<String,String> messagesPerUser = new HashMap<String, String>();

        String rawTweet = value.toString();

        try {
            Status status = TwitterObjectFactory.createStatus(rawTweet);
            String text = status.getText().replaceAll("[^\\w]", " ");
            User user = status.getUser();
            String userName = user.getName();
            context.write(new Text(userName), new IntWritable(1));
            messagesPerUser.put(userName,text);
        } catch (TwitterException e) {

        }

    }
}