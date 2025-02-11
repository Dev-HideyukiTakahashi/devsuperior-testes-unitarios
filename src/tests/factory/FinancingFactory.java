package tests.factory;

import entities.Financing;

public class FinancingFactory {
	
	
	/* Metodo validateFinancing da entidade
	 * (totalAmount * 0.8 / months > income / 2.0)
	 *  (1000 > 1000) OK
	 */
	public static Financing newValidFinancing() {
		return new Financing(100000.0, 2000.0, 80);
	}
	
	/* Metodo validateFinancing da entidade
	 * (totalAmount * 0.8 / months > income / 2.0)
	 *  (4000 > 1000) throw new IllegalArgumentException()
	 */
	public static Financing invalidFinancing() {
		return new Financing(100000, 2000, 20);
	}

}
