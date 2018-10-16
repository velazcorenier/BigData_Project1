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
import java.util.List;

public class TwitterUniqueNamesMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String rawTweet = value.toString();
        ArrayList<User> userList = new ArrayList<User>();

        try {
            Status status = TwitterObjectFactory.createStatus(rawTweet);
            User user = status.getUser();
            userList.add(user);

            if(!userList.contains(user))
                context.write(new Text(user.toString()), new IntWritable(1));

        } catch (TwitterException e) {

        }
    }
}
