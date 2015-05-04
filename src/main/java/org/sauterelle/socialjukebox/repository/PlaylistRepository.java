package org.sauterelle.socialjukebox.repository;

import org.sauterelle.socialjukebox.domain.Playlist;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Playlist entity.
 */
public interface PlaylistRepository extends JpaRepository<Playlist,Long> {

    @Query("select playlist from Playlist playlist left join fetch playlist.songs left join fetch playlist.host where playlist.id =:id")
    Playlist findOneWithEagerRelationships(@Param("id") Long id);

}
