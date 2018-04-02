# spark multi project

Desired state: executing the following commands (on the root project should work)
```
sbt console

# pasting code below
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().master("local[*]").enableHiveSupport.getOrCreate
spark.sql("CREATE database foo")
```
However, this fails due to messed up dependencies when executing:
```
spark.sql("CREATE database foo")
```
the error is:
```
java.lang.NoClassDefFoundError: Could not initialize class org.apache.derby.jdbc.EmbeddedDriver

```

However, when running the same code in the sub-project `common` - everything works just fine.
```
sbt
project common
console

# pasting code below
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().master("local[*]").enableHiveSupport.getOrCreate
spark.sql("CREATE database foo")
```

## questions
- How can I fix `sbt console` to directly load the right dependencies?
- How can I load the console directly from the sub project? `sbt common/console` does not seem to fix the issue.