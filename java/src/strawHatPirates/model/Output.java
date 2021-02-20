package strawHatPirates.model;

import java.util.ArrayList;
import java.util.List;

public class Output {
	int numberOfDeliveries;
	List<Delivery> deliveries = new ArrayList<>();

	public int getNumberOfDeliveries() {
		return numberOfDeliveries;
	}

	public void setNumberOfDeliveries(int numberOfDeliveries) {
		this.numberOfDeliveries = numberOfDeliveries;
	}

	public List<Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(List<Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	@Override
	public String toString() {
		return "Output [numberOfDeliveries=" + numberOfDeliveries + ", deliveries=" + deliveries + "]";
	}
	

}
