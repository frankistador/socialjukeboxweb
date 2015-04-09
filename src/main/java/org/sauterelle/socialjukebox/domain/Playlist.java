package org.sauterelle.socialjukebox.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Playlist.
 */
@Entity
@Table(name = "T_PLAYLIST")
public class Playlist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "T_PLAYLIST_SONG",
               joinColumns = @JoinColumn(name="playlists_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="songs_id", referencedColumnName="ID"))
    private Set<Song> songs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Playlist playlist = (Playlist) o;

        if ( ! Objects.equals(id, playlist.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", name='" + name + "'" +
                '}';
    }
}
