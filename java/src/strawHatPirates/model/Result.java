package strawHatPirates.model;

public class Result {

	Delivery del;
	int score;

	public Result(int pizzasReqd) {
		del = new Delivery(pizzasReqd);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Delivery getDel() {
		return del;
	}

	public void setDel(Delivery del) {
		this.del = del;
	}

}
