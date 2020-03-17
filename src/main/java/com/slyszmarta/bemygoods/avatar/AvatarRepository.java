package com.slyszmarta.bemygoods.avatar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, String> {

    void deleteAvatarByUser(Long id);

    Avatar findAvatarByUser(Long id);

}
