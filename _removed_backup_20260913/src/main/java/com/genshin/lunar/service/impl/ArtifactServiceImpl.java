package com.genshin.lunar.service.impl;

import com.genshin.lunar.entity.Artifact;
import com.genshin.lunar.entity.ArtifactQuery;
import com.genshin.lunar.mapper.ArtifactMapper;
import com.genshin.lunar.service.ArtifactService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtifactServiceImpl implements ArtifactService {
    private final ArtifactMapper artifactMapper;

    public ArtifactServiceImpl(ArtifactMapper artifactMapper) {
        this.artifactMapper = artifactMapper;
    }

    @Override
    public List<Artifact> searchArtifact(ArtifactQuery query) {
        return artifactMapper.searchArtifact(query);
    }
}
