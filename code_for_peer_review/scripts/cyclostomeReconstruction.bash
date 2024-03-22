#!/bin/bash

JAVA_BIN_PATH='java'
JAVA_CLASS_PATH='../MSynDup/bin'

K=18
L=0.1

WORK_DIR='./cyclostome'
SUPP_DATA_DIR="../SuppData1"
SEG_DIR="${SUPP_DATA_DIR}/segments/seg"
PAR_DIR="${SUPP_DATA_DIR}/paralogs"
ORT_DIR="${SUPP_DATA_DIR}/orthologs"
INPUT_SEG_GROUP_FILE="${WORK_DIR}/psm_rec.txt"
INPUT_LINKED_SEG_FILE="${WORK_DIR}/linkedPetmaSegPairs.txt"
OUTPUT_FILE="${WORK_DIR}/psm_rec_FREE_FORM.txt"

${JAVA_BIN_PATH} \
-cp ${JAVA_CLASS_PATH} reconstruction.multispecies.MSynDup \
-s "Japanese Lamprey,${SEG_DIR}/LETJA.txt,${PAR_DIR}/LETJA-LETJA.txt" \
-s "Sea Lamprey,${SEG_DIR}/PETMA.txt,${PAR_DIR}/PETMA-PETMA.txt" \
-S "Japanese Lamprey,Sea Lamprey,${ORT_DIR}/LETJA-PETMA.txt" \
-g "${INPUT_SEG_GROUP_FILE}" \
-r "${INPUT_LINKED_SEG_FILE}" \
-o "${OUTPUT_FILE}" \
-T FREE_FORM \
-R true \
-I 18
