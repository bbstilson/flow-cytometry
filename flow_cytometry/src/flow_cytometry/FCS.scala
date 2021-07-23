package flow_cytometry

import scodec.bits.BitVector

import java.io.{ BufferedInputStream, FileInputStream }
import java.io.File
import java.nio.file.Files

// Implementation follows the paper:
// Data File Standard for Flow Cytometry
// https://onlinelibrary.wiley.com/doi/epdf/10.1002/cyto.990110303
@main
def main: Unit =
  val file = File(getClass.getResource("/001.fcs").toURI).toPath
  val bytes = BitVector(Files.readAllBytes(file))

  val header = Header.decode(bytes).value

  val text = Text
    .decode(bytes.slice(header.textStart, header.textEnd + 8))
    .require

  println(header)
  val data = Data
    .decode(
      text,
      bytes.slice(header.dataStart, header.dataEnd + 8)
    ).require.value

  println(data.size)
