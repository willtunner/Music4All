package com.music4all.Music4All.services;

import com.music4all.Music4All.model.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentServiceInterface {
    Attachment saveAttachement(MultipartFile file) throws Exception;

    Attachment getAttachment(Long fileId) throws Exception;
}
