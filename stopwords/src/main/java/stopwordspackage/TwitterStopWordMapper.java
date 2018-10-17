package stopwordspackage;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import java.util.Arrays;

import java.io.IOException;


public class TwitterStopWordMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private static String[] arrayStopWords = {"of", "and", "for", "due", "to",
            "in", "a", "with", "become", "you", "will", "that", "the", "be", "are", "different", "on", "other", "than",
            "then", "out", "each", "which",
            "all", "your", "it", "might", "some", "via", "from"};



    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String rawTweet = value.toString();
        try {
            Status status = TwitterObjectFactory.createStatus(rawTweet);
            String tweet = status.getText().toLowerCase().replaceAll("[^\\w]", " ");
            String[] tweetArray = tweet.split("\\s");
            for (int i = 0; i < tweetArray.length; i++) {
                if (!Arrays.asList(arrayStopWords).contains(tweetArray[i]))
                    context.write(new Text(tweetArray[i]), new IntWritable(1));
            }
        } catch (TwitterException e) {

        }

    }
}