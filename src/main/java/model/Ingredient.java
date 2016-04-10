package model;

public class Ingredient {
	private long ingredient_id;
	private String name;
	private int amount;
	private String unit;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public long getIngredient_id() {
		return ingredient_id;
	}
	public void setIngredient_id(long ingredient_id) {
		this.ingredient_id = ingredient_id;
	}
	
}
