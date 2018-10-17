package messagestringpackage;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class TwitterMessageStringReducer extends Reducer<IntWritable, Text, Text, Text> {
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        String messages = null;

        for (Text value : values) {
            messages += value + "  ||  ";
        }
        context.write(key, new Text(messages));
    }
}
