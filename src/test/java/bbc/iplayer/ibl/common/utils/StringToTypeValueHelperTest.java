package bbc.iplayer.ibl.common.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringToTypeValueHelperTest {

	private enum TestEnum {
		ENUM_VALUE1,
		ENUM_VALUE2
	}
	
	@Test
	public void testGetAsEnumType() throws Exception {
		String enumStringValue = TestEnum.ENUM_VALUE1.name();
		Enum<?> enumValueObject = (Enum<?>) StringToTypedValueHelper.getAsEnumType(enumStringValue, TestEnum.class);
		assertEquals(TestEnum.ENUM_VALUE1, enumValueObject);
	}
	
	@Test
	public void givenBoolean_testGetAsType() throws Exception {
		String booleanStringValue = "true";
		Boolean booleanValueObject = (Boolean) StringToTypedValueHelper.getAsType(booleanStringValue, Boolean.class);
		boolean booleanValuePrimitive = (Boolean) StringToTypedValueHelper.getAsType(booleanStringValue, boolean.class);
		assertEquals(Boolean.TRUE, booleanValueObject);
		assertEquals(true, booleanValuePrimitive);
	}
	
	@Test
	public void givenInteger_testGetAsType() throws Exception {
		String integerStringValue = "1";
		Integer integerValueObject = (Integer) StringToTypedValueHelper.getAsType(integerStringValue, Integer.class);
		int integerValuePrimitive = (Integer) StringToTypedValueHelper.getAsType(integerStringValue, int.class);
		assertEquals(new Integer(integerStringValue), integerValueObject);
		assertEquals(1, integerValuePrimitive);
	}
	
	@Test
	public void givenShort_testGetAsType() throws Exception {
		String shortStringValue = "1";
		Short shortValueObject = (Short) StringToTypedValueHelper.getAsType(shortStringValue, Short.class);
		short shortValuePrimitive = (Short) StringToTypedValueHelper.getAsType(shortStringValue, short.class);
		assertEquals(new Short(shortStringValue), shortValueObject);
		assertEquals(1, shortValuePrimitive);
	}	
	
	@Test
	public void givenLong_testGetAsType() throws Exception {
		String longStringValue = "1";
		Long longValueObject = (Long) StringToTypedValueHelper.getAsType(longStringValue, Long.class);
		long longValuePrimitive = (Long) StringToTypedValueHelper.getAsType(longStringValue, long.class);
		assertEquals(new Long(longStringValue), longValueObject);
		assertEquals(1, longValuePrimitive);
	}	
	
	@Test
	public void givenFloat_testGetAsType() throws Exception {
		String floatStringValue = "1.5";
		Float floatValueObject = (Float) StringToTypedValueHelper.getAsType(floatStringValue, Float.class);
		float floatValuePrimitive = (Float) StringToTypedValueHelper.getAsType(floatStringValue, float.class);
		assertEquals(new Float(floatStringValue), floatValueObject);
		assertEquals(1.5, floatValuePrimitive, 0.0);
	}	
	
	@Test
	public void givenDouble_testGetAsType() throws Exception {
		String doubleStringValue = "1.5";
		Double doubleValueObject = (Double) StringToTypedValueHelper.getAsType(doubleStringValue, Double.class);
		double doubleValuePrimitive = (Double) StringToTypedValueHelper.getAsType(doubleStringValue, double.class);
		assertEquals(new Double(doubleStringValue), doubleValueObject);
		assertEquals(1.5, doubleValuePrimitive, 0.0);
	}
	
	@Test
	public void givenString_testGetAsType() throws Exception {
		String aStringValue = "1.5";
		String aStringValueObject = (String) StringToTypedValueHelper.getAsType(aStringValue, String.class);
		assertEquals(aStringValue, aStringValueObject);
	}	
	
	@Test(expected = Exception.class) 
	public void givenInvalidInteger_throwsException() throws Exception {
		String invalidIntegerStringValue = "1S";
		StringToTypedValueHelper.getAsType(invalidIntegerStringValue, Integer.class);
	}
}
