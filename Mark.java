package bowling;
import java.util.HashMap;
import java.util.Map;

public enum Mark {
	
ZERO("-"), ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), SPARE("/"), STRIKE("X"), EMPTY(" ");

private final String stringRepresentation;
Mark(String stringRepresentation){
	this.stringRepresentation = stringRepresentation;
}
private static Map<Integer,Mark> integerToMarkMap;
static
{
integerToMarkMap = new HashMap<Integer, Mark>();
integerToMarkMap.put(0, ZERO);
integerToMarkMap.put(1, ONE);
integerToMarkMap.put(2, TWO);
integerToMarkMap.put(3, THREE);
integerToMarkMap.put(4, FOUR);
integerToMarkMap.put(5, FIVE);
integerToMarkMap.put(6, SIX);
integerToMarkMap.put(7, SEVEN);
integerToMarkMap.put(8, EIGHT);
integerToMarkMap.put(9, NINE);
}
//part of pre: 0<= pinCount <= 9
//part of post: rv != null
public static Mark translate(int pinCount)
{
	assert integerToMarkMap.containsKey(pinCount): "pinCount not in the set integerToMarkMap.keySet() = "
+ integerToMarkMap.keySet() + " !: pinCount = " + pinCount;
	return integerToMarkMap.get(pinCount);
}
public String toString()
{
	return stringRepresentation;
}
}
