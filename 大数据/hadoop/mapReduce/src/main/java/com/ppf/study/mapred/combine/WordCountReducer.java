package com.ppf.study.mapred.wordcount.v1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 把 key (1,1,1,1,1,1) 转进来的值 转成 key 6的形式
 *
 * 就是把 Iterable<LongWritable> 里的值相加
 */
public class WordCountReducer extends Reducer<Text, LongWritable,Text,LongWritable> {
    LongWritable amount = new LongWritable();

    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        amount.set(0);

        for (LongWritable next : values) {
            amount.set(amount.get() + next.get());
        }

        context.write(key,amount);
    }
}
