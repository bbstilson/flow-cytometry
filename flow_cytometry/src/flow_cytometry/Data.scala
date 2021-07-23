package flow_cytometry

import scodec.bits.BitVector
import scodec.codecs.*
import scodec.*

object Data:

  // For all types of data storage, and for all modes of data storage (i.e., for
  // all values of the keyword $MODE), the order in which the parameters are
  // written to the file must be the same as the order of specification. For
  // list mode data ($MODE = L, to be described later), the first parameter
  // value stored in the data part of the data set must be described by the
  // $Pl* set of keywords, the second parameter value by the $P2* keywords, etc.
  // For uncorrelated single-parameter histograms ($MODE = U), the histogram
  // values for parameter 1 must be stored first, then histogram values for
  // parameter 2, etc. In storing multiparameter correlated data, the index for
  // the first parameter is incremented first, then the second, etc. For
  // two-parameter data, this means that the first data value corresponds to
  // index 1 for parameter 1 and index 1 for parameter 2, the second data value
  // corresponds to index 2 for parameter 1 and index 1 for parameter 2, etc.
  // For data sets containing values longer than eight bits (more than one
  // byte), the keyword $BYTEORD specifies the order, from low to high, in which
  // the bytes have been stored. For example, the value "1,2,3,4" indicates that
  // the lowest-order byte is stored first (e.g., for the Digital Equipment
  // Corporation VAX family of computers). The value "2,1,4,3" indicates that
  // the high-order byte of each 16-bit word is stored first, but that the
  // low-order 16-bit word of a 32-bit value is stored first (e.g., for the
  // Motorola 68000 series of computers). A data set may contain any number of
  // uncorrelated ($MODE = U) single-parameter histograms. If gated, they must
  // have all been acquired with the same gates and thus contain the same number
  // of events; see the $TOT keyword. However, it may contain only one
  // correlated ($MODE = C, e.g., two-parameter). Also, a data set may contain
  // only one data list ($MODE = L).

  def decode(text: Map[String, String], bits: BitVector): Attempt[DecodeResult[Vector[Float]]] =
    val metadata = Metadata.parse(text)
    println(metadata)
    vector(float).decode(bits)
