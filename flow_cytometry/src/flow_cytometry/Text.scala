package flow_cytometry

import scodec.bits.BitVector
import scodec.codecs.*
import scodec.*

object Text:
  // The TEXT part of a data set consists primarily of KEYWORD and keyword
  // VALUE combinations. The first byte of the TEXT part defines a “separator”
  // character. The separator character is inserted after each keyword and
  // after each keyword value.
  // For example, for “$TOT/10000/” the keyword is $TOT, the separator is
  // the “/” character, and the value of $TOT is 10000. The separator character
  // may not be the first character in a keyword or in a keyword value.
  // If the separator appears in a keyword or in a keyword value, it must
  // be “quoted” by being repeated. For example, “$SYS/RSX-ll//M/” shows a
  // value of RSX-ll/M for the keyword $SYS. Since null (zero length) keywords
  // or keyword values are not permitted, two consecutive separators can never
  // occur between a value and a keyword. The remainder of the TEXT part
  // consists of repeats of “KEYWORD separator VALUE separator”, etc.
  // Although standard keywords begin with “$”, it is not necessary to “quote”
  // the “$” to include it within either a KEYWORD or KEYWORD VALUE, since null
  // keywords are not allowed (and therefore “$” only has significance in the
  // first position of a keyword name). The standard keywords and the format
  // of their values are described below. Note that some of the keywords are
  // mandatory. In addition, the user will find it advantageous to include
  // sufficient keywords to describe clearly the data set. The TEXT part should
  // not contain “carriage return” or “line feed” characters unless they are
  // within a keyword value or unless used as the separator.
  private val separatorCodec = fixedSizeBytes(1, ascii)

  def decode(bits: BitVector): Attempt[Map[String, String]] =
    separatorCodec.decode(bits).flatMap { r =>
      val separator = r.value.toCharArray.head
      ascii
        .decode(r.remainder)
        .map(_.value)
        .map(parseKVStr(separator))
    }

  private def parseKVStr(sep: Char)(str: String): Map[String, String] = str
    .split(sep)
    .sliding(2, 2)
    .toList
    .map { case xs =>
      xs.head -> xs.last
    }
    .toMap
