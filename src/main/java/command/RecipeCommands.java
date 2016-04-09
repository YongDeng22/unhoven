package command;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectionprovider.ConnectionProvider;
import model.Ingredient;
import model.Recipe;
import model.User;

public class RecipeCommands {
	private static final String dbURL = "dburl";
	private static final String createRecipeSql = "";
	private static final String getSingleRecipeSql = "SELECT * FROM RECIPE WHERE ID = ?";
	private static final String updateRecipeSql = "";
	private static final String deleteRecipeSql = "";
	private static final String getRecipeByUserSql = "";
	private static final String searchRecipeSql = "";
	
	private static final String createIngredientSql = "";
	private static final String getSingleIngredientSql = "selec";
	private static final String updateIngredientSql = "";
	private static final String deleteIngredientSql = "";
	private static final String getIngredientByUserSql = "";
	private static final String searchIngredientSql = "";
	
	private static final String createUserSql = "";
	private static final String getSingleUserSql = "selec";
	private static final String updateUserSql = "";
	private static final String deleteUserSql = "";
	private static final String getUserByUserSql = "";
	private static final String searchUserSql = "";
	
	
	public static boolean createRecipe(Recipe recipe) {
		// TODO Auto-generated method stub
		return true;
	}

	public static Recipe getRecipe(long id) {
		Recipe recipe = null;
		try {
			Connection connection = ConnectionProvider.getConnection();
			PreparedStatement statement = connection.prepareStatement(getSingleRecipeSql);
			statement.setLong(0, id);
			statement.setMaxRows(1);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				recipe = new Recipe();
				recipe.setId(rs.getLong("id"));
				recipe.setCategory(rs.getString("category"));
				recipe.setDescription(rs.getString("description"));
				recipe.setNumber_of_servings(rs.getInt("number_of_servings"));
				recipe.setTitle(rs.getString("title"));
				recipe.setSubCategory(rs.getString("subCategory"));
				recipe.setPrimaryIngredient(rs.getString("primaryIngredient"));
				recipe.setWebURL(rs.getString("webURL"));
				recipe.setImageURL(rs.getString("imageURL"));
				recipe.setInstructions(rs.getString("instructions"));
				recipe.setYieldNumber(rs.getInt("yieldNumber"));
				recipe.setYieldUnit(rs.getString("yieldUnit"));
				recipe.setIngredients(getIngredients(id));
				recipe.setAuthors(getAuthors(id));
			}
			
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
	
	
	//#########################################
	public static ArrayList<User> getAuthors(long id) {

		
		return null;
	}
	
	public static ArrayList<Ingredient> getIngredients(long id) {

		
		return null;
	}

}
