/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Test.scala
 * Author:   zhangdanji
 * Date:     2017年07月03日
 * Description: 测试
 */
import java.text.SimpleDateFormat

import com.atomicscala.AtomicTest._
import com.mychebao.spark.Recommend

import reflect.runtime.currentMirror
import scala.util.{Success, Try}
import scala.math.{Pi, sqrt}

/**
  *
  * 测试
  *
  * @author zhangdanji
  *
  */

object Test{
  def main(args: Array[String]): Unit = {

  }
}



trait Reporter[T]{
  def generate(types : T) : String
}

case class Person(message : String)

case class Store(message : String)

case class Vehicle(message : String)

case class Book(title : String)

object BookExtension{
  implicit class BookEx(val book : Book) extends AnyVal{
    def read(s : String) = s"read $s from book ${book.title}"
  }
}

class Problem(val msg : String) extends Exception

case class Name(firstName : String,lastName : String)

trait Flavor

object Coffee extends Enumeration{
  case class _Val() extends Val with Flavor
  type Coffee = _Val
  val Single,Caf,Here,Skim,Choc = _Val()
}

case class Coffee(flavors : Flavor*)

object Syrup extends Enumeration{
  case class _Val() extends Val with Flavor
  type Syrup = _Val
  val Chocolate,HotFudge,Butterscotch,Caramel = _Val()
}

object Level extends Enumeration{
  type Level = Value
  val Overflow,High,Medium,Low,Empty = Value
}

trait ArtPeriod{
  def period:String = "Renaissance"
}

class WIFICamera extends Camera with WIFI{

  override val address: String = "192.168.0.200"

  override def reportState = "working"

  def showImgage : String = "Showing video"

}

class Camera

trait WIFI{
  val address : String

  def reportState : String
}

trait Ability

case class Mobility(mobility : String) extends Ability

case class Vision(vision : String) extends Ability

case class Manipulator(manipulator : String) extends Ability

class Robot{
  var abilities : Vector[Ability] = Vector()

  def +(ability: Ability): Robot ={
    abilities = abilities :+ ability
    this
  }

  override def toString: String = abilities.mkString
}

trait Shape{
  def draw : String
}

class Ellipse extends Shape{
  override def draw = "Ellipse"
}

class Rectangle extends Shape{
  override def draw = "Rectangle"
}

class Square extends Rectangle{
  override def draw: String = "Square"
}

class Drawing(s : Shape*){
  val shapes : Vector[Shape] = s.toVector

  override def toString: String = {
    var result = ""
    for(i <- shapes){
      result += i.draw
    }
    result
  }
}

class Seed

class Tomato extends Seed{
  override def toString: String = "This is Tomato"
}

class Corn extends Seed{
  override def toString: String = "This is Corn"
}

class Zucchini extends Seed{
  override def toString: String = "This is Zucchini"
}

class Garden(seed : Seed*){
  var plants : Vector[Seed] = seed.toVector

  override def toString: String = plants.mkString
}

trait BatteryPower{
  val power : Int
  def monitor(x : Int) : String
}

class Toy

abstract class EnergySource

class Battery extends EnergySource with BatteryPower{
  override val power: Int = 100
  override def monitor(x: Int) = {
    x match {
      case x if(x >= 40) => "green"
      case x if(x >= 20 && x < 40) => "yellow"
      case x if(x < 20) => "red"
    }
  }
}

class NumericAdder(val x : Int) extends Adder(x){
  override def add(y: Int) = x + y
}

abstract class Adder(x : Int){
  def add(y : Int) : Int
}

abstract class Animal{
  val food : String
}

class Duck extends Animal{
  override val food: String = "plants"
}

class Cow extends Animal{
  override val food: String = "grass"
}

object MonthName extends Enumeration{
  val January = Value(1,"January")
  val February = Value("February")
}

class CycleA{
  val wheels = 2
  def ride = "Riding"
}

class BicycleA extends CycleA

class GreatApe{
  val weight = 100.0
  val age = 12

  def vocalize = "Grrr!"
}

class Bonobo extends GreatApe

class Chimpanzee extends GreatApe

class BonoboB extends Bonobo

class WalkActivity

object WalkActivity{
  var log : String = ""
  var MET : Double = 2.3
  def calories(lbs : Int,mins : Int,mph : Double = 3) : Long = math.round((MET * 3.5 * lbs * 0.45)/200.0 * mins)
  def start(name : String) = {println(s" $name started!");log += s"[$name] Activity started\n"}
  def stop(name : String) = {println(s"$name stopped");log += s"[$name] Activity stopped\n"}
}

