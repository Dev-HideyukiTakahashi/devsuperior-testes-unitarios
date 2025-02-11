package entities;

public class Financing {

	private double totalAmount;
	private double income;
	private int months;

	public Financing() {
	}

	public Financing(double totalAmount, double income, int months) {
		validateFinancing(totalAmount, income, months);
		this.totalAmount = totalAmount;
		this.income = income;
		this.months = months;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		validateFinancing(totalAmount, income, months);
		this.totalAmount = totalAmount;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		validateFinancing(totalAmount, income, months);
		this.income = income;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		validateFinancing(totalAmount, income, months);
		this.months = months;
	}
	
	public double entry() {
		return totalAmount * 0.2;
	}
	
	public double quota() {
		return ((totalAmount - entry()) / months);
	}
	
	private void validateFinancing(Double totalAmount, double income, Integer months) {
		if(totalAmount * 0.8 / months > income / 2.0) {
			throw new IllegalArgumentException();
		}
	}
	
	

}
