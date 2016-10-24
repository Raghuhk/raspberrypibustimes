import java.util.TreeSet;

public class BusTimeWrapper implements Comparable<BusTimeWrapper> {
	String vehicleId;
	String routeName;
	TreeSet<Double> timeToStationTreeSet;
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	
	
	public TreeSet<Double> getTimeToStationTreeSet() {
		return timeToStationTreeSet;
	}
	public void setTimeToStationTreeSet(TreeSet<Double> timeToStationTreeSet) {
		this.timeToStationTreeSet = timeToStationTreeSet;
	}
	@Override
	public int compareTo(BusTimeWrapper bus2){
		Double timeToStation1=timeToStationTreeSet!=null?timeToStationTreeSet.first():null;
		Double timeToStation2=bus2.getTimeToStationTreeSet()!=null?bus2.getTimeToStationTreeSet().first():null;
		
		if(timeToStation2<timeToStation1){
			return 1;
		}
		else if (timeToStation1< timeToStation2){
			return -1;
		}
		else{
			return 0;
		}
	}
}
