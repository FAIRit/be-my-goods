package com.slyszmarta.bemygoods.avatar;

import com.slyszmarta.bemygoods.exceptions.AvatarNotFoundException;
import com.slyszmarta.bemygoods.exceptions.AvatarStorageException;
import com.slyszmarta.bemygoods.user.ApplicationUser;
import com.slyszmarta.bemygoods.user.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AvatarService {

    private AvatarRepository avatarRepository;
    private ApplicationUserRepository userRepository;

    public Avatar storeFile(MultipartFile file, ApplicationUser user) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if(fileName.contains("..")) {
                throw new AvatarStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            if(!isSupportedFiletype(file.getContentType())){
                throw new AvatarStorageException("Unsupported type of file.");
            }
            Avatar avatar = Avatar.builder()
                    .fileType(file.getContentType())
                    .data(file.getBytes())
                    .user(user)
                    .build();
            user.setAvatar(avatar);
            userRepository.save(user);
            return avatarRepository.save(avatar);
        } catch (IOException ex) {
            throw new AvatarStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Avatar getFile(Long userId) {
        return avatarRepository.findAvatarByUser(userId);
    }

    public void deleteUserAvatar(long userId){
        avatarRepository.deleteAvatarByUser(userId);
    }

    public boolean isSupportedFiletype(String fileType){
        return nonNull(fileType) && (fileType.equals(MimeTypeUtils.IMAGE_JPEG_VALUE) || fileType.equals(MimeTypeUtils.IMAGE_PNG_VALUE));
    }
}
