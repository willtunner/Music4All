package com.music4all.Music4All.dtos;

import com.music4all.Music4All.model.Music;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MusicMapper {

        MusicMapper INSTANCE = Mappers.getMapper(MusicMapper.class);

        @Mapping(target = "membersByMusic", ignore = true)
        Music toMusic(MusicDTO musicDTO);

        MusicDTO toMusicDTO(Music music);

}
