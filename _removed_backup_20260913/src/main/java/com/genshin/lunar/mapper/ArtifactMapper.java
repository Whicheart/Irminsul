package com.genshin.lunar.mapper;

import com.genshin.lunar.entity.Artifact;
import com.genshin.lunar.entity.ArtifactQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArtifactMapper {
    List<Artifact> searchArtifact(ArtifactQuery query);
}
