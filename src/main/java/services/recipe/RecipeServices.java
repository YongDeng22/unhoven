package services.recipe;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import command.*;


@Path("/recipe")
public class RecipeServices {
	ObjectMapper mapper = new ObjectMapper();

	//Creating a recipe HTTP POST
	//​URL: https://unhoven.herokuapp.com/rest/recipe
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response createRecipe(String recipeString) {
		try {
			Recipe recipe = mapper.readValue(recipeString, Recipe.class);
			RecipeCommands.createRecipe(recipe);
			return Response.status(Response.Status.CREATED).entity(recipeString).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(e.toString()).build();
		}
	}
	
	// Reading a recipe HTTP GET
	// ​​URL: https://unhoven.herokuapp.com/rest/recipe/{id}
	@GET
	@Path("{recipeId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response getRecipe(@PathParam("recipeId") long recipeId) {
		try {
			Recipe recipe = RecipeCommands.getRecipe(recipeId);
			String recipeJson = mapper.writeValueAsString(recipe);
			return Response.status(Response.Status.OK).entity(recipeJson).build();
//			return Response.status(Response.Status.OK).entity("test").build();

		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	// Updating a recipe HTTP PUT
	// ​​URL: https://unhoven.herokuapp.com/rest/recipe/{id}
	@PUT
	@Path("{recipeId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response updateRecipe(@PathParam("recipeId") long recipeId, String recipeString) {
		try {
			Recipe recipe = mapper.readValue(recipeString, Recipe.class);
			boolean result = RecipeCommands.updateRecipe(recipeId, recipe);
			String recipeJson = mapper.writeValueAsString(recipe);
			return Response.status(Response.Status.OK).entity(recipeJson).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.toString()).build();
		}
	}

	// Deleting a recipe HTTP DELETE
	// ​​​URL: https://unhoven.herokuapp.com/rest/recipe/{id}
	@DELETE
	@Path("{recipeId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response deleteRecipe(@PathParam("id") long recipeId) {
		boolean result = RecipeCommands.deleteRecipe(recipeId);
		if (result) {
			return Response.status(Response.Status.OK).entity(recipeId).build();
		} else {
			return Response.status(500).entity(false).build();
		}
	}

	// Searching for a recipe HTTP GET
	// ​​​URL: https://unhoven.herokuapp.com/rest/recipe/search?query=chicken
	// marsala
	@GET
	@Path("search")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response searchRecipe(@QueryParam("searchParam") String searchParam) {
		try {
			Recipe[] recipes = RecipeCommands.searchRecipe(searchParam);
			String recipeJson = mapper.writeValueAsString(recipes);
			return Response.status(Response.Status.OK).entity(recipeJson).build();
		} catch (JsonProcessingException e) {
			return Response.status(500).entity(e.toString()).build();
		}
	}

	// Get recipes created by the user HTTP GET
	// ​​​URL: https://unhoven.herokuapp.com/rest/user/{id}/recipies/
	@GET
	@Path("recipies/{userId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response searchRecipe(@PathParam("userId") long userId) {
		try {
			Recipe[] recipes = RecipeCommands.getRecipesByUser(userId);
			String recipeJson = mapper.writeValueAsString(recipes);
			return Response.status(Response.Status.OK).entity(recipeJson).build();
		} catch (JsonProcessingException e) {
			return Response.status(500).entity(e.toString()).build();
		}
	}

}
