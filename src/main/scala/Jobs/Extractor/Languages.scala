package Jobs.Extractor

trait Languages {

  private def processLine(line: String): Array[(String, String)] = {
    line.toString.split(": ") match {
      case Array(language, extensions) =>
        extensions.toString.stripMargin.split(" ").map((_, language))
    }
  }

  val languageNames = """Rust: rs rlib
            |Kotlin: kt kts
            |Python: py pyc pyd pyo pyw pyz
            |TypeScript: ts tsx
            |Go: go
            |Swift: swift
            |JavaScript: js
            |C#: cs
            |F#: fs fsi fsx fsscript
            |Clojure: clj cljs cljc edn
            |Scala: scala sc
            |SQL: sql
            |HTML: html htm
            |CSS: css
            |Haskell: hs lhs
            |Julia: jl
            |Java: java class jar
            |R: r R RData rds rda
            |Ruby: rb
            |Erlang: erl hrl
            |C++: C cc cpp cxx c++ h hpp hxx h++
            |Hack: hh
            |PHP: php phtml php3 php4 php5 php7 phps php-s
            |Ocaml: ml mli"""
    .stripMargin
    .split("\n")
    .flatMap(processLine)
    .toMap

  val languagePatterns: Map[String, List[String]] =
    Map(
      "Python" -> List("""import\s+([0-9a-zA-Z]+)""", """from\s+(\w+?)\s+import\s+(?:[0-9a-zA-Z])"""),
      "Scala" -> List("""import\s+([0-9a-zA-Z\.]*[0-9a-zA-Z]+)"""),
      "Haskell" -> List("""import\s+(?!qualified)\s*([0-9a-zA-Z]+)""", """import\squalified\s([0-9a-zA-Z]+)"""), // Could probably do this with optional non-capture group
      "Java" -> List("""import\s+(?!static)\s*([0-9a-zA-Z\.]*[0-9a-zA-Z]+)""", """import\s+static\s+([0-9a-zA-Z\.]*[0-9a-zA-Z]+)"""),
      "Rust" -> List("""use\s+([a-zA-Z0-9]+)::.+?;"""),
      "JavaScript" -> List("""import.*?\"(.*?)\";""")
    )
}
