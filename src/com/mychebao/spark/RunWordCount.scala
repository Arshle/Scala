package com.mychebao.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by zhangdanji on 2017/7/13.
  */
class RunWordCount{}

object RunWordCount{
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    println("---wordCount start---")
    val sparkConf = new SparkConf().setAppName("wordCount").setMaster("local[4]").setJars(List("/Volumes/Arshle/works/spark/IdeaProjects/scala/out/artifacts/scala_jar/scala.jar"))
    val sc = new SparkContext(sparkConf)
    val textFile = sc.textFile("file:/home/spark/data/test.txt")
    println("---开始创建RDD---")
    val countsRDD = textFile.flatMap(line => line.split(" ")).map(word => (word,1)).reduceByKey(_ + _)
    println("---开始保存本地文件---")
    countsRDD.saveAsTextFile("/home/spark/data/result")
    println("---success---")
  }
}
