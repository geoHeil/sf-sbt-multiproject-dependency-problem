// Copyright (C) 2018
package com.geoheil

object Dummy extends App {

  import org.apache.spark.sql.SparkSession

  val spark = SparkSession.builder().master("local[*]").enableHiveSupport.getOrCreate
  spark.sql("CREATE database foo")

}
