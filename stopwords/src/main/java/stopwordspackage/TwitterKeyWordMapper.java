package stopwordspackage;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TwitterKeyWordMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String rawTweet = value.toString();
        String[] stopWords = { "of", "and", "for", "due", "to", "in", "a", "with", "become", "you", "will", "that",
                                "the", "be", "are", "different", "on", "other", "than", "then" , "out", "each", "which",
                                "all", "your", "it", "might", "some", "via", "from"};

        Set<String> container = new HashSet<String>();
        for(int i=0; i<stopWords.length; i++)
            container.add(stopWords[i]);


        try {
            Status status = TwitterObjectFactory.createStatus(rawTweet);
            String tweet = status.getText().toLowerCase();
            if(!(container.contains(tweet)))
                context.write(new Text(tweet), new IntWritable(1));
        } catch (TwitterException e) {

        }

    }
}