# Flow Cytometry

This is a small program that parses Flow Cytometry data (.fcs files). The implementation follows [this paper](https://onlinelibrary.wiley.com/doi/epdf/10.1002/cyto.990110303), and the files found in the [resources](https://github.com/bbstilson/flow-cytometry/tree/main/flow_cytometry/resources) directory were pulled from [here](http://flowrepository.org/id/FR-FCM-ZZC8).

## How to run

You will need Scala and Mill. If you have neither, I recommend using the [Scala One-Click Install](https://www.scala-lang.org/2020/06/29/one-click-install.html). After that, you can run:

```plaintext
mill flow_cytometry.run
```

This program doesn't really do anything but parse a binary fcs file into a structured, statically typed form, so you should mostly see things being printed about the file.

The entry point to the program is the [`FCS.scala`](https://github.com/bbstilson/flow-cytometry/blob/main/flow_cytometry/src/flow_cytometry/FCS.scala).
