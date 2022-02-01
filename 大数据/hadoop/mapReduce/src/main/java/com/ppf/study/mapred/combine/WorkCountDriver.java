package com.ppf.study.mapred.wordcount.v1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.net.URI;

/**
 * 写驱动
 */
public class WorkCountDriver extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        //String hdfsUrl = "hdfs://nameservice1:8020";
        String hdfsUrl = "/Users/panpengfei/Desktop";

        Job job = Job.getInstance(this.getConf(), "ppf_word_count_v1");

        job.setJarByClass(WorkCountDriver.class);

        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job,new Path(hdfsUrl + "/test/workcount/v1/input"));

        job.setMapperClass(WordCountMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        job.setReducerClass(WordCountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        job.setOutputFormatClass(TextOutputFormat.class);

        Path outputPath = new Path(hdfsUrl + "/test/workcount/v1/output");
        TextOutputFormat.setOutputPath(job,outputPath);

        FileSystem fs = FileSystem.get(new URI(hdfsUrl), new Configuration());
        if (fs.exists(outputPath)) {
            fs.delete(outputPath,true);
        }

        boolean flag = job.waitForCompletion(true);

        return flag ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        Configuration config = new Configuration();
        int run = ToolRunner.run(config, new WorkCountDriver(), args);
        System.exit(run);
    }
}
