package com.genshin.lunar.service;

import com.genshin.lunar.entity.Artifact;
import com.genshin.lunar.entity.ArtifactQuery;

import java.util.List;

public interface ArtifactService {
    List<Artifact> searchArtifact(ArtifactQuery query);
}
