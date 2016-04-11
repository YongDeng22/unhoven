package command;

public class CommandConstants {
	//---------------------------------------------------------------------	
	//recipe table
	public static final String CREATE_RECIPE_SQL = 
			"INSERT "
			+ "INTO recipe"
			+ "(recipe_id, title, description, category, subcategory, primary_ingredient, "
			+ "web_url, image_url, instructions, yield_number, yield_unit) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static final String GET_SINGLE_RECIPE_BY_ID_SQL = 
			"SELECT * "
			+ "FROM recipe "
			+ "LEFT OUTER JOIN associations ON recipe.recipe_id = associations.recipe_id "
			+ "LEFT OUTER JOIN ingredient ON associations.ingredient_id = ingredient.ingredient_id "
			+ "LEFT OUTER JOIN poster ON associations.user_id = poster.user_id "
			+ "WHERE recipe.recipe_id = ? "
			+ "ORDER BY recipe.recipe_id, ingredient.ingredient_id, poster.user_id";
	
	public static final String UPDATE_RECIPE_SQL = 
			"UPDATE "
			+ "recipe SET "
			+ "title = ?, "
			+ "description = ?, "
			+ "category = ?, "
			+ "subcategory = ?, "
			+ "primary_ingredient = ?, "
			+ "web_url = ?, "
			+ "image_url = ?, "
			+ "instructions = ?, "
			+ "yield_number = ?, "
			+ "yield_unit = ? "
			+ "WHERE recipe_id = ?";
	
	public static final String DELETE_RECIPE_SQL = 
			"DELETE "
			+ "FROM recipe WHERE recipe_id = ?";
	
	public static final String GET_RECIPES_BY_UESER_SQL = 
			"SELECT recipe.recipe_id "
			+ "FROM recipe INNER JOIN associations ON recipe.recipe_id = associations.recipe_id "
			+ "INNER JOIN ingredient ON associations.ingredient_id = ingredient.ingredient_id "
			+ "INNER JOIN poster ON associations.user_id = poster.user_id "
			+ "WHERE poster.user_id = ?";
		
	public static final String SEARCH_RECIPE_ID_SQL = 
			"SELECT recipe_id "
			+ "FROM recipe "
			+ "WHERE recipe_id = ? "
			+ "OR title ILIKE ? "
			+ "OR description ILIKE ? "
			+ "OR category ILIKE ? "
			+ "OR subcategory ILIKE ? "
			+ "OR primary_ingredient ILIKE ? "
			+ "OR web_url ILIKE ? "
			+ "OR image_url ILIKE ? "
			+ "OR instructions ILIKE ? "
			+ "OR yield_number = ? "
			+ "OR yield_unit ILIKE ? "
			+ "ORDER BY recipe_id";
	
	//---------------------------------------------------------------------	
	//ingredient table
	public static final String CREATE_INGREDIENT_SQL = 
			"INSERT INTO ingredient(ingredient_id, name, amount, unit) VALUES(?, ?, ?, ?)";
	
	public static final String GET_SINGLE_INGREDIENT_BY_ID_SQL = 
			"SELECT * FROM ingredient WHERE ingredient_id = ? ORDER BY ingredient_id";
	
	public static final String GET_INGREDIENTS_BY_RECIPE_ID_SQL = 
			"SELECT * "
			+ "FROM ingredient INNER JOIN associations ON ingredient.ingredient_id = association.ingredient_id "
			+ "WHERE associations.recipe_id = ? "
			+ "ORDER BY associations.recipe_id, associations.ingredient_id, associations.user_id";
	
	public static final String UPDATE_INGREDIENT_SQL = 
			"UPDATE ingredient "
			+ "SET name = ?, amount = ?, unit = ? "
			+ "WHERE ingredient_id = ?";
	
	public static final String DELETE_INGREDIENT_SQL = 
			"DELETE FROM ingredient "
			+ "WHERE ingredient_id = ?";
	
	//---------------------------------------------------------------------	
	//poster table
	public static final String CREATE_POSTER_SQL = 
			"INSERT INTO poster VALUES(?, ?)";
	
	public static final String GET_SINGLE_POSTER_BY_ID_SQL = 
			"SELECT * FROM poster WHERE user_id = ? ORDER BY user_id";
	
	public static final String GET_POSTERS_BY_RECIPE_ID_SQL = 
			"SELECT * "
			+ "FROM poster INNER JOIN associations ON poster.user_id = associations.user_id "
			+ "WHERE associations.recipe_id = ? "
			+ "ORDER BY poster.user_id";
	
	public static final String UPDATE_POSTER_SQL = 
			"UPDATE poster SET user_name = ? WHERE user_id = ?";
	
	public static final String DELETE_POSTER_SQL = 
			"DELETE FROM poster "
			+ "WHERE user_id = ?";
	
	
	//---------------------------------------------------------------------	
	//association table
	public static final String CREATE_ASSOCIATION_SQL = 
			"INSERT INTO associations(recipe_id, user_id, ingredient_id) VALUES(?, ?, ?)";
	
	public static final String UPDATE_ASSOCIATION_SQL = 
			"UPDATE associations SET user_id = ?, ingredient_id = ? WHERE recipe_id = ?";
	
	public static final String DELETE_ASSOCIATION_BY_RECIPE_ID_SQL =
			"DELETE FROM associations WHERE recipe_id = ?";
		
}
