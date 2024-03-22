The proto-vertebrate/-cyclostome/-gnathostome genomes were inferred by running the reconstruction programs in the following way.
$ cd scripts
$ ./vertebrateReconstruction.bash
$ ./cyclostomeReconstruction.bash
$ ./gnathostomeReconstruction.bash

The source files can be found in MSynRec/src and MSynDup/src, and an external library is included in the current package (i.e. apache.commons.math3 in MSynRec/lib). The programs/scripts were tested on Linux and Windows 10 with Java SE 8/11, and no installtion is required. The run time is within a minuite for each script on Windows 10 with Core i9-9900 CPU.

The input data files and output files are explained below.

vertebrateReconstruction.bash:
*** Input file format
The input file shows the macrosynteny between the amphioxus genome and two lamprey genome.
The amphioxus scaffolds are separated by blank lines, and each line shows an amphioxus gene.
If the amphioxus gene has co-orthologs in the lamprey genomes, their segment indexes are shown in the second (sea lamprey segments) and third (Japanese lamprey segments) columns.

e.g.
#comment
scafName1
gene1 tab petmaSegmentIndex1,petmaSegmentIndex2,petmaSegmentIndex3,petmaSegmentIndex4 tab letjaSegmentIndex1,letjaSegmentIndex2,letjaSegmentIndex3,letjaSegmentIndex4
gene2 tab petmaSegmentIndex1,petmaSegmentIndex2,petmaSegmentIndex3,petmaSegmentIndex4 tab letjaSegmentIndex1,letjaSegmentIndex2,letjaSegmentIndex3,letjaSegmentIndex4
...

scafName2
gene1 tab petmaSegmentIndex1,petmaSegmentIndex2,petmaSegmentIndex3,petmaSegmentIndex4 tab letjaSegmentIndex1,letjaSegmentIndex2,letjaSegmentIndex3,letjaSegmentIndex4
gene2 tab petmaSegmentIndex1,petmaSegmentIndex2,petmaSegmentIndex3,petmaSegmentIndex4 tab letjaSegmentIndex1,letjaSegmentIndex2,letjaSegmentIndex3,letjaSegmentIndex4
...



*** Output files
est_*.txt: These files show the estimated values of the latent variables and parameters in the probabilistic macrosynteny model. The symbols (i.e. V and W) used in the program and output files are different from the description in [Nakatani and McLysaght, Bioinformatics (2017)]. 
rec_maxProb.txt: Each non-WGD segments were assigned to the pre-WGD chromosome with the largest reconstruction probability (according to est_Usk.txt). Each line defines a pre-WGD chromosome by a list of non-WGD segments (or scaffolds). (The reconstructed chromosomes in SuppData1 were manually re-ordered.)



cyclostomeReconstruction.bash:
*** Input file format
psm_rec.txt: Each line shows a list of lamprey segments (paralogons) that were inferred to have originated from a single proto-vertebrate chromosome.
linkedPetmaSegPairs.txt: The list of sea lamprey segment pairs with evidence of linkage in the Pacific lamprey linkage map.

*** Output file format
Each line shows the proto-cyclostome chromosome represented by lamprey segments. (The reconstructed chromosomes in SuppData1 were manually re-ordered.)


gnathostomeReconstruction.bash:
*** Input file format
psm_rec.txt: Each line shows a list of gnathostome segments (or paralogons) that were inferred to have originated from a single proto-vertebrate chromosome (with no inter-chromosomal rearrangements between 1R and 2R or with fissions between 1R and 2R) or multiple proto-vertebrate chromosomes (due to fusions/translocations between 1R and 2R).

*** Output file format
Each line shows the proto-gnathostome chromosome represented by gnathostome segments. (The reconstructed chromosomes in SuppData1 were manually re-ordered.)

