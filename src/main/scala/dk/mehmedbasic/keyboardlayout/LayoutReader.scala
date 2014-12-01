package dk.mehmedbasic.keyboardlayout

import java.io.{InputStream, InputStreamReader}
import java.util

import com.google.gson.Gson

import scala.collection.convert.DecorateAsScala
import scala.collection.convert.DecorateAsJava
import scala.collection.mutable

/**
 * Reads the layout of
 *
 * @author Jesenko Mehmedbasic
 *         created 11/12/2014, 2:58 PM
 */
class LayoutReader(in: InputStream) extends DecorateAsScala {
   private val streamReader = new InputStreamReader(in)
   private val array = new Gson().fromJson(streamReader, classOf[util.ArrayList[Object]])

   val keyboard = new mutable.MutableList[List[Letter]]
   val flatList = new mutable.MutableList[Letter]

   for (read <- array.asScala) {

      val row = new mutable.MutableList[Letter]

      val line = read.asInstanceOf[util.ArrayList[Object]].asScala

      var map: util.Map[String, Double] = null
      for (x <- line) {
         if (x.isInstanceOf[util.Map[String, Double]]) {
            map = x.asInstanceOf[util.Map[String, Double]]
         } else {

            val letter: Letter = if (map != null) {
               new Letter(x.asInstanceOf[String]).configure(map.asScala.toMap)
            } else {
               new Letter(x.asInstanceOf[String])
            }
            row += letter
            flatList += letter
            map = null
         }
      }
      keyboard += row.toList
   }

   def getFlat(): List[Letter] = flatList.toList

   def getKeys(): List[List[Letter]] = keyboard.toList
}
