package models

import anorm.{Macro, RowParser, SqlParser, SqlStringInterpolation}
import javax.inject.{Inject, Singleton}
import play.api.db.Database
import play.api.libs.json.{Json, OFormat}

case class Pet(id: Option[Long] = None, name: String, species: String, gender: String, temperament: String)

object Pet {
  implicit val format: OFormat[Pet] = Json.format[Pet]
  val parser: RowParser[Pet] = Macro.namedParser[Pet]
}

@Singleton
class PetRepository @Inject()(db: Database) {

  def get(id: Long): Option[Pet] = {
    db.withConnection { implicit c =>
      SQL"select * from pets where id = $id".as(Pet.parser.singleOpt)
    }
  }

  def create(pet: Pet): Int = {
    db.withConnection { implicit c =>
      SQL"""
        insert into pets (name, species, gender, temperament)
        values (${pet.name}, ${pet.species}, ${pet.gender}, ${pet.temperament})
        returning id
        """.as(SqlParser.int("id").single)
    }
  }

  def update(pet: Pet): Option[Pet] = {
    db.withConnection { implicit c =>
      SQL"""
        update pets set
          name = ${pet.name},
          species = ${pet.species},
          gender = ${pet.gender},
          temperament = ${pet.temperament}
        where id = ${pet.id}
        returning id, name, species, gender, temperament
        """.as(Pet.parser.singleOpt)
    }
  }

  def delete(id: Long): Boolean = {
    db.withConnection { implicit c =>
      SQL"delete from pets where id = $id".execute
    }
  }

}
