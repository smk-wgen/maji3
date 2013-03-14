package controllers

import play.api._
import play.api.mvc._
import models._
import play.api.libs.json._
import anorm._



object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def listFacility(id:Int) = Action{
    val f = Facility(Id(1),"name","address","houseNu","Mumbai","Mah","India","49554")
    val fjs = Json.toJson(f)
    Ok(fjs)
  }

  def listFacilities = Action{
    val f1 = Facility(Id(1),"name_1","address","houseNu","Mumbai","Mah","India","49554")
    val f2 = Facility(Id(1),"name_2","address","houseNu","Mumbai","Mah","India","49554")
    val facilties:Seq[Facility] = Seq(f1,f2)
    val fjs = Json.toJson(facilties)
    Ok(fjs)

  }

  def listStories = Action{
    val s1 = Story(Id(1),"title","desc",1)
    val s2 = Story(Id(2),"title","desc",1)
    val sjs = Json.toJson(Seq(s1,s2))
    Ok(sjs)

  }

  def listStory(id:Int) = Action{
    val s1 = Story(Id(1),"title","desc",1)
    Ok(Json.toJson(s1))
  }

  def newStory = Action(parse.json){
    request =>
      val json = request.body
      System.out.println(json)
      val story = json.as[Story];
      System.out.println("Story" + story)
      Ok(story.title)
//      json.validate[Story].fold(
//        valid = ( res => Ok(res.title) ),
//        invalid = ( e => BadRequest(e.toString) )
//      )
    //request => val json = request.body


  }

  def getStoryForm = Action {
    Ok(views.html.storyform())
  }

  def javascriptRoutes() = Action { implicit request =>
    import play.api.Routes
    Ok(
      Routes.javascriptRouter("jsRouter", Some("jQuery.ajax"))(
        routes.javascript.Application.newStory,
        routes.javascript.Application.listStories,
        routes.javascript.Application.listFacilities
      )
    ).as("text/javascript")
  }
}