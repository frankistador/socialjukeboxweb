package org.sauterelle.socialjukebox.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.*;

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

    @ManyToOne
    @JoinTable(name = "T_USER_PLAYLIST",
    joinColumns = @JoinColumn(name="playlists_id", referencedColumnName="ID"),
    inverseJoinColumns = @JoinColumn(name="host_id", referencedColumnName="ID"))
    private User host;
    
    
    public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}

	@ManyToMany
    @JoinTable(name = "T_PLAYLIST_SONG",
               joinColumns = @JoinColumn(name="playlists_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="songs_id", referencedColumnName="ID"))
    private List<Song> songs = new ArrayList<>();

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

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
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
                ", name='" + name + 
               // ", host="  + host.getLogin() + "'" +
                '}';
    }
}
