package chr;

import java.util.Iterator;
import math.SpecialFunctions;
import data.ConservedBlockList;
import data.OhnologList;

public class OhnologDistributionTable{
	static boolean DEBUG = false;
	static boolean NO_INTRA_SEG_OHNOLOGS = Boolean.getBoolean("NO_INTRA_SEG_OHNOLOGS");

	ConservedBlockList blocks_;

	// block statistics
	// int[] blockSize_;
	int[] interBlockOhnologs_;
	int allGenePairs_ = 0;
	int allDuplications_ = 0;
	int[] block2duplicatePairs_;

	static public void setNoIntraSegOhnologs(boolean noIntraSegOhnologs){
		NO_INTRA_SEG_OHNOLOGS = noIntraSegOhnologs;
	}
	public OhnologDistributionTable(ConservedBlockList blocks, OhnologList ohnologs){
		blocks_ = blocks;
		block2duplicatePairs_ = new int[blocks_.getNumberOfBlocks()];
		setupOhnologTable(blocks, ohnologs);
	}

	private void setupOhnologTable(ConservedBlockList blocks, OhnologList ohnologs){
		int numberOfBlocks = blocks.getNumberOfBlocks();
		int arraySize = 1+this.getMaxBlockPairIndex(numberOfBlocks);
		this.interBlockOhnologs_ = new int[arraySize];
		this.allGenePairs_ = (blocks.getTotalNumberOfGenes()*(blocks.getTotalNumberOfGenes()-1))/2;
		for(Iterator<String> i = ohnologs.getAllOhnologs().iterator(); i.hasNext();){
			String gene1 = i.next();
			String block1 = blocks.getBlockID(gene1);
			if(block1==null) continue;// no such ohnolog gene in the conserved blocks
			for(Iterator<String> j = ohnologs.getOhnologs(gene1).iterator(); j.hasNext();){
				String gene2 = j.next();
				String block2 = blocks.getBlockID(gene2);
				if(block2==null) continue;// no such ohnolog gene in the conserved blocks
				if(NO_INTRA_SEG_OHNOLOGS&&block1.equals(block2)) continue;
				this.incrementOhnologs(block1, block2);
				++this.allDuplications_;
			}
		}
		this.allDuplications_ /= 2;
		for(int i = 0; i<this.interBlockOhnologs_.length; ++i){
			this.interBlockOhnologs_[i] /= 2;
		}
		int i = 0;
		for(String block1: blocks_.getBlockNameList()){
			for(String block2: blocks_.getBlockNameList()){
				if(block1.equals(block2)){
					continue;
				}
				block2duplicatePairs_[i] += this.getOhnologs(block1, block2);
			}
			++i;
		}
	}
	public int getBlockSize(String blockName){
		return this.blocks_.getNumberOfGenes(blockName);
	}
	private int getMaxBlockPairIndex(int numberOfBlocks){
		int maxBlockID = numberOfBlocks-1;
		int lastIndex = (maxBlockID*(maxBlockID+1))/2+maxBlockID;
		return lastIndex;
	}
	private int getBlockPairIndex(String blockName1, String blockName2){
		int block1 = blocks_.getBlockIndex(blockName1).intValue();
		int block2 = blocks_.getBlockIndex(blockName2).intValue();
		int min = block1;
		int max = block2;
		if(min>max){
			min = block2;
			max = block1;
		}
		return (max*(max+1))/2+min;
	}
	private void incrementOhnologs(String block1, String block2){
		++this.interBlockOhnologs_[this.getBlockPairIndex(block1, block2)];
	}
	public int getOhnologs(String block1, String block2){
		return this.interBlockOhnologs_[this.getBlockPairIndex(block1, block2)];
	}
	public int getOhnologs(String blockName){
		Integer blockIndex = blocks_.getBlockIndex(blockName);
		if(blockIndex==null){
			System.err.println("Error: unknown block name="+blockName);
		}
		int block = blockIndex.intValue();
		return this.block2duplicatePairs_[block];
	}
	public int getGenePairs(String blockName1, String blockName2){
		if(blockName1.equals(blockName2)){
			return (this.getBlockSize(blockName1)*(this.getBlockSize(blockName1)-1))/2;
		}else{
			return this.getBlockSize(blockName1)*this.getBlockSize(blockName2);
		}
	}
	public int getTotalDuplicates(){
		return this.allDuplications_;
	}
	public int getTotalGenePairs(){
		return this.allGenePairs_;
	}

	// significance calculation
	public double computeSignificanceOfParalogon(String block1, String block2){
		int n = this.allDuplications_;
		int m = this.allGenePairs_-this.allDuplications_;
		int N = this.getGenePairs(block1, block2);
		int i = this.getOhnologs(block1, block2);
		if(i==0) return 1.0;
		double p = SpecialFunctions.logHypergeometricUpperProbability(n, m, N, i);
		p = Math.exp(p);
		return p;
	}

	// public int[] countDuplicationPairs(Reconstruction rec){
	// // intra-protochromosomeについて集計
	// int intraChromosomeDuplications = 0;
	// int intraChromosomeGenePairs = 0;
	// for(String[] chr: rec.getChromosomes()){
	// for(int i = 0; i<chr.length; ++i){
	// String block1 = chr[i];
	// // j=iの場合だけ先に計算する(ペア数の計算式が他の場合と異なるから)
	// intraChromosomeDuplications += this.getOhnologs(block1, block1);
	// intraChromosomeGenePairs += (this.getBlockSize(block1)*(this.getBlockSize(block1)-1))/2;
	// for(int j = i+1; j<chr.length; ++j){
	// String block2 = chr[j];
	// // chr[i]とchr[j]間について集計
	// intraChromosomeDuplications += this.getOhnologs(block1, block2);
	// intraChromosomeGenePairs += this.getBlockSize(block1)*this.getBlockSize(block2);
	// }
	// }
	// }
	// // inter-protochromosomeの計算
	// int interChromosomeDuplications = 0;
	// int interChromosomeGenePairs = 0;
	// for(int x = 0; x<rec.getChromosomeSize(); ++x){
	// String[] xchr = rec.getChromosome(x);
	// for(int y = x+1; y<rec.getChromosomeSize(); ++y){
	// String[] ychr = rec.getChromosome(y);
	// for(int xi = 0; xi<xchr.length; ++xi){
	// String xblock = xchr[xi];
	// for(int yi = 0; yi<ychr.length; ++yi){
	// String yblock = ychr[yi];
	// interChromosomeDuplications += this.getOhnologs(xblock, yblock);
	// interChromosomeGenePairs += this.getBlockSize(xblock)*this.getBlockSize(yblock);
	// }
	// }
	// }
	// }
	// int[] count = new int[4];
	// count[0] = intraChromosomeGenePairs;
	// count[1] = intraChromosomeDuplications;
	// count[2] = interChromosomeGenePairs;
	// count[3] = interChromosomeDuplications;
	// return count;
	// }
}
