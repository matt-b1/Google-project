package com.google;

import java.util.ArrayList;

/** A class used to represent a Playlist */
class VideoPlaylist {
    private String playlistName;
    private ArrayList<Video> videos;

    VideoPlaylist(String playlistName, ArrayList<Video> videos) {
        this.playlistName = playlistName;
        this.videos = videos;
    }

    public void addVid(Video video){
        videos.add(video);
    }

    public String getPlaylistName() {
        return this.playlistName;
    }

    public ArrayList<Video> getVideos() {
        return this.videos;
    }

    void setPlaylistName(String playlist) {
        this.playlistName = playlist;
    }

    void setVideos(ArrayList<Video> video) {
        this.videos = video;
    }

    void clearPlaylist(String playlist) {
        this.videos = new ArrayList<>();
    }
}
