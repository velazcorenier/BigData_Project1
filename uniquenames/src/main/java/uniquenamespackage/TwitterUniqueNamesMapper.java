package uniquenamespackage;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class TwitterUniqueNamesMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String rawTweet = value.toString();
        ArrayList<String> uniqueScreenNameList = new ArrayList<String>();

        try {
            Status status = TwitterObjectFactory.createStatus(rawTweet);
            User user = status.getUser();
            if(!uniqueScreenNameList.contains(user.getScreenName())) {
                uniqueScreenNameList.add(user.getScreenName());
                context.write(new Text(user.getScreenName()), new IntWritable());
            }
        } catch (TwitterException e) {

        }
    }
}
