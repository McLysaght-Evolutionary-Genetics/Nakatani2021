package math;

public class LogSumExpBinary{
	double value_;

	public LogSumExpBinary(){
		clear();
	}
	public void clear(){
		value_ = Double.NEGATIVE_INFINITY;// 最初の値は適当に入れるが、計算する際にはこの値を使ってしまわないように注意。
	}

	public double getTotal(){
		return this.value_;
	}
	public void add(double d){
		if(value_==Double.NEGATIVE_INFINITY){
			this.value_ = d;
			return;
		}
		this.value_ = logSumExp(this.value_, d);
	}
	static private double logSumExp(double a, double b){
		double max = (a>b) ? a : b;
		double C = max-700;
		double sum = Math.exp(a-C)+Math.exp(b-C);
		double result = Math.log(sum)+C;
		return result;
	}
}
