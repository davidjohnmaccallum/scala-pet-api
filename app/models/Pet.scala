package models

import anorm.{Macro, RowParser, SqlStringInterpolation}
import javax.inject.{Inject, Singleton}
import play.api.db.Database
import play.api.libs.json.Json

case class Pet(id: Option[Long] = None, name: String, species: String, gender: String, temperament: String)

object Pet {
  implicit val format = Json.format[Pet]
  val parser: RowParser[Pet] = Macro.namedParser[Pet]
}

@Singleton
class PetRepository @Inject()(db: Database) {

  def get(id: Long) = {
    db.withConnection { implicit c =>
      SQL"select * from pets where id = $id".as(Pet.parser.singleOpt)
    }
  }

  def create(pet: Pet) = pet

  def update(pet: Pet) = Some(pet)

  def delete(id: Long) = {
    db.withConnection { implicit c =>
      SQL"select * from pets where id = $id".as(Pet.parser.singleOpt)
    }
  }

}
