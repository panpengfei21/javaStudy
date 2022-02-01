package com.ppf.study.mapred.seriable;

import com.ppf.study.mapred.seriable.entity.Stream;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SerReducer extends Reducer<Text, Stream,Text,Stream> {

    private final Stream value = new Stream();
    @Override
    protected void reduce(Text key, Iterable<Stream> values, Context context) throws IOException, InterruptedException {
        value.setUpStream(0);
        value.setDownStream(0);
        value.setTotalStream(0);

        for (Stream stream : values) {
            value.setUpStream(stream.getUpStream() + value.getUpStream());
            value.setDownStream(stream.getDownStream() + value.getDownStream());
        }

        value.setTotalStream(value.getDownStream() + value.getUpStream());

        context.write(key,value);
    }
}
