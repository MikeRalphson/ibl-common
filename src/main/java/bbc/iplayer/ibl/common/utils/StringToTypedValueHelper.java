package bbc.iplayer.ibl.common.utils;

/**
 * <p>
 * 	Simple (final) class that provides helpful static methods to:
 * 	<ul>
 * 		<li>convert a String value to an Enumeration</li>
 * 		<li>convert a String value to a specific type</li>
 * 	</ul>
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 */
public final class StringToTypedValueHelper {
	
	public final static <E extends Enum<E>> Object getAsEnumType(String valueAsString, Class<E> type) throws Exception {
		Object result = null;

		if ((valueAsString != null) && (!valueAsString.isEmpty()) && (type != null)) {
			result = Enum.valueOf(type, valueAsString);
		}

		return result;
	}
	
	public final static Object getAsType(String valueAsString, Class<?> type) throws Exception {
		Object result = null;

		if ((valueAsString != null) && (!valueAsString.isEmpty()) && (type != null))	{
			if (type.equals(boolean.class) || type.equals(Boolean.class)) {
				if (valueAsString.equalsIgnoreCase("yes") ||
					valueAsString.equalsIgnoreCase("y") ||
					valueAsString.equalsIgnoreCase("true")) {
					result = Boolean.TRUE;
				}
				else {
					result = Boolean.FALSE;
				}
			}				
			else if (type.equals(int.class) || type.equals(Integer.class)) {
				result = Integer.valueOf(valueAsString);
			}
			else if (type.equals(short.class) || type.equals(Short.class)) {
				result = Short.valueOf(valueAsString);
			}				
			else if (type.equals(long.class) || type.equals(Long.class)) {
				result = Long.valueOf(valueAsString);
			}
			else if (type.equals(float.class) || type.equals(Float.class)) {
				result = Float.valueOf(valueAsString);
			}
			else if (type.equals(double.class) || type.equals(Double.class)) {
				result = Double.valueOf(valueAsString);
			}
			else if (type.equals(String.class))	{
				result = valueAsString;
			}
		}
		
		return result;
	}
}
