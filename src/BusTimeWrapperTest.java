import org.junit.Test;

public class BusTimeWrapperTest {
	@Test
	public void testgetBusString2(){
		
		String busTimes = LcdExample.getBusString2("490008254S");
		System.out.println(busTimes);
	}

}
