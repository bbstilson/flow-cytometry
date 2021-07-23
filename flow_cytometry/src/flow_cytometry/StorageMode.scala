package flow_cytometry

enum StorageMode:
  case C, L, U

object StorageMode:

  def parse(str: String): StorageMode = str match
    case "C" => StorageMode.C
    case "L" => StorageMode.L
    case "U" => StorageMode.U
    case _   => throw new Exception("unknown $MODE")
