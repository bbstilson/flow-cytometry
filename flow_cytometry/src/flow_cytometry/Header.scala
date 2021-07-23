package flow_cytometry

import scodec.bits.BitVector
import scodec.codecs.*
import scodec.*

case class Header(
  version: String,
  textStart: Long,
  textEnd: Long,
  dataStart: Long,
  dataEnd: Long,
  analysisStart: Long,
  analysisEnd: Long
)

object Header:
  def decode(bits: BitVector): DecodeResult[Header] = codec.decode(bits).require

  // The HEADER part of each dataset in a data file begins with the
  // Flow Cytometry Standard version identifier, which occupies the first
  // 10 bytes. This version of the Standard will be entered as “FCS2.0”
  // followed by four spaces.
  private val versionCodec = fixedSizeBytes(10, utf8)

  // The next eight (8) bytes contain the offset, in bytes, from the beginning
  // of the dataset to the start of the TEXT part of the dataset; i.e., an
  // offset of 60 bytes points to the 61st byte of the dataset.
  // All offsets are in ASCII, right justified.
  private val textStartCodec = fixedSizeBytes(8, ascii)

  // The next eight (8) bytes contain the offset, again in bytes, from the
  // beginning of the dataset to the last byte of the TEXT part.
  private val textEndCodec = fixedSizeBytes(8, ascii)

  // The next four (4) 8-byte numbers give the offsets to the beginning
  // and end of the DATA part...
  private val dataStartCodec = fixedSizeBytes(8, ascii)
  private val dataEndCodec = fixedSizeBytes(8, ascii)
  // ...and the beginning and end of the ANALYSIS part.
  private val analysisStartCodec = fixedSizeBytes(8, ascii)
  private val analysisEndCodec = fixedSizeBytes(8, ascii)

  // If any part of the dataset is not included in the file, the offsets can
  // be zero (0) or blank. Additional pairs of locally defined offsets may
  // follow the standard three (3) pairs if desired. Thus, the first
  // 58 bytes of the dataset are of fixed format and contain information that
  // identifies the version number of the Standard used and pointers to the
  // beginning and end of all mandatory parts of the dataset.
  // The offsets contained in the HEADER part of a second dataset refer to the
  // first byte of the second dataset; i.e., all offsets within a dataset are
  // relative to the first byte of the dataset. In addition to the standard
  // offsets described above, which point to the standard parts of a dataset, a
  // user can add additional offsets to point to user-defined parts of the
  // dataset. These user-defined parts will not be readable by another
  // laboratory, unless the user passes the appropriate information along to
  // the laboratory.

  private val codec = {
    ("version" | versionCodec) ::
      ("textStart" | textStartCodec) ::
      ("textEnd" | textEndCodec) ::
      ("dataStart" | dataStartCodec) ::
      ("dataEnd" | dataEndCodec) ::
      ("analysisStart" | analysisStartCodec) ::
      ("analysisEnd" | analysisEndCodec)
  }.map { case (ver, tS, tE, dS, dE, aS, aE) =>
    val trimmed = List(ver, tS, tE, dS, dE, aS, aE).map(_.trim)
    // convert strings to longs and multiply by 8 for bytes
    trimmed.tail.map(_.toLong * 8) match
      case List(tS, tE, dS, dE, aS, aE) => (trimmed.head, tS, tE, dS, dE, aS, aE)
      case _                            => throw Exception("bad format")
  }.as[Header]
