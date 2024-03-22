package math;

public class SpecialFunctions{
	static boolean precomputeLogFactorial = false;
	static double[] precomputedLogFactorial;

	static public void precomputeLogFactorial(int maxN){
		precomputedLogFactorial = new double[maxN+1];
		for(int i = 0; i<maxN+1; ++i){
			precomputedLogFactorial[i] = logFactorial(i);
		}
		precomputeLogFactorial = true;
	}

	private static final double[] cof = {
			57.1562356658629235,
			-59.5979603554754912,
			14.1360979747417471,
			-0.491913816097620199,
			.339946499848118887e-4,
			.465236289270485756e-4,
			-.983744753048795646e-4,
			.158088703224912494e-3,
			-.210264441724104883e-3,
			.217439618115212643e-3,
			-.164318106536763890e-3,
			.844182239838527433e-4,
			-.261908384015814087e-4,
			.368991826595316234e-5
	};

	public static double logGamma(double d){
		if(d<=0){ throw new IllegalArgumentException("Bad argument in logGamma() d="+d); }
		double y = d;
		double x = d;
		double tmp = x+5.24218750000000000;
		tmp = (x+0.5)*Math.log(tmp)-tmp;
		double ser = 0.999999999999997092;
		for(int j = 0; j<14; j++)
			ser += cof[j]/++y;
		return tmp+Math.log(2.5066282746310005*ser/x);
	}
	public static double logFactorial(int d){
		if(precomputeLogFactorial&&precomputedLogFactorial.length>d){ return precomputedLogFactorial[d]; }
		return logGamma(d+1);
	}
	public static double logFactorial(long d){
		return logGamma(d+1);
	}
	public static double logCombination(int N, int n){
		if(n<0||N<n) return Double.NEGATIVE_INFINITY;
		return logFactorial(N)-logFactorial(n)-logFactorial(N-n);
	}
	public static double logCombination(long N, long n){
		if(n<0||N<n) return Double.NEGATIVE_INFINITY;
		return logFactorial(N)-logFactorial(n)-logFactorial(N-n);
	}
	public static double logBinomial(double p, int N, int n){
		double logp = logCombination(N, n);
		logp += n*Math.log(p);
		logp += (N-n)*Math.log(1-p);
		return logp;
	}
	public static double logBinomial(double p, long N, long n){
		double logp = logCombination(N, n);
		logp += n*Math.log(p);
		logp += (N-n)*Math.log(1-p);
		return logp;
	}
	public static double logHypergeometric(int n, int m, int N, int i){
		double logp = 0;
		logp += logCombination(n, i);
		logp += logCombination(m, N-i);
		logp -= logCombination(n+m, N);
		return logp;
	}
	public static double logHypergeometric(long n, long m, long N, long i){
		double logp = 0;
		logp += logCombination(n, i);
		logp += logCombination(m, N-i);
		logp -= logCombination(n+m, N);
		return logp;
	}
	public static double logHypergeometricUpperProbability(int n, int m, int N, int i){
		LogSumExpBinary logsump = new LogSumExpBinary();
		double constant = logCombination(n+m, N);
		for(int j = i; j<=n; ++j){
			double logp = 0;
			logp += logCombination(n, j);
			logp += logCombination(m, N-j);
			logp -= constant;
			logsump.add(logp);
		}
		return logsump.getTotal();
	}
	public static double logHypergeometricUpperProbability(long n, long m, long N, long i){
		LogSumExpBinary logsump = new LogSumExpBinary();
		double constant = logCombination(n+m, N);
		for(long j = i; j<=n; ++j){
			double logp = 0;
			logp += logCombination(n, j);
			logp += logCombination(m, N-j);
			logp -= constant;
			logsump.add(logp);
		}
		return logsump.getTotal();
	}
	public static double logHypergeometricLowerProbability(int n, int m, int N, int i){
		LogSumExpBinary logsump = new LogSumExpBinary();
		double constant = logCombination(n+m, N);
		for(int j = 0; j<=i; ++j){
			double logp = 0;
			logp += logCombination(n, j);
			logp += logCombination(m, N-j);
			logp -= constant;
			logsump.add(logp);
		}
		return logsump.getTotal();
	}
	public static double logHypergeometricLowerProbability(long n, long m, long N, long i){
		LogSumExpBinary logsump = new LogSumExpBinary();
		double constant = logCombination(n+m, N);
		for(long j = 0; j<=i; ++j){
			double logp = 0;
			logp += logCombination(n, j);
			logp += logCombination(m, N-j);
			logp -= constant;
			logsump.add(logp);
		}
		return logsump.getTotal();
	}
}
