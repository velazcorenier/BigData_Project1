package repliespackage;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.User;

import java.io.IOException;



public class TwitterRepliesMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String rawTweet = value.toString();

        try {
            Status status = TwitterObjectFactory.createStatus(rawTweet);
            long originalMessageId = status.getInReplyToStatusId();
            String screenName = status.getInReplyToScreenName();

            if(screenName != null)
                context.write(new Text(screenName + "-" + originalMessageId), new IntWritable(1));

        } catch (TwitterException e) {

        }

    }
}
