package com.genshin.lunar.controller;

import com.genshin.lunar.entity.Artifact;
import com.genshin.lunar.entity.ArtifactQuery;
import com.genshin.lunar.service.ArtifactService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artifact")
public class ArtifactController {
    private final ArtifactService artifactService;

    public ArtifactController(ArtifactService artifactService) {
        this.artifactService = artifactService;
    }

    @GetMapping("/search")
    public List<Artifact> searchArtifact(ArtifactQuery query) {
        return artifactService.searchArtifact(query);
    }
}
