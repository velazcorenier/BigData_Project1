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
import java.util.ArrayList;


public class TwitterStopWordMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private static ArrayList<String> stopWords = (ArrayList<String>) Arrays.asList("of", "and", "for", "due", "to",
            "in", "a", "with", "become", "you", "will", "that", "the", "be", "are", "different", "on", "other", "than",
            "then", "out", "each", "which",
            "all", "your", "it", "might", "some", "via", "from");


    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String rawTweet = value.toString();

        try {
            Status status = TwitterObjectFactory.createStatus(rawTweet);
            String tweet = status.getText().toLowerCase();
            String[] tweetArray = tweet.split("\\s");
            for (int i = 0; i < tweetArray.length; i++)
                tweetArray[i].replaceAll("[^\\w]", "");

            for (int i = 0; i < tweetArray.length; i++) {
                if (!tweetArray[i].contains(null) && !stopWords.contains(tweetArray[i]))
                    context.write(new Text(tweetArray[i]), new IntWritable(1));
            }
        } catch (TwitterException e) {

        }

    }
}