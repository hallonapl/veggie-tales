package se.hallonapl.veggietales.model;

public class Vegetable {
	
	public Vegetable() {}
	public Vegetable(int id, double price, String name) {
		this.id = id;
		this.price = price;
		this.name = name;
		}
	
	private int id;
	private double price;
	private String name;
	
	public int getId() {return id;}
	public double getPrice() {return price;}
	public String getName() {return name;}
	public void setId(int id) {this.id = id;}
	public void setPrice(double price) {this.price = price;}
	public void setName(String name) {this.name = name;}
	
	public String toXML() {
		StringBuilder xmlstrb = new StringBuilder();
		xmlstrb.append("<Vegetable>");
		xmlstrb.append("<ID>");
		xmlstrb.append(String.valueOf(this.id));
		xmlstrb.append("</ID>");
		xmlstrb.append("<Price>");
		xmlstrb.append(String.valueOf(this.price));
		xmlstrb.append("</Price>");
		xmlstrb.append("<Name>");
		xmlstrb.append(String.valueOf(this.name));
		xmlstrb.append("</Name>");
		xmlstrb.append("</Vegetable>");
		return xmlstrb.toString();
		}
	
	}

