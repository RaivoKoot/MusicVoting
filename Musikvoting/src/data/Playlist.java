package data;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Playlist {

	private ObservableList<Song> playlist;

	public Playlist()
	{
		this.playlist = FXCollections.observableArrayList();
	}
	
	public void addSong(Song s){
		playlist.add(s);
	}
	
	public ObservableList<Song> getPlaylist(){
		return playlist;
	}
}
