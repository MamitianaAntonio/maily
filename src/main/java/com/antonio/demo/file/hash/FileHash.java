package com.antonio.demo.file.hash;

import com.antonio.demo.PojaGenerated;

@PojaGenerated
public record FileHash(FileHashAlgorithm algorithm, String value) {}
