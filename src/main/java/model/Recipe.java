package model;

import java.util.ArrayList;
import java.util.Comparator;


public class Recipe {
	private long recipeId;
	private String title;
	private String description;
	private String category;
	private String subCategory;
	private String primaryIngredient;
	private String webURL;
	private String imageURL;
	private String instructions;
	private int yieldNumber;
	private String yieldUnit;

	private ArrayList<Ingredient> ingredients;
	private Poster poster;

	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getPrimaryIngredient() {
		return primaryIngredient;
	}

	public void setPrimaryIngredient(String primaryIngredient) {
		this.primaryIngredient = primaryIngredient;
	}

	public String getWebURL() {
		return webURL;
	}

	public void setWebURL(String webURL) {
		this.webURL = webURL;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public int getYieldNumber() {
		return yieldNumber;
	}

	public void setYieldNumber(int yieldNumber) {
		this.yieldNumber = yieldNumber;
	}

	public String getYieldUnit() {
		return yieldUnit;
	}

	public void setYieldUnit(String yieldUnit) {
		this.yieldUnit = yieldUnit;
	}

	public Poster getPoster() {
		return poster;
	}

	public void setPoster(Poster poster) {
		this.poster = poster;
	}

	public long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(long recipeId) {
		this.recipeId = recipeId;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (int) recipeId;
		return hash;
	}
}
