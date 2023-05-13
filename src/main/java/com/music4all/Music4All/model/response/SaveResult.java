package com.music4all.Music4All.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveResult {
    private boolean error;
    private String filename;
    private String link;

    private Long idBand;
}
