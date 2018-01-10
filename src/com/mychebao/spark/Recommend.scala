/**
  * spark ASL推荐算法模型
  *
  * Created by zhangdanji on 2017/7/14.
  */
package com.mychebao.spark

import org.apache.spark.mllib.recommendation.{MatrixFactorizationModel, Rating}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD


class Recommend {

}

object Recommend{
  def recommend(model : MatrixFactorizationModel,movieTitle : Map[Int,String]) = {
    var choose = ""
    while(choose != "3"){
      print("请选择要推荐类型 1.针对用户推荐电影 2.针对电影推荐给感兴趣的用户 3.李恺 :")
      choose = readLine()
      if(choose == "1"){
        print("请输入用户id:")
        val inputUserID = readLine()
        RecommendMovies(model,movieTitle,inputUserID.toInt)
      }else if(choose == "2"){
        print("请输入电影id:")
        val inputMovieID = readLine()
        RecommendUsers(model,movieTitle,inputMovieID.toInt)
      }
    }
  }

  def prepareData() : (RDD[Rating],Map[Int,String]) = {
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
    return (ratingsRDD,movieTitle)
  }

  def RecommendMovies(model : MatrixFactorizationModel,movieTitle : Map[Int,String],inputUserID : Int) = {
    val RecommendMovie = model.recommendProducts(inputUserID,10)
    var i = 1
    println("针对用户id" + inputUserID + "推荐下列电影:")
    RecommendMovie.foreach{
      r => println(i.toString + "." + movieTitle(r.product) + "评分:" + r.rating.toString)
        i += 1
    }
  }

  def RecommendUsers(model : MatrixFactorizationModel,movieTitle : Map[Int,String],inputMovieID : Int) = {
    val RecommendUser = model.recommendUsers(inputMovieID,10)
    var i = 1
    println("针对电影id" + inputMovieID + "电影名:" + movieTitle(inputMovieID.toInt) + "推荐下列用户id:")
    RecommendUser.foreach{
      r => println(i.toString + "用户id:" + r.user + " 评分:" + r.rating)
        i += 1
    }
  }

  def SetLogger = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("com").setLevel(Level.OFF)
    System.setProperty("spark.ui.showConsoleProgress","false")
    Logger.getRootLogger().setLevel(Level.OFF)
  }
}
