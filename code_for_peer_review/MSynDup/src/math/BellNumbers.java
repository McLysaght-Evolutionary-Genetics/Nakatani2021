package math;

public class BellNumbers{

	int n_;
	double[] stirlingSetNumbers_;

	public BellNumbers(int n){
		n_ = n;
		stirlingSetNumbers_ = new double[n];
		this.computeStirlingSetNumbers();
	}
	private void computeStirlingSetNumbers(){
		this.stirlingSetNumbers_[0] = 1;
		for(int j = 1; j<n_; ++j)
			this.stirlingSetNumbers_[j] = 0;
		for(int i = 2; i<=n_; ++i){
			double sprev = 1;
			for(int j = 1; j<i; ++j){
				int k = j+1;
				double snext = (j+1)*this.stirlingSetNumbers_[j]+sprev;
				sprev = this.stirlingSetNumbers_[j];
				this.stirlingSetNumbers_[j] = snext;
			}
		}
	}

	public double getStirlingSetNumber(int k){
		return this.stirlingSetNumbers_[k-1];
	}

	public double getBellNumber(){
		double bell = 0;
		for(int i = 0; i<n_; ++i){
			bell += this.stirlingSetNumbers_[i];
		}
		return bell;
	}
}
