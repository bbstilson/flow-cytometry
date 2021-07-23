package flow_cytometry

case class Metadata(
  numDatum: Long,
  storageMode: StorageMode,
  datatype: Datatype
)

object Metadata:

  def parse(text: Map[String, String]): Metadata = Metadata(
    numDatum = text("$TOT").trim.toLong,
    storageMode = StorageMode.parse(text("$MODE")),
    datatype = Datatype.parse(text("$DATATYPE"))
  )
