package models

import anorm._
import anorm.SqlParser._

import play.api.libs.functional.syntax._
import play.api.libs.json._

import play.api.libs.json.Writes._
import play.api.libs.json.JsValue

import play.api.db._
import play.api.Play.current



/**
 * Created with IntelliJ IDEA.
 * User: skunnumkal
 * Date: 3/11/13
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
case class Facility(id:Pk[Int],name:String,line1:String,line2:String,city:String,state:String,country:String,
                    zip:String)

object Facility{




    val facilityParser = {
      get[Pk[Int]]("facility.id") ~
        get[String]("facility.name") ~
        get[String]("facility.line1") ~
        get[String]("facility.line2") ~
        get[String]("facility.city") ~
        get[String]("facility.state") ~
        get[String]("facility.country") ~
        get[String]("facility.zip") map {
        case id~name~line1~line2~city~state~country~zip => Facility(id,name,line1,line2,city,state,country,zip)
      }
    }

    implicit val facilityWriter = (
    (__ \ "id").write(PkWriter) and
      (__ \ "name").write[String] and
      (__ \ "line1").write[String] and
      (__ \ "line2").write[String] and
      (__ \ "city").write[String] and
      (__ \ "state").write[String] and
      (__ \ "country").write[String] and
      (__ \ "zip").write[String]
    )(unlift(Facility.unapply))


  implicit object PkWriter extends Writes[Pk[Int]]{
    def writes(id:Pk[Int]):JsValue = new JsNumber(id.get)
  }
  implicit object PkReader extends Reads[Pk[Int]]{
    def reads(js:JsValue):JsResult[Pk[Int]] = js match{
      case JsNumber(js) => JsSuccess(Id(JsNumber(js).as[Int]))
      case _ => JsError()
    }
  }


    def findById(id:Int): Option[Facility] = {
      DB.withConnection { implicit connection =>
        SQL("select * from facility where id = {id}").on(
          'id -> id
        ).as(Facility.facilityParser.singleOpt)
      }
    }

    def findAll(): Seq[Facility] = {
      DB.withConnection {
        implicit connection =>
          SQL("select * from facility").as(Facility.facilityParser *)
      }
    }
    def create(facility:Facility):Boolean = {
      DB.withConnection {
        implicit connection =>
          SQL("insert into facility(name, line1, line2, city, state,country,zip) " +
            "values ({name}, {line1}, {line2}, {city}, {state}, {country}, {zip});").on(
            'name -> facility.name,
            'line1 -> facility.line1,
            'line2 -> facility.line2,
            'city -> facility.city,
            'state -> facility.state,
            'country -> facility.country,
            'zip -> facility.zip
          ).executeUpdate()

          return true;
      }
    }


	
	
}