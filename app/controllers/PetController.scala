package controllers

import javax.inject._
import models.{ApiResult, Pet, PetRepository}
import play.api.libs.json.Json
import play.api.mvc._

@Singleton
class PetController @Inject()(val controllerComponents: ControllerComponents, pets: PetRepository) extends BaseController {

  def get(id: Long) = Action { request =>
    val pet = pets.get(id)
    if (pet.isDefined) {
      Ok(Json.toJson(ApiResult(result = Some(Json.toJson(pet)))))
    } else {
      NotFound(Json.toJson(ApiResult(error = Some("Pet not found"))))
    }
  }

  def create(): Action[Pet] = Action(parse.json[Pet]) { request =>
    val pet = pets.create(request.body)
    Ok(Json.toJson(ApiResult(message = Some(s"${request.body.name} created"), result = Some(Json.toJson(pet)))))
  }

  def update(): Action[Pet] = Action(parse.json[Pet]) { request =>
    val pet = pets.update(request.body)
    if (pet.isDefined) {
      Ok(Json.toJson(ApiResult(message = Some(s"${request.body.name} updated"), result = Some(Json.toJson(pet)))))
    } else {
      NotFound(Json.toJson(ApiResult(error = Some("Pet not found"))))
    }
  }

  def delete(id: Long) = Action { request =>
    val pet = pets.delete(id)
    if (pet.isDefined) {
      Ok(Json.toJson(ApiResult(message = Some(s"${pet.get.name} deleted"), result = Some(Json.toJson(pet)))))
    } else {
      NotFound(Json.toJson(ApiResult(error = Some("Pet not found"))))
    }
  }

}
