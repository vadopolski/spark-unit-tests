import org.apache.spark.sql.SparkSession
import org.scalatest.flatspec.AnyFlatSpec
import test.MethodsForTest.{processTaxiData, readCSV, readParquet}


class OtusMethodsForTestTest extends AnyFlatSpec {
  implicit val spark = SparkSession.builder()
    .config("spark.master", "local")
    .appName("Test №1 for Big Data Application")
    .getOrCreate()

  it should "uccessfully calculate the distribution by valid taxi data" in {
    val taxiZonesDF = readCSV("src/main/resources/data/taxi_zones.csv")
    val taxiDF = readParquet("src/main/resources/data/yellow_taxi_jan_25_2018")

    val actualDistribution = processTaxiData(taxiZonesDF, taxiDF)
      .collectAsList()
      .get(0)

    assert(actualDistribution.get(0) == "Manhattan")
    assert(actualDistribution.get(1) == 304266)
    assert(actualDistribution.get(2) == 0.0)
    assert(actualDistribution.get(3) == 2.23)
    assert(actualDistribution.get(4) == 66.0)
  }
}