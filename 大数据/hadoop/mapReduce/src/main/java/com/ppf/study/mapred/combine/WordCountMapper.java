package com.ppf.study.mapred.wordcount.v1;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;

import java.io.IOException;

/**
 * 把一行的字符，用空格切分成一个个的work
 * 再按 (word,1) (word,1) (word,1)的形式写出去
 */
public class WordCountMapper extends Mapper<LongWritable,Text,Text,LongWritable> {

    /**
     * 为什么放这里
     *
     * 为了尽可能少创建类，一个mapper用一个Text,重复利用。
     */
    Text word = new Text();
    LongWritable amount = new LongWritable(1L);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] split = value.toString().split(" ");

        for (String s : split) {
            word.set(s);
            context.write(word,amount);
        }
    }
}
