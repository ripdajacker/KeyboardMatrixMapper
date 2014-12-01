package dk.mehmedbasic.keyboardlayout

import java.io.{FileInputStream, FileOutputStream, PrintWriter}

import com.google.gson._

/**
 * TODO[jekm] - someone remind me to document this class. 
 *
 * @author Jesenko Mehmedbasic
 *         created 11/12/2014, 3:00 PM
 */
object Application {

   def nonEmpty(): (String) => Boolean = !_.trim.isEmpty

   def
   main(args: Array[String]) {
      val reader: LayoutReader = new LayoutReader(new FileInputStream("src/main/resources/layout.json"))
      val keys: List[List[Letter]] = reader.getKeys()

      val wires: Array[Array[String]] = readWires
      var i = 0
      wires.foreach(
         (column: Array[String]) => {
            val myLetters: List[Letter] = reader.getFlat().filter((letter: Letter) => column.contains(letter.getString.trim))
            val outputStream: FileOutputStream = new FileOutputStream("src/main/resources/wires/" + (i + 1) + ".json")
            val writer: PrintWriter = new PrintWriter(outputStream)

            val map: List[List[Letter]] = keys.map(
               (letters: List[Letter]) => {

                  letters.map(
                     (letter: Letter) => {
                        if (myLetters.contains(letter)) {
                           letter.colored(i+1)
                        } else {
                           letter
                        }
                     })
               })

            val list = map.map(
               (letters: List[Letter]) => {
                  letters.flatMap((letter: Letter) => letter.jsonFriendly).toArray
               })
            val gson: Gson = new GsonBuilder().create()

            val json: String = gson.toJson(list.toArray)
            writer.print(json.substring(1, json.length - 1))
            writer.close()

         })
      wires
   }

   def readWires: Array[Array[String]] = {
      val source = scala.io.Source.fromFile("src/main/resources/k270_matrix.tab")
      val lines = source.mkString

      val table = Array.ofDim[String](24, 12)

      var i = 0
      for (line <- lines.split("\n")) {
         val letters: Array[String] = line.split("\t")
         var col = 0
         for (letter <- letters) {
            table(col)(i) = letter.trim
            col += 1
         }
         i += 1
      }
      source.close()
      table
   }
}


