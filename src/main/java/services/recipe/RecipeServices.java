package services.recipe;

import java.util.ArrayList;
import java.util.HashMap;

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
			HashMap<String, Object> result = RecipeCommands.createRecipe(recipe);
//			result.put("recipe string", recipeString);
			return Response.status(Response.Status.CREATED).entity(mapper.writeValueAsString(result))
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "POST").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(e.toString())
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "POST")
					.build();
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
			return Response.status(Response.Status.OK).entity(recipeJson)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET")
					.build();
//			return Response.status(Response.Status.OK).entity("test").build();

		} catch (Exception e) {
			StackTraceElement[] s = e.getStackTrace();
			StringBuilder sb = new StringBuilder();
			for (StackTraceElement se: s) {
				sb.append(se.getMethodName() + "\n");
			}
			return Response.status(500).entity(sb.toString())
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET")
					.build();
		}
	}

	// Updating a recipe HTTP PUT
	// ​​URL: https://unhoven.herokuapp.com/rest/recipe/{id}
	@PUT
//	@Path("{recipeId}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
//	public Response updateRecipe(@PathParam("recipeId") long recipeId, String recipeString) {
	public Response updateRecipe(String recipeString) {
		try {
			Recipe recipe = mapper.readValue(recipeString, Recipe.class);
//			HashMap<String, Object> result = RecipeCommands.updateRecipe(recipeId, recipe);
			HashMap<String, Object> result = RecipeCommands.updateRecipe(recipe);
			String resultJson = mapper.writeValueAsString(result);
			return Response.status(Response.Status.OK).entity(resultJson)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "PUT")
					.build();
		} catch (Exception e) {
			return Response.status(500).entity(e.toString())
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "PUT")
					.build();
		}
	}

	// Deleting a recipe HTTP DELETE
	// ​​​URL: https://unhoven.herokuapp.com/rest/recipe/{id}
	@DELETE
	@Path("{recipeId}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response deleteRecipe(@PathParam("recipeId") long recipeId) {
		String result = RecipeCommands.deleteRecipe(recipeId);
		if (!result.equals("")) {
			return Response.status(Response.Status.OK).entity(result)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "DELETE")
					.build();
		} else {
			return Response.status(500).entity(false)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "DELETE")
					.build();
		}
	}

	// Searching for a recipe HTTP GET
	// ​​​URL: https://unhoven.herokuapp.com/rest/recipe/search?query=chicken
	// marsala
	@GET
	@Path("search")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response searchRecipe(@QueryParam("query") String query) {
		try {
			ArrayList<Recipe> recipes = RecipeCommands.searchRecipe(query);
			String recipeJson = mapper.writeValueAsString(recipes);
			return Response.status(Response.Status.OK).entity(recipeJson)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET")
					.build();
		} catch (JsonProcessingException e) {
			return Response.status(500).entity(e.toString())
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET")
					.build();
		}
	}

	// Get recipes created by the user HTTP GET
	// ​​​URL: https://unhoven.herokuapp.com/rest/user/{id}/recipies/
	@GET
	@Path("user/{userId}/recipies")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response searchRecipe(@PathParam("userId") long userId) {
		try {
			ArrayList<Recipe> recipes = RecipeCommands.getRecipesByUser(userId);
			String recipeJson = mapper.writeValueAsString(recipes);
			return Response.status(Response.Status.OK).entity(recipeJson)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET")
					.build();
		} catch (JsonProcessingException e) {
			return Response.status(500).entity(e.toString())
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET")
					.build();
		}
	}

}
