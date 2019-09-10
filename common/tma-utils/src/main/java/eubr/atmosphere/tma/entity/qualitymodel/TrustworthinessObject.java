package eubr.atmosphere.tma.entity.qualitymodel;

public abstract class TrustworthinessObject {

	private double score;
	
	private double threshold;

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}
	
}
