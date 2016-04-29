package command;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import connectionprovider.ConnectionProvider;
import model.Ingredient;
import model.Poster;
import model.Recipe;;

public class RecipeCommands {
	static Connection connection = null;

// ******************************************************************************************************************
	// REGION: CREATE RECIPE AND POPULATE OTHER TABLES
	public static HashMap<String, Object> createRecipe(Recipe recipe) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			connection = ConnectionProvider.getConnection();

			insertRecipe(recipe, connection, result);

			Poster poster = recipe.getPoster();
			insertPoster(poster, connection, result);

			ArrayList<Ingredient> ingredients = recipe.getIngredients();
			for (Ingredient ingredient : ingredients) {
				insertIngredient(ingredient, connection, result);
				insertAssociation(recipe.getRecipeId(), ingredient.getIngredientId(), poster.getUserId(), connection, result);
			}
//			result.put("Recipe insert operation", "sucess");
		} catch (URISyntaxException e) {
			e.printStackTrace();
			result.put("Recipe insert operation", e.getMessage());
		} catch (SQLException e) {
			result.put("Recipe insert operation", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			result.put("Recipe insert operation", e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}	
		}
		return result;
	}

	// insert recipe to database--table recipe
	public static void insertRecipe(Recipe recipe, Connection connection, HashMap<String, Object> result) {
		try {
			PreparedStatement recipeStatement = connection.prepareStatement(CommandConstants.CREATE_RECIPE_SQL);
			long recipeId = recipe.getRecipeId();
			if (recipeId != 0) {
				recipeStatement.setLong(1, recipeId);
				recipeStatement.setString(2, recipe.getTitle());
				recipeStatement.setString(3, recipe.getDescription());
				recipeStatement.setString(4, recipe.getCategory());
				recipeStatement.setString(5, recipe.getSubCategory());
				recipeStatement.setString(6, recipe.getPrimaryIngredient());
				recipeStatement.setString(7, recipe.getWebURL());
				recipeStatement.setString(8, recipe.getImageURL());
				recipeStatement.setString(9, recipe.getInstructions());
				recipeStatement.setLong(10, recipe.getYieldNumber());
				recipeStatement.setString(11, recipe.getYieldUnit());
				recipeStatement.executeUpdate();
				result.put("Recipe insert operation for id: " + recipeId, "success");
			} else {
				result.put("Recipe insert operation for id: " + recipeId, "Mising recipe ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// insert/update poster to database--table poster
	public static void insertPoster(Poster poster, Connection connection, HashMap<String, Object> result) {
		try {
			long posterId = poster.getUserId();
			PreparedStatement posterStatement = null;
			if (posterId != 0) {
				posterStatement = connection.prepareStatement(CommandConstants.CREATE_POSTER_SQL);
				posterStatement.setLong(1, posterId);
				posterStatement.setString(2, poster.getUserName());
				posterStatement.executeUpdate();
				result.put("Poster insert operation for id: " + posterId, "success");
			} else {
				result.put("Poster insert operation for id: " + posterId, "Mising user ID");
			}
		} catch (SQLException e) {
			result.put("Poster insert operation", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			result.put("Poster insert operation", e.getMessage());
			e.printStackTrace();
		}
	}

	// insert/update ingredient to database--table ingredient
	public static void insertIngredient(Ingredient ingredient, Connection connection, HashMap<String, Object> result) {
		try {
			long ingredientId = ingredient.getIngredientId();
			PreparedStatement posterStatement = null;
			if (ingredientId != 0) {
				posterStatement = connection.prepareStatement(CommandConstants.CREATE_INGREDIENT_SQL);
				posterStatement.setLong(1, ingredientId);
				posterStatement.setString(2, ingredient.getName());
				posterStatement.setInt(3, ingredient.getAmount());
				posterStatement.setString(4, ingredient.getUnit());
				posterStatement.executeUpdate();
				result.put("Ingredient insert operation for id: " + ingredientId, "success");
			} else {
				result.put("Ingredient insert operation for id: " + ingredientId, "Missing ingredient ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result.put("Ingredient insert operation", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("Ingredient insert operation", e.getMessage());
		}
	}

	// insert/update ingredient to database--table ingredient
	public static void insertAssociation(long recipeId, long userId, long ingredientId, Connection connection,
			HashMap<String, Object> result) {
		try {
			PreparedStatement associationStatement = null;
			if (recipeId != 0) {
				associationStatement = connection.prepareStatement(CommandConstants.CREATE_ASSOCIATION_SQL);
				associationStatement.setLong(1, recipeId);
				associationStatement.setLong(2, userId);
				associationStatement.setLong(3, ingredientId);
				associationStatement.executeUpdate();
				result.put("Association insert operation", "success");
			}
		} catch (SQLException e) {
			result.put("Association insert operation", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			result.put("Association insert operation", e.getMessage());
			e.printStackTrace();
		}
	}

// ******************************************************************************************************************
	// REGION: GET SINGLE RECIPE BY RECIPE_ID
	public static Recipe getRecipe(long recipe_id) throws SQLException {
		Recipe recipe = new Recipe();
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		try {
			connection = ConnectionProvider.getConnection();
			PreparedStatement statement = connection.prepareStatement(CommandConstants.GET_SINGLE_RECIPE_BY_ID_SQL);
			statement.setLong(1, recipe_id);

			ResultSet rs = statement.executeQuery();
			System.out.println(rs.getMetaData().toString());

			while (rs.next()) {
				if (recipe.getRecipeId() != recipe_id) {
					recipe.setRecipeId(recipe_id);
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

					recipe.setPoster(populatePoster(rs));
				}

				ingredients.add(populateIngredient(rs));

			}
			recipe.setIngredients(ingredients);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return recipe;
	}

	// populate poster from result set
	public static Poster populatePoster(ResultSet rs) {
		Poster poster = new Poster();
		try {
			poster.setUserId(rs.getLong("user_id"));
			poster.setUserName(rs.getString("user_name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return poster;
	}

	// populate ingredient from result set
	public static Ingredient populateIngredient(ResultSet rs) {
		Ingredient ingredient = new Ingredient();
		try {
			ingredient.setIngredientId(rs.getLong("ingredient_id"));
			ingredient.setName(rs.getString("name"));
			ingredient.setAmount(rs.getInt("amount"));
			ingredient.setUnit(rs.getString("unit"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ingredient;
	}

// ******************************************************************************************************************
	// REGION: UPDATE RECIPE AND POPULATE OTHER TABLES
	public static HashMap<String, Object> updateRecipe(long recipeId, Recipe recipe) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		ArrayList<Ingredient> ingredients = recipe.getIngredients();
		Poster poster = recipe.getPoster();

		try {
			connection = ConnectionProvider.getConnection();
			int recipeRow = updateRecipe(recipe, connection, result);
			if (recipeRow < 1) {
				insertRecipe(recipe, connection, result);
			}

			int posterRow = updatePoster(poster, connection, result);
			if (posterRow < 1) {
				insertPoster(poster, connection, result);
			}

			long recipe_Id = recipe.getRecipeId();
			long userId = poster.getUserId();
			for (Ingredient ingredient : ingredients) {
				int ingRow = updateIngredient(ingredient, connection, result);
				if (ingRow < 1) {
					insertIngredient(ingredient, connection, result);
				}
				int associationRow = updateAssociation(recipe_Id, userId, ingredient.getIngredientId(), connection, result);
				if (associationRow < 1) {
					insertAssociation(recipe_Id, userId, ingredient.getIngredientId(), connection, result);
				}
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
//			result.put("insert operation", e.getMessage());
		} catch (SQLException e) {
//			result.put("insert operation", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
//			result.put("insert operation", e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// update recipe in database--table recipe
	public static int updateRecipe(Recipe recipe, Connection connection, HashMap<String, Object> result) {
		int row = 0;
		try {
			PreparedStatement recipeStatement = connection.prepareStatement(CommandConstants.UPDATE_RECIPE_SQL);
			long recipeId = recipe.getRecipeId();
			if (recipeId != 0) {
				recipeStatement.setLong(11, recipeId);
				recipeStatement.setString(1, recipe.getTitle());
				recipeStatement.setString(2, recipe.getDescription());
				recipeStatement.setString(3, recipe.getCategory());
				recipeStatement.setString(4, recipe.getSubCategory());
				recipeStatement.setString(5, recipe.getPrimaryIngredient());
				recipeStatement.setString(6, recipe.getWebURL());
				recipeStatement.setString(7, recipe.getImageURL());
				recipeStatement.setString(8, recipe.getInstructions());
				recipeStatement.setLong(9, recipe.getYieldNumber());
				recipeStatement.setString(10, recipe.getYieldUnit());
				row = recipeStatement.executeUpdate();
				result.put("Recipe update operation for id: " + recipeId, "success");
			} else {
				result.put("Recipe update operation for id: " + recipeId, "Mising recipe ID");
			}
		} catch (SQLException e) {
			result.put("Recipe update operation", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			result.put("Recipe update operation", e.getMessage());
			e.printStackTrace();
		}
		return row;
	}

	// update poster in database--table poster
	public static int updatePoster(Poster poster, Connection connection, HashMap<String, Object> result) {
		int row = 0;
		try {
			long posterId = poster.getUserId();
			PreparedStatement posterStatement = null;
			if (posterId != 0) {
				posterStatement = connection.prepareStatement(CommandConstants.UPDATE_POSTER_SQL);
				posterStatement.setLong(2, posterId);
				posterStatement.setString(1, poster.getUserName());
				row = posterStatement.executeUpdate();
				result.put("Poster update operation for id: " + posterId, "success");
			} else {
				result.put("Poster update operation for id: " + posterId, "Mising user ID");
			}
		} catch (SQLException e) {
			result.put("Poster update operation failed", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			result.put("Poster update operation failed", e.getMessage());
			e.printStackTrace();
		}
		return row;
	}

	// update ingredient in database--table ingredient
	public static int updateIngredient(Ingredient ingredient, Connection connection, HashMap<String, Object> result) {
		int row = 0;
		try {
			long ingredientId = ingredient.getIngredientId();
			PreparedStatement posterStatement = null;
			if (ingredientId != 0) {
				posterStatement = connection.prepareStatement(CommandConstants.UPDATE_INGREDIENT_SQL);
				posterStatement.setLong(4, ingredientId);
				posterStatement.setString(1, ingredient.getName());
				posterStatement.setInt(2, ingredient.getAmount());
				posterStatement.setString(3, ingredient.getUnit());
				row = posterStatement.executeUpdate();
				result.put("Ingredient update operation for id: " + ingredientId, "success");
			} else {
				result.put("Ingredient update operation for id: " + ingredientId, "Missing ingredient ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result.put("Ingredient operation failed", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("Ingredient operation failed", e.getMessage());
		}
		return row;
	}

	// update ingredient in database--table ingredient
	public static int updateAssociation(long recipeId, long userId, long ingredientId, Connection connection,
			HashMap<String, Object> result) {
		int row = 0;
		try {
			PreparedStatement associationStatement = null;
			if (recipeId != 0) {
				associationStatement = connection.prepareStatement(CommandConstants.UPDATE_ASSOCIATION_SQL);
				associationStatement.setLong(3, recipeId);
				associationStatement.setLong(1, userId);
				associationStatement.setLong(2, ingredientId);
				row = associationStatement.executeUpdate();
				result.put("Association update operation for id: " + recipeId, "success");
			}
		} catch (SQLException e) {
			result.put("Association update operation failed", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			result.put("Association update operation failed", e.getMessage());
			e.printStackTrace();
		}
		return row;
	}

// ******************************************************************************************************************
	// REGION: DELETE RECIPE AND POPULATE OTHER TABLES
	public static String deleteRecipe(long recipeId) {
		String result = "";
		try {
			connection = ConnectionProvider.getConnection();
			PreparedStatement statement = connection.prepareStatement(CommandConstants.DELETE_RECIPE_SQL);
			statement.setLong(1, recipeId);
			statement.executeUpdate();
			result = "Recipe: " + recipeId + " has been sucessfully deleted.\n";
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

// ******************************************************************************************************************
	// REGION: SEARCH RECIPE
	public static ArrayList<Recipe> searchRecipe(String searchParam) {
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		HashSet<Long> recipeIds = null;
		Set<Long> intersect = null;
		String[] params = searchParam.split(" ");
		if (params.length > 0) {
			recipeIds = searchRecipeIds(params[0]);
			intersect = new TreeSet<Long>(recipeIds);
			for (String param : params) {
				intersect.retainAll(searchRecipeIds(param));
			}
		}

		ArrayList<Long> idList = new ArrayList<Long>(intersect);
		Collections.sort(idList);

		for (Long id : idList) {
			Recipe recipe = null;
			try {
				recipe = getRecipe(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (recipe != null)
				recipes.add(recipe);
		}
		return recipes;
	}

	// search database and get a list of recipe ids
	public static HashSet<Long> searchRecipeIds(String param) {
		HashSet<Long> recipeIds = new HashSet<Long>();
		try {
			connection = ConnectionProvider.getConnection();
			PreparedStatement statement = connection.prepareStatement(CommandConstants.SEARCH_RECIPE_ID_SQL);
			try {
				long paramNum = Long.parseLong(param);
				statement.setLong(1, paramNum);
				statement.setInt(10, (int) paramNum);
			} catch (Exception e) {
				statement.setLong(1, 0);
				statement.setInt(10, 0);
			}
			param = "%" + param + "%";
			statement.setString(2, param);
			statement.setString(3, param);
			statement.setString(4, param);
			statement.setString(5, param);
			statement.setString(6, param);
			statement.setString(7, param);
			statement.setString(8, param);
			statement.setString(9, param);
			statement.setString(11, param);

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				recipeIds.add(rs.getLong("recipe_id"));
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return recipeIds;
	}

// ******************************************************************************************************************
	// REGION: GET RECIPE BY USER_ID
	public static ArrayList<Recipe> getRecipesByUser(long userId) {
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		Set<Long> recipeIds = new HashSet<Long>();

		try {
			connection = ConnectionProvider.getConnection();
			PreparedStatement statement = connection.prepareStatement(CommandConstants.GET_RECIPES_BY_UESER_SQL);
			statement.setLong(1, userId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				recipeIds.add(rs.getLong("recipe_id"));
			}
			for (Long id : recipeIds) {
				Recipe recipe = null;
				try {
					recipe = getRecipe(id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (recipe != null)
					recipes.add(recipe);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return recipes;
	}
}
