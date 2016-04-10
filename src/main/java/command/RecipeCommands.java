package command;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectionprovider.ConnectionProvider;
import model.Ingredient;
import model.Poster;
import model.Recipe;;

public class RecipeCommands {

	public static boolean createRecipe(Recipe recipe) {
		// TODO Auto-generated method stub
		return true;
	}

	public static Recipe getRecipe(long recipe_id) {
		Recipe recipe = null;
		ArrayList<Ingredient> ingredients = new ArrayList();

		try (Connection connection = ConnectionProvider.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(CommandConstants.GET_SINGLE_RECIPE_BY_ID_SQL);
			statement.setLong(0, recipe_id);

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				recipe = new Recipe();
				if (recipe.getId() != recipe_id) {
					recipe.setId(recipe_id);
					recipe.setTitle(rs.getString("title"));
					recipe.setDescription(rs.getString("description"));
					recipe.setCategory(rs.getString("category"));
					recipe.setSubCategory(rs.getString("subCategory"));
					recipe.setPrimaryIngredient(rs.getString("primary_ingredient"));
					recipe.setWebURL(rs.getString("web_url"));
					recipe.setImageURL(rs.getString("image_url"));
					recipe.setInstructions(rs.getString("instructions"));
					recipe.setYieldNumber(rs.getInt("yield_number"));
					recipe.setYieldUnit(rs.getString("yield_unit"));
					
					Poster poster = new Poster();
					poster.setUser_id(rs.getLong("user_id"));
					poster.setUserName(rs.getString("user_name"));
					recipe.setPoster(poster);
				}

				Ingredient ingredient = new Ingredient();
				ingredient.setIngredient_id(rs.getLong("ingredient_id"));
				ingredient.setName(rs.getString("name"));
				ingredient.setAmount(rs.getInt("amount"));
				ingredient.setUnit(rs.getString("unit"));
				ingredients.add(ingredient);
			}
			recipe.setIngredients(ingredients);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recipe;
	}

	public static boolean updateRecipe(long recipeId, Recipe recipe) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean deleteRecipe(long id) {
		// TODO Auto-generated method stub
		return true;
	}

	public static Recipe[] searchRecipe(String searchParam) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Recipe[] getRecipesByUser(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	// #########################################
	public static ArrayList<Poster> getAuthors(long id) {

		return null;
	}

	public static ArrayList<Ingredient> getIngredients(long id) {

		return null;
	}

}
