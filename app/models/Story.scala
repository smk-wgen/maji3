package models

import anorm._
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.json.util._
import play.api.libs.json.Writes._
import play.api.libs.json.Reads._
import play.api.libs.json.JsValue



/**
 * Created with IntelliJ IDEA.
 * User: skunnumkal
 * Date: 3/12/13
 * Time: 8:08 PM
 * To change this template use File | Settings | File Templates.
 */
case class Story(id:Pk[Int],title:String,description:String,facilityId:Int)

object Story{
  implicit val storyWriter = (
    (__ \ "id").write(PkWriter) and
      (__ \ "title").write[String] and
      (__  \ "description").write[String] and
      (__ \ "facilityId").write[Int]
    )(unlift(Story.unapply))



  implicit val storyReader = (
    (__ \ "id").read(PkReader) and
    (__ \ "title").read[String] and
     (__ \ "description").read[String] and
     (__ \ "facilityId").read[Int]
        //read(x=>augmentString(x).toInt)
    )(Story.apply _)

  //implicit object PkFormat extends Format[Pk[Int]] {
  //   def reads(jsNum:JsValue):JsResult[Pk[Int]] = Id(jsNum.as[Int])
  //    def reads(jsNum:JsValue): JsResult[Pk[Int]] = {
  //      case JsNumber(jsNum) => JsSuccess(Id(jsNum))
  //      case _ => JsError()
  //    }
  //Id(jsNum.as[Int])
  //def writes(id:Pk[Int]):JsValue = new JsNumber(id.get)
  //}
  implicit object PkWriter extends Writes[Pk[Int]]{
    def writes(id:Pk[Int]):JsValue = new JsNumber(id.get)
  }
  implicit object PkReader extends Reads[Pk[Int]]{
    def reads(js:JsValue):JsResult[Pk[Int]] = js match{
      case JsNumber(js) => JsSuccess(Id(JsNumber(js).as[Int]))
      case _ => JsSuccess(Id(0))
    }
  }

//  implicit object IntReader extends Reads[String]{
//    def reads(s:String):JsResult[Int] = s match {
//      case Int(s) => JsSuccess(augmentString(s).toInt)
//      case _ => JsError()
//    }
//  }


}