case class Cycle(num : Int){
  override def toString: String = {
    num match {
      case 1 => "Unicycle"
      case 2 => "Bicycle"
      case 3 => "Tricycle"
      case 4 => "Quandricycle"
      case n if(n > 4) => s"Cycle with $n wheels"
      case n if(n <= 0) => "That's not a cycle!"
    }
  }
}

case class Bicycle(num : Int){
  override def toString: String = s"Bicyacle built for $num"
}

class FancyNumber(num : Int){
  def power(n : Int) = {
    var result : Int = 1
    for(i <- 1 to n){
      result *= num
    }
    result
  }

  def ^(n : Int) = {
    var result : Int = 1
    for(i <- 1 to n){
      result *= num
    }
    result
  }
}

class Exclaim(var s : String){
  val noParens : String = s + "!"
  var count : Int = 0
  def parens() : String = s + "!";count += 1
}

case class Kitten(name : String)

case class Passenger(first : String,last:String)

case class Train(travelers : Vector[Passenger],line : String)

case class Bus(passengers : Vector[Passenger],capacity : String)

case class Plane(travelers : Vector[Passenger],name : String)

case class Activity(date : String,sport : String)

class DisplayVectorWithAnonymous{
  def show() : String = {
    var v = Vector(1,2,3,4,5,6)
    v = v :+ 0
    var s : String = ""
    v.foreach((n : Int) => s += "," + n)
    s
  }
}

case class GardenGnome(val name : String,val num : String){
  def show(): String ={
    s"name is $name,num is $num,get is ${get()}"
  }

  def magic() : String = {
    s"name is $name,num is $num"
  }

  def get() : String = "getFun"
}

class SimpleTime(val hour : Int,val minute : Int = 0){
  def subtract(st : SimpleTime): SimpleTime ={
    val time = hour * 60 + minute - (st.hour * 60 + st.minute)
    if(time <= 0){
      new SimpleTime(0,0)
    }else{
      val h = time / 60
      val min = time % 60
      new SimpleTime(h,min)
    }
  }
  def -(st : SimpleTime) = {
    val time = hour * 60 + minute - (st.hour * 60 + st.minute)
    if(time <= 0){
      new SimpleTime(0,0)
    }else{
      val h = time / 60
      val min = time % 60
      new SimpleTime(h,min)
    }
  }
}

class Info(val name : String,var description : String)

class Dimension(val height : Int,val width : Int)

class ClothesWasher(val model : String,val vapacity : Double){
  def this(model : String){
    this(model,1.0)
  }

  def this(capacity : Double){
    this("name",capacity)
  }
}

class Tea(val decaf : Boolean = false,val name : String = "Earl Grey",val sugar : Boolean = false,val milk : Boolean = false){
  def describe(): String ={
    var describe : String = name
    if(decaf){
      describe += " " + "decaf"
    }
    if(sugar){
      describe += " " + "sugar"
    }
    if(milk){
      describe += " " + "milk"
    }
    describe
  }

  def calories() : Int = {
    var calories = 0
    if(milk){
      calories += 100
    }
    if(sugar){
      calories += 16
    }
    calories
  }
}

class Planet(val name : String,val desc : String,val moon : Int = 0){
  def hasMoon() : Boolean = {
    if(moon > 0) true
    else false
  }

  def f(): Int ={
    0
  }

  def f(num : Int) : Int = 1

  def f(num : Int,num2 : Int) = 2

}

class Cup(var percentFull : Int){
  def add(increment : Int*) : Int = {
    for(i <- increment){
      percentFull += i
    }
    if(percentFull < 0){
      percentFull = 0
    }else if(percentFull > 100){
      percentFull = 100
    }
    percentFull
  }
}

class Family(val args : String*){
  def familySize() : Int = {
    args.length
  }
}

class FlexibleFamily(val mom : String,val dad : String,val childrens : String*) extends Family{
  override def familySize(): Int = {
    var total = 0;
    if(!StringUtils.isEmpty(mom)){
      total += 1
    }
    if(!StringUtils.isEmpty(dad)){
      total += 1
    }
    total += childrens.length
    total
  }
}

object StringUtils{
  def isEmpty(value : String) : Boolean = {
    value == null || "".equals(value) || "".equals(value.trim())
  }
}

class Cup2{

  var percentFull = 0
  val min = 0
  val max = 100

  def setPercentFull(value : Int): Unit ={
    if(value > max){
      percentFull = max
    }else if(value < min){
      percentFull = min
    }else{
      percentFull = value
    }
  }

  def getPercentFull() : Int = {
    percentFull
  }

  def add(increament : Int) : Int = {
    percentFull += increament
    if(percentFull > max){
      percentFull = max
    }else if(percentFull < min){
      percentFull = min
    }
    percentFull
  }
}
