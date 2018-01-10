package com.mychebao.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.recommendation.Rating
import org.apache.spark.rdd.RDD

/**
  * Created by zhangdanji on 2017/7/15.
  */
class AlsEvaluation {

}

object AlsEvaluation{

  def main(args: Array[String]): Unit = {
    println("===数据准备阶段===")
    val (trainData,validationData,testData) = PrepareData()
    trainData.persist()
  }

  def PrepareData() : (RDD[Rating],RDD[Rating],RDD[Rating]) = {
    val sc = new SparkContext(new SparkConf().setAppName("Recommend").setMaster("local[4]"))
    println("开始读取用户评分数据...")
    val rawUserData = sc.textFile("file:/Users/zhangdanji/Desktop/ml-100k/u.data")
    val rawRatings = rawUserData.map(_.split("\t").take(3))
    val ratingsRDD = rawRatings.map{
      case Array(user,movie,rating) => Rating(user.toInt,movie.toInt,rating.toDouble)
    }
    println("共计" + ratingsRDD.count.toString + "条ratings")

    println("开始读取电影数据...")
    val itemRDD = sc.textFile("file:/Users/zhangdanji/Desktop/ml-100k/u.item")
    val movieTitle = itemRDD.map(line => line.split("\\|").take(2)).map(array => (array(0).toInt,array(1))).collect().toMap

    val numRatings = ratingsRDD.count()
    val numUsers = ratingsRDD.map(_.user).distinct().count()
    val numMovies = ratingsRDD.map(_.product).distinct().count()
    println("共计:ratings:" + numRatings + " Users " + numUsers + " Movie " + numMovies)

    println("将数据分为")
    val Array(trainData,validationData,testData) = ratingsRDD.randomSplit(Array(0.8,0.1,0.1))
    println(" trainData:" + trainData.count() + " testData:" + testData.count())
    return (trainData,validationData,testData)
  }
}