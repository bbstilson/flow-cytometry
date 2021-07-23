package flow_cytometry

// The keyword $DATATYPE defines the format of the data.
// It has four possible values: I, F, D, and A.
//
// $DATATYPE = I.
// The data are written as unsigned binary integers.
// The length in bits of the data values is specified by the keyword “$PnB”.
// Here, the letter “P” stands for “Parameter”, or the variable being
// measured, the letter “B” stands for bits, and the letter “n” stands for
// the number of the parameter being specified
// (e.g., n = 1 might be for forward light scatter and n = 2 for fluorescence).
// This allows the data word length to be specified dynamically, facilitating
// compatibility between machines with different data word lengths and / or
// allowing bit compression of the data. Data are stored in a continuous
// stream of bits, with no delimiters.
//
// $DATATYPE = F.
// The data are written as single precision floating point values in the IEEE
// standard format. Note that the $PnB keywords should be set to a value of 32.
//
// $DATATYPE = D.
// The data are written as double precision floating point values in the IEEE
// standard format. Note that the $PnB keywords should be set to a value of 64.
//
// $DATATYPE = A.
// The data are written as ASCIIencoded integer values. In this case, the
// keyword “$PnB” specifies the number of bytes per value (one byte per
// character). This represents fixed format ASCII data.
//
// For example, for $PlB = 6, the maximum value would be 999999. Data are
// stored in a continuous byte stream, with no delimiters. If the value of
// this keyword is the * character (e.g., $PlB/*/), the number of characters
// per value may vary. In this case, all values will be separated by
// delimiters: “space”, “tab”, “comma”, “carriage return”, and “line feed”
// characters are allowed delimiters. Note that multiple, consecutive
// delimiters are treated as a single delimiter. Since there are significant
// differences between the way in which consecutive delimiters are treated by
// different programming languages, care should be taken when using this
// format. Zero values must be explicitly signified by the zero (0) character.
// Thus, the string “1,3,, ,3” (note the space between the third and
// fourth commas) would only specify three values. (It would be treated as
// between 3 and 5 values by different programming languages.)
enum Datatype:
  case A, D, F, I

object Datatype:

  def parse(str: String): Datatype = str match
    case "A" => Datatype.A
    case "D" => Datatype.D
    case "F" => Datatype.F
    case "I" => Datatype.I
    case _   => throw new Exception("unknown $DATATYPE")
