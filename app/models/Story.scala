package models

import anorm._
import anorm.SqlParser._
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.json.Writes._
import play.api.libs.json.Reads._
import play.api.db._
import play.api.Play.current



/**
 * Created with IntelliJ IDEA.
 * User: skunnumkal
 * Date: 3/12/13
 * Time: 8:08 PM
 * problem:String,you:String,treatment:String,facilityId:Int,rating:String
 * To change this template use File | Settings | File Templates.
 */
case class Story(id:Pk[Int],title:String,description:String,facilityId:Int,problemType:String,you:String,
                  treatment:String,rating:String)

object Story{
  implicit val storyWriter = (
    (__ \ "id").write(PkWriter) and
    (__ \ "title").write[String] and
    (__  \ "description").write[String] and
    (__ \ "facilityId").write[Int] and
    (__ \ "problem").write[String] and
      (__ \ "you").write[String] and
      (__ \ "treatment").write[String] and
      (__ \ "rating").write[String]
    )(unlift(Story.unapply))



  implicit val storyReader = (
    (__ \ "id").read(PkReader) and
    (__ \ "title").read[String] and
     (__ \ "description").read[String] and
     (__ \ "facilityId").read(StringReader) and
      (__ \ "problem").read[String] and
      (__ \ "you").read[String] and
      (__ \ "treatment").read[String] and
      (__ \ "rating").read[String]
        //read(x=>augmentString(x).toInt)
    )(Story.apply _)


  implicit object PkWriter extends Writes[Pk[Int]]{
    def writes(id:Pk[Int]):JsValue = new JsNumber(id.get)
  }
  implicit object PkReader extends Reads[Pk[Int]]{
    def reads(js:JsValue):JsResult[Pk[Int]] = js match{
      case JsNumber(js) => JsSuccess(Id(JsNumber(js).as[Int]))
      case _ => JsSuccess(Id(0))
    }
  }

  implicit object StringReader extends Reads[Int] {
       def reads(js:JsValue):JsResult[Int] = js match{
            case JsNumber(a) => JsSuccess(a.intValue)
            case JsString(x) => convert2Int(x) match{
                        case Some(i) => JsSuccess(i)
                          case None => JsError("NaN")
                             }
              case _ => JsError("This is neither a string nor number")

        }
  }

  def convert2Int(s : String) : Option[Int] = try {
    Some(s.toInt)
  } catch {
    case _ : java.lang.NumberFormatException => None
  }
  // -- Parsers

  /**
   * Parse a Project from a ResultSet
   */
  val storyParser = {
    get[Pk[Int]]("story.id") ~
      get[String]("story.name") ~
      get[String]("story.description") ~
      get[String]("story.problem") ~
      get[String]("story.you") ~
      get[String]("story.treatment") ~
      get[Int]("story.facility_id") ~
      get[String]("story.rating") map {
      case id~title~description~problem~you~treatment~facilityId~rating =>
        Story(id, title, description, facilityId,problem, you, treatment,rating)
    }
  }
  def findAll():Seq[Story] = {
    DB.withConnection {
      implicit connection =>
        SQL("select * from story").as(Story.storyParser *)
    }
  }

  def findById(id:Int):Option[Story] = {

    DB.withConnection { implicit connection =>
      SQL("select * from story where id = {id}").on(
        'id -> id
      ).as(Story.storyParser.singleOpt)
    }
  }

  def create(story:Story) = {
    DB.withConnection {
      implicit connection =>
        SQL("insert into story(name, description, problem, you, treatment,facility_id,rating) " +
          "values ({title}, {desc}, {problem}, {you}, {treatment}, {facilityId}, {rating});").on(
          'title -> story.title,
          'desc -> story.description,
          'problem -> story.problemType,
          'you -> story.you,
          'treatment -> story.treatment,
          'facilityId -> story.facilityId,
          'rating -> story.rating
        ).executeUpdate()
    }
  }




}