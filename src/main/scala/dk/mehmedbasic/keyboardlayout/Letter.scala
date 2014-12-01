package dk.mehmedbasic.keyboardlayout

import java.util

import org.apache.commons.lang3.StringEscapeUtils

import scala.collection.immutable.HashMap

/**
 * A letter
 *
 * @author Jesenko Mehmedbasic
 *         created 11/13/2014, 8:43 PM
 */
class Letter(letter: String, map: Map[String, Object] = new HashMap[String, Object]) {
   def colored(wireIndex: Int): Letter = new Letter(
      letter, map + ("c" -> {
         if (4 to 15 contains (wireIndex )) {
            "0084ff"
         } else {
            "618a40"
         }
      }))


   def configure(newMap: Map[String, Double]): Letter = {
      val transform: Map[String, Object] = newMap.transform(test)

      new Letter(letter, map ++ transform)
   }

   def test: (String, Double) => Object = (s, d) => d.asInstanceOf[Object]

   def getString = letter

   override def toString(): String = letter

   def jsonFriendly: Array[Object] = if (map.isEmpty) {
      Array(letter)
   } else {
      val dest: util.HashMap[String, Object] = new util.HashMap[String, Object]()
      map.foreach((tuple: (String, Object)) => dest.put(tuple._1, tuple._2))
      Array(dest, StringEscapeUtils.escapeJson(letter), whiteMap())
   }

   def whiteMap(): util.LinkedHashMap[String, Object] = {
      val map = new util.LinkedHashMap[String, Object]()
      map.put("c", "#ffffff")
      map
   }


}
