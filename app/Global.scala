/**
 * Created with IntelliJ IDEA.
 * User: skunnumkal
 * Date: 3/16/13
 * Time: 9:35 PM
 * To change this template use File | Settings | File Templates.
 */
import play.api._

import models._
import anorm._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }

}

/**
 * Initial set of data to be imported
 * in the sample application.
 */
object InitialData {

  def date(str: String) = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(str)

  def insert() = {

    if(Facility.findAll.isEmpty) {

      Seq(
        Facility(Id(1),"Hospital 1", "Address Line1","Line2","Mumbai","MH","India","09595"),
        Facility(Id(2),"Hospital 2", "Address Line1","Line2","Pune","MH","India","85888"),
        Facility(Id(3),"Hospital 3", "Address Line1","Line2","Mumbai","MH","India","09595")
      ).foreach(Facility.create)
    }
  }
}