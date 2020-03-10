package com.slyszmarta.bemygoods.album;

import com.slyszmarta.bemygoods.exceptions.AlbumNotFoundException;
import com.slyszmarta.bemygoods.user.ApplicationUser;
import com.slyszmarta.bemygoods.user.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ApplicationUserService userService;


    public Albums getAllUsersAlbums(final Long usersId) {
        return new Albums(albumRepository.findAllByUserId(usersId).stream()
                .map(AlbumMapper.INSTANCE::map)
                .collect(Collectors.toList()));
    }

    public AlbumDto getUsersAlbum(final Long usersId, final Long albumsId) throws AccessDeniedException {
        Album albumToFind = getExistingAlbumById(albumsId);
        if (albumToFind.getUser().getId().equals(usersId)) {
            return AlbumMapper.INSTANCE.map(albumToFind);
        }
        throw new AccessDeniedException("You don't have permission to access this content. Please log in.");
    }

    public Album saveAlbum(final AlbumDto dto, final Long usersId) {
        ApplicationUser user = userService.getExistingUser(usersId);
        Album albumToSave = AlbumMapper.INSTANCE.map(dto);
        albumToSave.setUser(user);
        albumRepository.save(albumToSave);
        return albumToSave;
    }


    public void deleteUsersAlbum(final Long usersId, final Long albumsId) throws AccessDeniedException {
        Album albumToFind = getExistingAlbumById(albumsId);
        if (albumToFind.getUser().getId().equals(usersId)) {
            albumRepository.deleteById(albumsId);
        }
        throw new AccessDeniedException("You don't have permission to access this content. Please log in.");
    }

    public void deleteAllUsersAlbum(final Long usersId) {
        albumRepository.deleteAllByUserId(usersId);
    }

    public Album getExistingAlbumById(final Long albumId) {
        return albumRepository.findById(albumId).orElseThrow(() -> new AlbumNotFoundException(albumId));
    }
}
