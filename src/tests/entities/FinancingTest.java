package tests.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import entities.Financing;
import tests.factory.FinancingFactory;

public class FinancingTest {

	@Test
	public void constructor_Should_createCorrectObject_When_validData() {
		Financing financing = FinancingFactory.newValidFinancing();
		assertEquals(100000.0, financing.getTotalAmount());
		assertEquals(2000.0, financing.getIncome());
		assertEquals(80,financing.getMonths());
	}

	@Test
	public void constructor_Should_throwsIllegalArgumentException_When_invalidData() {
		assertThrows(IllegalArgumentException.class, () -> FinancingFactory.invalidFinancing());
	}

	@Test
	public void setTotalAmount_Should_setValues_When_validData() {
		Financing financing = FinancingFactory.newValidFinancing();
		financing.setTotalAmount(90000.0);
		assertEquals(90000.0, financing.getTotalAmount());
	}

	@Test
	public void setTotalAmount_Should_throwsIllegalArgumentException_When_invalidData() {
		assertThrows(IllegalArgumentException.class, () -> {
			Financing financing = FinancingFactory.newValidFinancing();
			financing.setTotalAmount(110000.0); //erro pela regra de negocio, no metodo da entidade
		});
	}
	
	@Test
	public void setTotalIncome_Should_setValues_When_validData() {
		Financing financing = FinancingFactory.newValidFinancing();
		financing.setIncome(2500.0);
		assertEquals(2500.0, financing.getIncome()); 
	}
	
	@Test
	public void setTotalIncome_Should_throwsIllegalArgumentException_When_invalidData() {
		assertThrows(IllegalArgumentException.class, () ->{
			Financing financing = FinancingFactory.newValidFinancing();
			financing.setIncome(1500.0); //erro pela regra de negocio, no metodo da entidade
		});
	}
	
	@Test
	public void setMonths_Should_setValues_When_validData() {
		Financing financing = FinancingFactory.newValidFinancing();
		financing.setMonths(100);
		assertEquals(100, financing.getMonths()); 
	}
	
	@Test
	public void setMonths_Should_throwsIllegalArgumentException_When_invalidData() {
		assertThrows(IllegalArgumentException.class, () ->{
			Financing financing = FinancingFactory.newValidFinancing();
			financing.setMonths(70); //erro pela regra de negocio, no metodo da entidade
		});
	}
	
	@Test
	public void entry_Should_calculateCorrectValue() {
		Financing financing = FinancingFactory.newValidFinancing();
		double result = financing.entry();
		assertEquals(result, 20000.0);
	}
	
	@Test
	public void quota_Should_calculateCorrectValue() {
		Financing financing = FinancingFactory.newValidFinancing();
		double result = financing.quota();
		assertEquals(1000.0, result);
	}
	
}
